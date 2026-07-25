package br.com.bjjacademy.dto.acesso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilAcessoRequestDTO {

    @NotBlank(message = "O nome do perfil é obrigatório")
    @Size(max = 80)
    private String nome;

    @Size(max = 300)
    private String descricao;

    private Boolean ativo;

    @NotEmpty(message = "Selecione ao menos uma permissão")
    private Set<Long> permissoesIds;
}
