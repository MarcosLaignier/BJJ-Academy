package br.com.bjjacademy.dto.graduacao;

import br.com.bjjacademy.enums.CategoriaFaixa;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaRequestDTO {

    @NotBlank(message = "O nome da faixa é obrigatório")
    @Size(max = 50)
    private String nome;

    @NotNull(message = "A categoria é obrigatória")
    private CategoriaFaixa categoria;

    @NotBlank(message = "A cor principal é obrigatória")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Informe uma cor hexadecimal válida")
    private String corPrincipalHex;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Informe uma cor hexadecimal válida")
    private String corSecundariaHex;

    @NotBlank(message = "A cor da tarja é obrigatória")
    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Informe uma cor hexadecimal válida")
    private String corTarjaHex;

    @NotNull
    @Min(1)
    private Integer ordem;

    @Min(0)
    private Integer idadeMinima;

    @NotNull
    @Min(0)
    @Max(10)
    private Integer quantidadeMaximaGraus;

    @NotNull
    private Boolean ativo;
}
