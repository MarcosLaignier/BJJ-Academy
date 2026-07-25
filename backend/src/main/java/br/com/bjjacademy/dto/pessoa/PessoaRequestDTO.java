package br.com.bjjacademy.dto.pessoa;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PessoaRequestDTO {

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(max = 150)
    private String nomeCompleto;

    @Size(max = 150)
    private String nomeSocial;

    @Pattern(
            regexp = "\\d{11}|\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}",
            message = "O CPF deve conter 11 dígitos")
    private String cpf;

    private LocalDate dataNascimento;

    @Email(message = "E-mail inválido")
    @Size(max = 160)
    private String email;

    @Size(max = 30)
    private String telefone;

    private Boolean ativo;
}
