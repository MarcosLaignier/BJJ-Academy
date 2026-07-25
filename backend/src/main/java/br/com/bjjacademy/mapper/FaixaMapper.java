package br.com.bjjacademy.mapper;

import br.com.bjjacademy.domain.Faixa;
import br.com.bjjacademy.dto.graduacao.FaixaRequestDTO;
import br.com.bjjacademy.dto.graduacao.FaixaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class FaixaMapper {

    public FaixaResponseDTO paraDTO(Faixa faixa) {
        return FaixaResponseDTO.builder()
                .id(faixa.getId())
                .codigo(faixa.getCodigo())
                .nome(faixa.getNome())
                .categoria(faixa.getCategoria())
                .corPrincipalHex(faixa.getCorPrincipalHex())
                .corSecundariaHex(faixa.getCorSecundariaHex())
                .corTarjaHex(faixa.getCorTarjaHex())
                .ordem(faixa.getOrdem())
                .idadeMinima(faixa.getIdadeMinima())
                .quantidadeMaximaGraus(faixa.getQuantidadeMaximaGraus())
                .ativo(faixa.getAtivo())
                .build();
    }

    public void atualizarEntidade(Faixa faixa, FaixaRequestDTO dto) {
        faixa.setNome(dto.getNome().trim());
        faixa.setCategoria(dto.getCategoria());
        faixa.setCorPrincipalHex(normalizarCor(dto.getCorPrincipalHex()));
        faixa.setCorSecundariaHex(normalizarCor(dto.getCorSecundariaHex()));
        faixa.setCorTarjaHex(normalizarCor(dto.getCorTarjaHex()));
        faixa.setOrdem(dto.getOrdem());
        faixa.setIdadeMinima(dto.getIdadeMinima());
        faixa.setQuantidadeMaximaGraus(dto.getQuantidadeMaximaGraus());
        faixa.setAtivo(dto.getAtivo());
    }

    private String normalizarCor(String cor) {
        return cor == null || cor.isBlank() ? null : cor.toUpperCase();
    }
}
