package br.com.bjjacademy.dto.auth;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String tipo;
    private long expiraEmSegundos;
    private Long pessoaId;
    private String nome;
    private String perfil;
    private Set<String> permissoes;
    private Boolean trocaSenhaObrigatoria;
}
