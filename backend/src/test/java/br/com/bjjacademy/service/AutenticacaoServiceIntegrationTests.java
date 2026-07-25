package br.com.bjjacademy.service;

import br.com.bjjacademy.domain.ContaAcesso;
import br.com.bjjacademy.domain.Pessoa;
import br.com.bjjacademy.dto.auth.LoginRequestDTO;
import br.com.bjjacademy.dto.auth.LoginResponseDTO;
import br.com.bjjacademy.repository.ContaAcessoRepository;
import br.com.bjjacademy.repository.PerfilAcessoRepository;
import br.com.bjjacademy.repository.PessoaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AutenticacaoServiceIntegrationTests {

    @Autowired
    private AutenticacaoService service;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ContaAcessoRepository contaRepository;
    @Autowired
    private PerfilAcessoRepository perfilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void deveAutenticarAdministradorEEmitirJwtComPermissoes() {
        Pessoa pessoa = pessoaRepository.save(Pessoa.builder()
                .nomeCompleto("Administrador de Teste")
                .email("admin-teste@fenix.local")
                .ativo(true)
                .build());

        contaRepository.save(ContaAcesso.builder()
                .pessoa(pessoa)
                .email("admin-teste@fenix.local")
                .senhaHash(passwordEncoder.encode("SenhaForte@123"))
                .perfil(perfilRepository.findByNome("Administrador").orElseThrow())
                .ativo(true)
                .trocaSenhaObrigatoria(true)
                .build());

        LoginResponseDTO resposta = service.autenticar(LoginRequestDTO.builder()
                .email("admin-teste@fenix.local")
                .senha("SenhaForte@123")
                .build());

        assertThat(resposta.getToken()).isNotBlank();
        assertThat(resposta.getPerfil()).isEqualTo("Administrador");
        assertThat(resposta.getPermissoes()).contains("ALUNO_CADASTRAR", "PERFIL_GERENCIAR");
    }
}
