package br.com.bjjacademy.service;

import br.com.bjjacademy.domain.Pessoa;
import br.com.bjjacademy.dto.pessoa.PessoaRequestDTO;
import br.com.bjjacademy.dto.pessoa.PessoaResponseDTO;
import br.com.bjjacademy.exception.RegistroNaoEncontradoException;
import br.com.bjjacademy.exception.RegraNegocioException;
import br.com.bjjacademy.mapper.PessoaMapper;
import br.com.bjjacademy.query.PessoaQueryBuilder;
import br.com.bjjacademy.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoaService {

    private final PessoaRepository repository;
    private final PessoaMapper mapper;

    @Transactional(readOnly = true)
    public List<PessoaResponseDTO> pesquisar(String nome, String cpf, Boolean ativo) {
        return repository.findAll(
                        PessoaQueryBuilder.construir(nome, cpf, ativo),
                        Sort.by(Sort.Direction.ASC, "nomeCompleto"))
                .stream()
                .map(mapper::paraDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PessoaResponseDTO buscarPorId(Long id) {
        return mapper.paraDTO(buscarEntidade(id));
    }

    @Transactional
    public PessoaResponseDTO criar(PessoaRequestDTO dto) {
        Pessoa pessoa = mapper.paraEntidade(dto);
        validarCpfUnico(pessoa.getCpf(), null);
        return mapper.paraDTO(repository.save(pessoa));
    }

    @Transactional
    public PessoaResponseDTO atualizar(Long id, PessoaRequestDTO dto) {
        Pessoa pessoa = buscarEntidade(id);
        mapper.atualizarEntidade(pessoa, dto);
        validarCpfUnico(pessoa.getCpf(), id);
        return mapper.paraDTO(repository.save(pessoa));
    }

    @Transactional
    public void inativar(Long id) {
        Pessoa pessoa = buscarEntidade(id);
        pessoa.setAtivo(false);
        repository.save(pessoa);
    }

    private Pessoa buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RegistroNaoEncontradoException("Pessoa não encontrada"));
    }

    private void validarCpfUnico(String cpf, Long id) {
        if (cpf == null) {
            return;
        }
        boolean duplicado = id == null ? repository.existsByCpf(cpf) : repository.existsByCpfAndIdNot(cpf, id);
        if (duplicado) {
            throw new RegraNegocioException("Já existe uma pessoa cadastrada com este CPF");
        }
    }
}
