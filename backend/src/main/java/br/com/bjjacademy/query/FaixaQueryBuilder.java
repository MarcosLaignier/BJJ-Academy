package br.com.bjjacademy.query;

import br.com.bjjacademy.domain.Faixa;
import br.com.bjjacademy.enums.CategoriaFaixa;
import org.springframework.data.jpa.domain.Specification;

public final class FaixaQueryBuilder {

    private FaixaQueryBuilder() {
    }

    public static Specification<Faixa> construir(String nome, CategoriaFaixa categoria, Boolean ativo) {
        return QueryBuilder.<Faixa>criar()
                .contemIgnoreCase("nome", nome)
                .igual("categoria", categoria)
                .igual("ativo", ativo)
                .construir();
    }
}
