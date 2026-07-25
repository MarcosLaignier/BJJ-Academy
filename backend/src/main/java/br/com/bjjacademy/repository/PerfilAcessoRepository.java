package br.com.bjjacademy.repository;

import br.com.bjjacademy.domain.PerfilAcesso;

import java.util.Optional;

public interface PerfilAcessoRepository extends BaseRepository<PerfilAcesso, Long> {
    Optional<PerfilAcesso> findByNome(String nome);
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeIgnoreCaseAndIdNot(String nome, Long id);
}
