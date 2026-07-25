package br.com.bjjacademy.repository;

import br.com.bjjacademy.domain.ContaAcesso;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;

public interface ContaAcessoRepository extends BaseRepository<ContaAcesso, Long> {
    @EntityGraph(attributePaths = {"pessoa", "perfil", "perfil.permissoes"})
    Optional<ContaAcesso> findByEmailIgnoreCase(String email);
}
