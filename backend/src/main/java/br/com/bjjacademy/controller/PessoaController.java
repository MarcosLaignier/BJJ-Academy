package br.com.bjjacademy.controller;

import br.com.bjjacademy.dto.pessoa.PessoaRequestDTO;
import br.com.bjjacademy.dto.pessoa.PessoaResponseDTO;
import br.com.bjjacademy.service.PessoaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
@RequiredArgsConstructor
public class PessoaController {

    private final PessoaService service;

    @GetMapping
    @PreAuthorize("hasAuthority('ALUNO_VISUALIZAR')")
    public List<PessoaResponseDTO> pesquisar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String cpf,
            @RequestParam(required = false) Boolean ativo) {
        return service.pesquisar(nome, cpf, ativo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ALUNO_VISUALIZAR')")
    public PessoaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ALUNO_CADASTRAR')")
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponseDTO criar(@Valid @RequestBody PessoaRequestDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ALUNO_EDITAR')")
    public PessoaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody PessoaRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ALUNO_INATIVAR')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long id) {
        service.inativar(id);
    }
}
