package br.com.bjjacademy.service;

import br.com.bjjacademy.domain.Faixa;
import br.com.bjjacademy.dto.graduacao.FaixaRequestDTO;
import br.com.bjjacademy.dto.graduacao.FaixaResponseDTO;
import br.com.bjjacademy.enums.CategoriaFaixa;
import br.com.bjjacademy.exception.RegistroNaoEncontradoException;
import br.com.bjjacademy.exception.RegraNegocioException;
import br.com.bjjacademy.mapper.FaixaMapper;
import br.com.bjjacademy.query.FaixaQueryBuilder;
import br.com.bjjacademy.repository.FaixaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaixaService {

    private final FaixaRepository repository;
    private final FaixaMapper mapper;

    @Transactional(readOnly = true)
    public List<FaixaResponseDTO> pesquisar(String nome, CategoriaFaixa categoria, Boolean ativo) {
        return repository.findAll(FaixaQueryBuilder.construir(nome, categoria, ativo), Sort.by("ordem"))
                .stream()
                .map(mapper::paraDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public FaixaResponseDTO buscarPorId(Long id) {
        return mapper.paraDTO(buscar(id));
    }

    @Transactional
    public FaixaResponseDTO atualizar(Long id, FaixaRequestDTO dto) {
        Faixa faixa = buscar(id);
        validarDuplicidade(dto, id);
        mapper.atualizarEntidade(faixa, dto);
        return mapper.paraDTO(repository.save(faixa));
    }

    private Faixa buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Faixa não encontrada"));
    }

    private void validarDuplicidade(FaixaRequestDTO dto, Long id) {
        if (repository.existsByNomeIgnoreCaseAndIdNot(dto.getNome().trim(), id)) {
            throw new RegraNegocioException("Já existe uma faixa com este nome");
        }
        if (repository.existsByOrdemAndIdNot(dto.getOrdem(), id)) {
            throw new RegraNegocioException("Já existe uma faixa nesta ordem");
        }
    }
}
