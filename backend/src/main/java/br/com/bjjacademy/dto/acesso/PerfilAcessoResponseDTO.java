package br.com.bjjacademy.dto.acesso;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerfilAcessoResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Boolean ativo;
    private Set<PermissaoResponseDTO> permissoes;
}
