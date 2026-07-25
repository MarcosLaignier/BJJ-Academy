package br.com.bjjacademy.config;

import br.com.bjjacademy.domain.ContaAcesso;
import br.com.bjjacademy.domain.Pessoa;
import br.com.bjjacademy.domain.PerfilAcesso;
import br.com.bjjacademy.repository.ContaAcessoRepository;
import br.com.bjjacademy.repository.PerfilAcessoRepository;
import br.com.bjjacademy.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;

@Component
@Profile("dev")
@ConditionalOnProperty(name = "app.security.create-dev-admin", havingValue = "true", matchIfMissing = true)
@RequiredArgsConstructor
public class AdministradorDevInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdministradorDevInitializer.class);
    private static final String CARACTERES = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz23456789!@#$%";

    private final ContaAcessoRepository contaRepository;
    private final PessoaRepository pessoaRepository;
    private final PerfilAcessoRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.security.admin-email}")
    private String email;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (contaRepository.findByEmailIgnoreCase(email).isPresent()) {
            return;
        }

        PerfilAcesso perfil = perfilRepository.findByNome("Administrador")
                .orElseThrow(() -> new IllegalStateException("Perfil Administrador não encontrado"));
        Pessoa pessoa = pessoaRepository.save(Pessoa.builder()
                .nomeCompleto("Administrador Fênix")
                .email(email.toLowerCase())
                .ativo(true)
                .build());
        String senhaTemporaria = gerarSenha();

        contaRepository.save(ContaAcesso.builder()
                .pessoa(pessoa)
                .email(email.toLowerCase())
                .senhaHash(passwordEncoder.encode(senhaTemporaria))
                .perfil(perfil)
                .ativo(true)
                .trocaSenhaObrigatoria(true)
                .build());

        LOGGER.warn("============================================================");
        LOGGER.warn("CONTA ADMINISTRADORA DE DESENVOLVIMENTO CRIADA");
        LOGGER.warn("E-mail: {}", email);
        LOGGER.warn("Senha temporária: {}", senhaTemporaria);
        LOGGER.warn("A senha muda sempre que o banco H2 em memória é recriado.");
        LOGGER.warn("============================================================");
    }

    private String gerarSenha() {
        SecureRandom random = new SecureRandom();
        StringBuilder senha = new StringBuilder(16);
        for (int i = 0; i < 16; i++) {
            senha.append(CARACTERES.charAt(random.nextInt(CARACTERES.length())));
        }
        return senha.toString();
    }
}
