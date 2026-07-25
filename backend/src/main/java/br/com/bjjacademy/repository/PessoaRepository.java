package br.com.bjjacademy.repository;

import br.com.bjjacademy.domain.Pessoa;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends BaseRepository<Pessoa, Long> {
    boolean existsByCpf(String cpf);
    boolean existsByCpfAndIdNot(String cpf, Long id);
}
