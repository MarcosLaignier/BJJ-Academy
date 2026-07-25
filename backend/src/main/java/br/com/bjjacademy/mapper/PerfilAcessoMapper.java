package br.com.bjjacademy.mapper;

import br.com.bjjacademy.domain.PerfilAcesso;
import br.com.bjjacademy.domain.Permissao;
import br.com.bjjacademy.dto.acesso.PerfilAcessoRequestDTO;
import br.com.bjjacademy.dto.acesso.PerfilAcessoResponseDTO;
import br.com.bjjacademy.dto.acesso.PermissaoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PerfilAcessoMapper {

    public PerfilAcesso paraEntidade(PerfilAcessoRequestDTO dto, Set<Permissao> permissoes) {
        PerfilAcesso perfil = new PerfilAcesso();
        atualizarEntidade(perfil, dto, permissoes);
        return perfil;
    }

    public void atualizarEntidade(
            PerfilAcesso perfil,
            PerfilAcessoRequestDTO dto,
            Set<Permissao> permissoes) {
        perfil.setNome(dto.getNome().trim());
        perfil.setDescricao(normalizar(dto.getDescricao()));
        perfil.setPermissoes(new LinkedHashSet<>(permissoes));
        if (dto.getAtivo() != null) {
            perfil.setAtivo(dto.getAtivo());
        }
    }

    public PerfilAcessoResponseDTO paraDTO(PerfilAcesso perfil) {
        return PerfilAcessoResponseDTO.builder()
                .id(perfil.getId())
                .nome(perfil.getNome())
                .descricao(perfil.getDescricao())
                .ativo(perfil.getAtivo())
                .permissoes(perfil.getPermissoes().stream()
                        .sorted(Comparator.comparing(Permissao::getNome))
                        .map(this::permissaoParaDTO)
                        .collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }

    public PermissaoResponseDTO permissaoParaDTO(Permissao permissao) {
        return PermissaoResponseDTO.builder()
                .id(permissao.getId())
                .codigo(permissao.getCodigo())
                .nome(permissao.getNome())
                .descricao(permissao.getDescricao())
                .build();
    }

    private String normalizar(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
