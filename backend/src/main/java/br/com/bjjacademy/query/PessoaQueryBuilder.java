package br.com.bjjacademy.query;

import br.com.bjjacademy.domain.Pessoa;
import org.springframework.data.jpa.domain.Specification;

public final class PessoaQueryBuilder {

    private PessoaQueryBuilder() {
    }

    public static Specification<Pessoa> construir(String nome, String cpf, Boolean ativo) {
        return QueryBuilder.<Pessoa>criar()
                .contemIgnoreCase("nomeCompleto", nome)
                .igual("cpf", somenteNumeros(cpf))
                .igual("ativo", ativo)
                .construir();
    }

    private static String somenteNumeros(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.replaceAll("\\D", "");
    }
}
