ALTER TABLE faixa ADD COLUMN codigo VARCHAR(40);
ALTER TABLE faixa ADD COLUMN categoria VARCHAR(30);
ALTER TABLE faixa ADD COLUMN cor_principal_hex VARCHAR(7);
ALTER TABLE faixa ADD COLUMN cor_secundaria_hex VARCHAR(7);
ALTER TABLE faixa ADD COLUMN cor_tarja_hex VARCHAR(7);

UPDATE faixa SET ordem = ordem + 100;

UPDATE faixa SET codigo = 'BRANCA', categoria = 'GERAL', cor_principal_hex = '#F5F5F5', cor_tarja_hex = '#111111', ordem = 1 WHERE nome = 'Branca';
UPDATE faixa SET codigo = 'CINZA', categoria = 'INFANTIL', cor_principal_hex = '#808080', cor_tarja_hex = '#111111', ordem = 3 WHERE nome = 'Cinza';
UPDATE faixa SET codigo = 'AMARELA', categoria = 'INFANTIL', cor_principal_hex = '#F4D03F', cor_tarja_hex = '#111111', ordem = 6 WHERE nome = 'Amarela';
UPDATE faixa SET codigo = 'LARANJA', categoria = 'INFANTIL', cor_principal_hex = '#E67E22', cor_tarja_hex = '#111111', ordem = 9 WHERE nome = 'Laranja';
UPDATE faixa SET codigo = 'VERDE', categoria = 'INFANTIL', cor_principal_hex = '#278A45', cor_tarja_hex = '#111111', ordem = 12 WHERE nome = 'Verde';
UPDATE faixa SET codigo = 'AZUL', categoria = 'JUVENIL_ADULTO', cor_principal_hex = '#2455A4', cor_tarja_hex = '#111111', ordem = 14 WHERE nome = 'Azul';
UPDATE faixa SET codigo = 'ROXA', categoria = 'JUVENIL_ADULTO', cor_principal_hex = '#7030A0', cor_tarja_hex = '#111111', ordem = 15 WHERE nome = 'Roxa';
UPDATE faixa SET codigo = 'MARROM', categoria = 'JUVENIL_ADULTO', cor_principal_hex = '#6F3E24', cor_tarja_hex = '#111111', ordem = 16 WHERE nome = 'Marrom';
UPDATE faixa SET codigo = 'PRETA', categoria = 'JUVENIL_ADULTO', cor_principal_hex = '#111111', cor_tarja_hex = '#C62828', ordem = 17 WHERE nome = 'Preta';
UPDATE faixa SET codigo = 'CORAL_PRETA', categoria = 'GRADUACAO_SUPERIOR', cor_principal_hex = '#C62828', cor_secundaria_hex = '#111111', cor_tarja_hex = '#111111', ordem = 18 WHERE nome = 'Coral vermelha e preta';
UPDATE faixa SET codigo = 'CORAL_BRANCA', categoria = 'GRADUACAO_SUPERIOR', cor_principal_hex = '#C62828', cor_secundaria_hex = '#F5F5F5', cor_tarja_hex = '#111111', ordem = 19 WHERE nome = 'Coral vermelha e branca';
UPDATE faixa SET codigo = 'VERMELHA', categoria = 'GRADUACAO_SUPERIOR', cor_principal_hex = '#C62828', cor_tarja_hex = '#111111', ordem = 20 WHERE nome = 'Vermelha';

INSERT INTO faixa (codigo, nome, categoria, cor_principal_hex, cor_secundaria_hex, cor_tarja_hex, ordem, idade_minima, quantidade_maxima_graus, ativo) VALUES
    ('CINZA_BRANCA', 'Cinza e branca', 'INFANTIL', '#808080', '#F5F5F5', '#111111', 2, 4, 4, TRUE),
    ('CINZA_PRETA', 'Cinza e preta', 'INFANTIL', '#808080', '#111111', '#111111', 4, 4, 4, TRUE),
    ('AMARELA_BRANCA', 'Amarela e branca', 'INFANTIL', '#F4D03F', '#F5F5F5', '#111111', 5, 7, 4, TRUE),
    ('AMARELA_PRETA', 'Amarela e preta', 'INFANTIL', '#F4D03F', '#111111', '#111111', 7, 7, 4, TRUE),
    ('LARANJA_BRANCA', 'Laranja e branca', 'INFANTIL', '#E67E22', '#F5F5F5', '#111111', 8, 10, 4, TRUE),
    ('LARANJA_PRETA', 'Laranja e preta', 'INFANTIL', '#E67E22', '#111111', '#111111', 10, 10, 4, TRUE),
    ('VERDE_BRANCA', 'Verde e branca', 'INFANTIL', '#278A45', '#F5F5F5', '#111111', 11, 13, 4, TRUE),
    ('VERDE_PRETA', 'Verde e preta', 'INFANTIL', '#278A45', '#111111', '#111111', 13, 13, 4, TRUE);

ALTER TABLE faixa ALTER COLUMN codigo SET NOT NULL;
ALTER TABLE faixa ALTER COLUMN categoria SET NOT NULL;
ALTER TABLE faixa ALTER COLUMN cor_principal_hex SET NOT NULL;
ALTER TABLE faixa ALTER COLUMN cor_tarja_hex SET NOT NULL;
ALTER TABLE faixa ADD CONSTRAINT uk_faixa_codigo UNIQUE (codigo);
ALTER TABLE faixa ADD CONSTRAINT ck_faixa_categoria CHECK (categoria IN ('GERAL', 'INFANTIL', 'JUVENIL_ADULTO', 'GRADUACAO_SUPERIOR'));

INSERT INTO permissao (codigo, nome, descricao) VALUES
    ('FAIXA_GERENCIAR', 'Gerenciar faixas', 'Consultar e alterar o catálogo de faixas');

INSERT INTO perfil_permissao (perfil_acesso_id, permissao_id)
SELECT perfil.id, permissao.id
FROM perfil_acesso perfil
CROSS JOIN permissao
WHERE perfil.nome = 'Administrador'
  AND permissao.codigo = 'FAIXA_GERENCIAR';
