package br.com.bjjacademy.mapper;

import br.com.bjjacademy.domain.Pessoa;
import br.com.bjjacademy.dto.pessoa.PessoaRequestDTO;
import br.com.bjjacademy.dto.pessoa.PessoaResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

    public Pessoa paraEntidade(PessoaRequestDTO dto) {
        Pessoa pessoa = new Pessoa();
        atualizarEntidade(pessoa, dto);
        return pessoa;
    }

    public void atualizarEntidade(Pessoa pessoa, PessoaRequestDTO dto) {
        pessoa.setNomeCompleto(dto.getNomeCompleto().trim());
        pessoa.setNomeSocial(normalizar(dto.getNomeSocial()));
        pessoa.setCpf(somenteNumeros(dto.getCpf()));
        pessoa.setDataNascimento(dto.getDataNascimento());
        pessoa.setEmail(normalizarMinusculo(dto.getEmail()));
        pessoa.setTelefone(normalizar(dto.getTelefone()));
        if (dto.getAtivo() != null) {
            pessoa.setAtivo(dto.getAtivo());
        }
    }

    public PessoaResponseDTO paraDTO(Pessoa pessoa) {
        return PessoaResponseDTO.builder()
                .id(pessoa.getId())
                .nomeCompleto(pessoa.getNomeCompleto())
                .nomeSocial(pessoa.getNomeSocial())
                .cpf(pessoa.getCpf())
                .dataNascimento(pessoa.getDataNascimento())
                .email(pessoa.getEmail())
                .telefone(pessoa.getTelefone())
                .ativo(pessoa.getAtivo())
                .criadoEm(pessoa.getCriadoEm())
                .atualizadoEm(pessoa.getAtualizadoEm())
                .build();
    }

    private String somenteNumeros(String valor) {
        return valor == null || valor.isBlank() ? null : valor.replaceAll("\\D", "");
    }

    private String normalizar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }

    private String normalizarMinusculo(String valor) {
        String normalizado = normalizar(valor);
        return normalizado == null ? null : normalizado.toLowerCase();
    }
}
