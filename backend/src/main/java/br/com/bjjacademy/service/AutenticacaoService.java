package br.com.bjjacademy.service;

import br.com.bjjacademy.domain.ContaAcesso;
import br.com.bjjacademy.dto.auth.LoginRequestDTO;
import br.com.bjjacademy.dto.auth.LoginResponseDTO;
import br.com.bjjacademy.repository.ContaAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final ContaAcessoRepository repository;
    private final JwtEncoder jwtEncoder;

    @Value("${app.security.token-duration}")
    private Duration duracaoToken;

    @Transactional
    public LoginResponseDTO autenticar(LoginRequestDTO dto) {
        authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(dto.getEmail(), dto.getSenha()));

        ContaAcesso conta = repository.findByEmailIgnoreCase(dto.getEmail()).orElseThrow();
        Set<String> permissoes = conta.getPerfil().getPermissoes().stream()
                .map(permissao -> permissao.getCodigo())
                .collect(Collectors.toSet());

        Instant agora = Instant.now();
        String token = jwtEncoder.encode(JwtEncoderParameters.from(JwtClaimsSet.builder()
                        .issuer("bjj-academy-api")
                        .issuedAt(agora)
                        .expiresAt(agora.plus(duracaoToken))
                        .subject(conta.getEmail())
                        .claim("pessoaId", conta.getPessoa().getId())
                        .claim("nome", conta.getPessoa().getNomeCompleto())
                        .claim("perfil", conta.getPerfil().getNome())
                        .claim("permissoes", permissoes)
                        .build()))
                .getTokenValue();

        conta.setUltimoAcessoEm(OffsetDateTime.now());

        return LoginResponseDTO.builder()
                .token(token)
                .tipo("Bearer")
                .expiraEmSegundos(duracaoToken.toSeconds())
                .pessoaId(conta.getPessoa().getId())
                .nome(conta.getPessoa().getNomeCompleto())
                .perfil(conta.getPerfil().getNome())
                .permissoes(permissoes)
                .trocaSenhaObrigatoria(conta.getTrocaSenhaObrigatoria())
                .build();
    }
}
