package br.com.bjjacademy.query;

import br.com.bjjacademy.domain.PerfilAcesso;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public final class PerfilAcessoQueryBuilder {

    private PerfilAcessoQueryBuilder() {
    }

    public static Specification<PerfilAcesso> construir(
            String nome,
            Boolean ativo,
            Long permissaoId) {
        Specification<PerfilAcesso> filtrosBasicos = QueryBuilder.<PerfilAcesso>criar()
                .contemIgnoreCase("nome", nome)
                .igual("ativo", ativo)
                .construir();

        if (permissaoId == null) {
            return filtrosBasicos;
        }

        Specification<PerfilAcesso> porPermissao = (root, query, cb) -> {
            query.distinct(true);
            return cb.equal(root.join("permissoes", JoinType.INNER).get("id"), permissaoId);
        };
        return filtrosBasicos.and(porPermissao);
    }
}
