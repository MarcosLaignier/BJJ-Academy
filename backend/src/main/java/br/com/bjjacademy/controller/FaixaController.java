package br.com.bjjacademy.controller;

import br.com.bjjacademy.dto.graduacao.FaixaRequestDTO;
import br.com.bjjacademy.dto.graduacao.FaixaResponseDTO;
import br.com.bjjacademy.enums.CategoriaFaixa;
import br.com.bjjacademy.service.FaixaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faixas")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('FAIXA_GERENCIAR')")
public class FaixaController {

    private final FaixaService service;

    @GetMapping
    public List<FaixaResponseDTO> pesquisar(@RequestParam(required = false) String nome,
                                           @RequestParam(required = false) CategoriaFaixa categoria,
                                           @RequestParam(required = false) Boolean ativo) {
        return service.pesquisar(nome, categoria, ativo);
    }

    @GetMapping("/{id}")
    public FaixaResponseDTO buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public FaixaResponseDTO atualizar(@PathVariable Long id, @Valid @RequestBody FaixaRequestDTO dto) {
        return service.atualizar(id, dto);
    }
}
