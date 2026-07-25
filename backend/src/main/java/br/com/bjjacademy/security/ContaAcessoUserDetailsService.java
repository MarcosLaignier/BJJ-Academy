package br.com.bjjacademy.security;

import br.com.bjjacademy.domain.ContaAcesso;
import br.com.bjjacademy.repository.ContaAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContaAcessoUserDetailsService implements UserDetailsService {

    private final ContaAcessoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ContaAcesso conta = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais inválidas"));

        var autoridades = conta.getPerfil().getPermissoes().stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getCodigo()))
                .toList();

        return User.withUsername(conta.getEmail())
                .password(conta.getSenhaHash())
                .authorities(autoridades)
                .disabled(!conta.getAtivo())
                .build();
    }
}
