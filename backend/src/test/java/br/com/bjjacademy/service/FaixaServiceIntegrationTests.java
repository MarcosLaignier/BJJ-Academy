package br.com.bjjacademy.service;

import br.com.bjjacademy.dto.graduacao.FaixaRequestDTO;
import br.com.bjjacademy.dto.graduacao.FaixaResponseDTO;
import br.com.bjjacademy.enums.CategoriaFaixa;
import br.com.bjjacademy.enums.CodigoFaixa;
import br.com.bjjacademy.exception.RegraNegocioException;
import br.com.bjjacademy.repository.FaixaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class FaixaServiceIntegrationTests {

    @Autowired
    private FaixaService service;
    @Autowired
    private FaixaRepository repository;

    @Test
    void deveCarregarCatalogoOficialEAtualizarAparencia() {
        assertThat(repository.findAll()).hasSize(20);
        FaixaResponseDTO branca = service.pesquisar("Branca", CategoriaFaixa.GERAL, true).get(0);

        FaixaResponseDTO atualizada = service.atualizar(branca.getId(), request("Branca", 1, "#FFFFFF"));

        assertThat(atualizada.getCodigo()).isEqualTo(CodigoFaixa.BRANCA);
        assertThat(atualizada.getCorPrincipalHex()).isEqualTo("#FFFFFF");
    }

    @Test
    void naoDevePermitirOrdemDuplicada() {
        FaixaResponseDTO branca = service.pesquisar("Branca", null, null).get(0);
        assertThatThrownBy(() -> service.atualizar(branca.getId(), request("Branca", 2, "#FFFFFF")))
                .isInstanceOf(RegraNegocioException.class);
    }

    private FaixaRequestDTO request(String nome, int ordem, String cor) {
        return FaixaRequestDTO.builder()
                .nome(nome)
                .categoria(CategoriaFaixa.GERAL)
                .corPrincipalHex(cor)
                .corTarjaHex("#111111")
                .ordem(ordem)
                .idadeMinima(0)
                .quantidadeMaximaGraus(4)
                .ativo(true)
                .build();
    }
}
