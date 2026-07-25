package br.com.bjjacademy.controller;

import br.com.bjjacademy.dto.acesso.PerfilAcessoRequestDTO;
import br.com.bjjacademy.dto.acesso.PerfilAcessoResponseDTO;
import br.com.bjjacademy.dto.acesso.PermissaoResponseDTO;
import br.com.bjjacademy.service.PerfilAcessoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('PERFIL_GERENCIAR')")
public class PerfilAcessoController {

    private final PerfilAcessoService service;

    @GetMapping("/perfis-acesso")
    public List<PerfilAcessoResponseDTO> pesquisar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Long permissaoId) {
        return service.pesquisar(nome, ativo, permissaoId);
    }

    @PostMapping("/perfis-acesso")
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilAcessoResponseDTO criar(@Valid @RequestBody PerfilAcessoRequestDTO dto) {
        return service.criar(dto);
    }

    @GetMapping("/perfis-acesso/{id}")
    public PerfilAcessoResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/perfis-acesso/{id}")
    public PerfilAcessoResponseDTO atualizar(
            @PathVariable Long id,
            @Valid @RequestBody PerfilAcessoRequestDTO dto) {
        return service.atualizar(id, dto);
    }

    @GetMapping("/permissoes")
    public List<PermissaoResponseDTO> listarPermissoes() {
        return service.listarPermissoes();
    }
}
