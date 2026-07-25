package br.com.bjjacademy.service;

import br.com.bjjacademy.dto.pessoa.PessoaRequestDTO;
import br.com.bjjacademy.dto.pessoa.PessoaResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PessoaServiceIntegrationTests {

    @Autowired
    private PessoaService service;

    @Test
    void deveCriarPesquisarAtualizarEInativarPessoaSemTrocarOId() {
        PessoaResponseDTO criada = service.criar(PessoaRequestDTO.builder()
                .nomeCompleto("Maria da Silva")
                .cpf("123.456.789-01")
                .dataNascimento(LocalDate.of(1995, 5, 10))
                .email("MARIA@EXEMPLO.COM")
                .build());

        assertThat(criada.getId()).isNotNull();
        assertThat(criada.getCpf()).isEqualTo("12345678901");
        assertThat(criada.getEmail()).isEqualTo("maria@exemplo.com");
        assertThat(service.pesquisar("maria", null, true)).hasSize(1);

        PessoaResponseDTO atualizada = service.atualizar(criada.getId(), PessoaRequestDTO.builder()
                .nomeCompleto("Maria Souza")
                .cpf("12345678901")
                .dataNascimento(LocalDate.of(1995, 5, 10))
                .email("maria@exemplo.com")
                .build());

        assertThat(atualizada.getId()).isEqualTo(criada.getId());
        assertThat(atualizada.getNomeCompleto()).isEqualTo("Maria Souza");

        service.inativar(criada.getId());

        assertThat(service.buscarPorId(criada.getId()).getAtivo()).isFalse();
    }
}
