package br.com.bjjacademy.service;

import br.com.bjjacademy.domain.PerfilAcesso;
import br.com.bjjacademy.domain.Permissao;
import br.com.bjjacademy.dto.acesso.PerfilAcessoRequestDTO;
import br.com.bjjacademy.dto.acesso.PerfilAcessoResponseDTO;
import br.com.bjjacademy.dto.acesso.PermissaoResponseDTO;
import br.com.bjjacademy.exception.RegistroNaoEncontradoException;
import br.com.bjjacademy.exception.RegraNegocioException;
import br.com.bjjacademy.mapper.PerfilAcessoMapper;
import br.com.bjjacademy.query.PerfilAcessoQueryBuilder;
import br.com.bjjacademy.repository.PerfilAcessoRepository;
import br.com.bjjacademy.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PerfilAcessoService {

    private final PerfilAcessoRepository repository;
    private final PermissaoRepository permissaoRepository;
    private final PerfilAcessoMapper mapper;

    @Transactional(readOnly = true)
    public List<PerfilAcessoResponseDTO> pesquisar(String nome, Boolean ativo, Long permissaoId) {
        return repository.findAll(
                        PerfilAcessoQueryBuilder.construir(nome, ativo, permissaoId),
                        Sort.by("nome"))
                .stream()
                .map(mapper::paraDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PermissaoResponseDTO> listarPermissoes() {
        return permissaoRepository.findAll(Sort.by("nome")).stream()
                .map(mapper::permissaoParaDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PerfilAcessoResponseDTO buscarPorId(Long id) {
        return mapper.paraDTO(buscar(id));
    }

    @Transactional
    public PerfilAcessoResponseDTO criar(PerfilAcessoRequestDTO dto) {
        validarNome(dto.getNome(), null);
        PerfilAcesso perfil = mapper.paraEntidade(dto, buscarPermissoes(dto.getPermissoesIds()));
        return mapper.paraDTO(repository.save(perfil));
    }

    @Transactional
    public PerfilAcessoResponseDTO atualizar(Long id, PerfilAcessoRequestDTO dto) {
        PerfilAcesso perfil = buscar(id);
        validarNome(dto.getNome(), id);
        validarAdministrador(perfil, dto);
        mapper.atualizarEntidade(perfil, dto, buscarPermissoes(dto.getPermissoesIds()));
        return mapper.paraDTO(repository.save(perfil));
    }

    private PerfilAcesso buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Perfil de acesso não encontrado"));
    }

    private Set<Permissao> buscarPermissoes(Set<Long> ids) {
        List<Permissao> permissoes = permissaoRepository.findAllById(ids);
        if (permissoes.size() != ids.size()) {
            throw new RegraNegocioException("Uma ou mais permissões não foram encontradas");
        }
        return new LinkedHashSet<>(permissoes);
    }

    private void validarNome(String nome, Long id) {
        boolean existe = id == null
                ? repository.existsByNomeIgnoreCase(nome.trim())
                : repository.existsByNomeIgnoreCaseAndIdNot(nome.trim(), id);
        if (existe) {
            throw new RegraNegocioException("Já existe um perfil com este nome");
        }
    }

    private void validarAdministrador(PerfilAcesso perfil, PerfilAcessoRequestDTO dto) {
        if (!"Administrador".equalsIgnoreCase(perfil.getNome())) {
            return;
        }
        boolean mantemGerenciamento = permissaoRepository.findAllById(dto.getPermissoesIds()).stream()
                .anyMatch(permissao -> "PERFIL_GERENCIAR".equals(permissao.getCodigo()));
        if (Boolean.FALSE.equals(dto.getAtivo()) || !mantemGerenciamento) {
            throw new RegraNegocioException(
                    "O perfil Administrador deve permanecer ativo e com a permissão de gerenciar perfis");
        }
    }
}
