package br.com.bjjacademy.dto.pessoa;

import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaResponseDTO {
    private Long id;
    private String nomeCompleto;
    private String nomeSocial;
    private String cpf;
    private LocalDate dataNascimento;
    private String email;
    private String telefone;
    private Boolean ativo;
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;
}
