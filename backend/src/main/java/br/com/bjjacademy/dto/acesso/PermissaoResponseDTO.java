package br.com.bjjacademy.dto.acesso;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissaoResponseDTO {
    private Long id;
    private String codigo;
    private String nome;
    private String descricao;
}
