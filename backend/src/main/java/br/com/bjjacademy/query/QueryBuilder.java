package br.com.bjjacademy.query;

import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class QueryBuilder<T> {

    private final List<Specification<T>> especificacoes = new ArrayList<>();

    private QueryBuilder() {
    }

    public static <T> QueryBuilder<T> criar() {
        return new QueryBuilder<>();
    }

    public QueryBuilder<T> igual(String campo, Object valor) {
        if (valor != null) {
            especificacoes.add((root, query, cb) -> cb.equal(resolver(root, campo), valor));
        }
        return this;
    }

    public QueryBuilder<T> contemIgnoreCase(String campo, String valor) {
        if (valor != null && !valor.isBlank()) {
            String termo = "%" + valor.trim().toLowerCase() + "%";
            especificacoes.add((root, query, cb) ->
                    cb.like(cb.lower(resolver(root, campo).as(String.class)), termo));
        }
        return this;
    }

    public Specification<T> construir() {
        return especificacoes.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }

    private static Path<?> resolver(Path<?> raiz, String campo) {
        Path<?> caminho = raiz;
        for (String parte : campo.split("\\.")) {
            caminho = caminho.get(parte);
        }
        return caminho;
    }
}
