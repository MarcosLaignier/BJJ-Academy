package br.com.bjjacademy.dto.graduacao;

import br.com.bjjacademy.enums.CategoriaFaixa;
import br.com.bjjacademy.enums.CodigoFaixa;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaResponseDTO {
    private Long id;
    private CodigoFaixa codigo;
    private String nome;
    private CategoriaFaixa categoria;
    private String corPrincipalHex;
    private String corSecundariaHex;
    private String corTarjaHex;
    private Integer ordem;
    private Integer idadeMinima;
    private Integer quantidadeMaximaGraus;
    private Boolean ativo;
}
