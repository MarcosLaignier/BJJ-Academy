package br.com.bjjacademy.repository;

import br.com.bjjacademy.domain.Faixa;
import br.com.bjjacademy.enums.CodigoFaixa;

public interface FaixaRepository extends BaseRepository<Faixa, Long> {
    boolean existsByCodigo(CodigoFaixa codigo);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
    boolean existsByOrdemAndIdNot(Integer ordem, Long id);
}
