package br.com.bjjacademy.controller;

import br.com.bjjacademy.dto.auth.LoginRequestDTO;
import br.com.bjjacademy.dto.auth.LoginResponseDTO;
import br.com.bjjacademy.service.AutenticacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AutenticacaoService service;

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto) {
        return service.autenticar(dto);
    }
}
