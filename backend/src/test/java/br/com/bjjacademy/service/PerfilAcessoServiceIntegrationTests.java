package br.com.bjjacademy.service;

import br.com.bjjacademy.dto.acesso.PerfilAcessoRequestDTO;
import br.com.bjjacademy.dto.acesso.PerfilAcessoResponseDTO;
import br.com.bjjacademy.exception.RegraNegocioException;
import br.com.bjjacademy.repository.PerfilAcessoRepository;
import br.com.bjjacademy.repository.PermissaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class PerfilAcessoServiceIntegrationTests {

    @Autowired
    private PerfilAcessoService service;
    @Autowired
    private PerfilAcessoRepository perfilRepository;
    @Autowired
    private PermissaoRepository permissaoRepository;

    @Test
    void deveCriarEAtualizarPerfilComPermissoes() {
        Long visualizar = permissaoId("ALUNO_VISUALIZAR");
        Long cadastrar = permissaoId("ALUNO_CADASTRAR");

        PerfilAcessoResponseDTO criado = service.criar(PerfilAcessoRequestDTO.builder()
                .nome("Secretaria")
                .descricao("Atendimento da academia")
                .ativo(true)
                .permissoesIds(Set.of(visualizar))
                .build());

        PerfilAcessoResponseDTO atualizado = service.atualizar(criado.getId(), PerfilAcessoRequestDTO.builder()
                .nome("Secretaria")
                .descricao("Cadastro e atendimento")
                .ativo(true)
                .permissoesIds(Set.of(visualizar, cadastrar))
                .build());

        assertThat(atualizado.getId()).isEqualTo(criado.getId());
        assertThat(atualizado.getPermissoes()).hasSize(2);
        assertThat(service.pesquisar("secret", true, cadastrar))
                .extracting(PerfilAcessoResponseDTO::getId)
                .containsExactly(criado.getId());
        assertThat(service.pesquisar(null, false, cadastrar)).isEmpty();
    }

    @Test
    void naoDeveRemoverGerenciamentoDoAdministrador() {
        Long administradorId = perfilRepository.findByNome("Administrador").orElseThrow().getId();

        assertThatThrownBy(() -> service.atualizar(administradorId, PerfilAcessoRequestDTO.builder()
                .nome("Administrador")
                .ativo(true)
                .permissoesIds(Set.of(permissaoId("ALUNO_VISUALIZAR")))
                .build()))
                .isInstanceOf(RegraNegocioException.class);
    }

    private Long permissaoId(String codigo) {
        return permissaoRepository.findAll().stream()
                .filter(permissao -> codigo.equals(permissao.getCodigo()))
                .findFirst()
                .orElseThrow()
                .getId();
    }
}
