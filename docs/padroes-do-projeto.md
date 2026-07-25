# Padrões do projeto

Este documento define o padrão técnico do BJJ Academy. O projeto LimiteMEI é uma
referência de organização em frontend e API, mas as decisões aqui registradas têm
prioridade e são adaptadas ao domínio de academias de jiu-jitsu.

## Estrutura

```text
BJJ Academy/
├── frontend/  # aplicação Angular
├── backend/   # API Spring Boot
└── docs/      # decisões, ambiente e documentação técnica
```

Os diretórios mantêm nomes curtos para facilitar os comandos locais. Artefatos e
pacotes devem usar nomes explícitos do produto:

- frontend: `bjj-academy-web`;
- backend: `bjj-academy-api`;
- pacote Java raiz: `br.com.bjjacademy`.

Não devem ser versionados artefatos gerados, dependências baixadas, configurações
pessoais da IDE, logs ou segredos. Isso inclui `node_modules`, `dist`, `.angular`,
`coverage`, `target`, `.idea`, `.vscode` e arquivos `.env`.

## Backend

O backend usa Java 17, Spring Boot, Maven Wrapper, JPA, Flyway, H2 e PostgreSQL.
Novas funcionalidades devem ser organizadas por responsabilidade, seguindo a
referência do LimiteMEI:

```text
br.com.bjjacademy
├── controller
├── domain
├── dto
├── enums
├── mapper
├── repository
├── service
└── exception
```

O domínio inicial separa:

- `Pessoa`, que mantém os dados cadastrais;
- `PapelAcademia` e `PessoaPapel`, que representam a atuação na academia;
- `ContaAcesso`, `PerfilAcesso` e `Permissao`, que controlam acesso ao sistema;
- `Matricula`, que registra o vínculo do aluno;
- `Faixa` e `HistoricoGraduacao`, que preservam a evolução sem sobrescrever o
  histórico anterior.

Papéis da academia não concedem acesso automaticamente. Uma pessoa pode ter
vários papéis e nenhuma conta, enquanto permissões são atribuídas por perfil de
acesso.

A autenticação usa Spring Security, senhas BCrypt e tokens JWT assinados com
HS256. Endpoints privados exigem token Bearer e verificam permissões específicas.
A chave de produção é fornecida obrigatoriamente por `JWT_SECRET`. Senhas não são
versionadas: no profile `dev`, a senha temporária do administrador aparece apenas
no console durante a inicialização.

Regras:

- controllers tratam HTTP e delegam regras de negócio;
- services concentram casos de uso e transações;
- repositories cuidam apenas da persistência;
- entidades JPA não são expostas diretamente pela API; usar DTOs;
- mapeamentos entre entidades e DTOs ficam em `mapper`;
- erros da API são tratados de forma centralizada em `exception`;
- nomes de classes e métodos ficam em inglês, mantendo consistência com Java e
  com o esquema do banco;
- alterações no banco são feitas somente por migrations Flyway versionadas;
- `ddl-auto` permanece como `validate`, sem criação automática de tabelas;
- executar Maven sempre pelo wrapper: `./mvnw`.

O profile `dev` usa H2 em memória. O profile `prod` usa PostgreSQL e recebe
credenciais por variáveis de ambiente. Segredos nunca são colocados no código,
nos arquivos YAML ou na documentação.

O IntelliJ possui a configuração compartilhada `.run/BJJ Academy API.run.xml`
para executar somente a API com o profile `dev`. O frontend não faz parte dessa
configuração e é iniciado separadamente com `npm start`, na porta 4201.

## Frontend

O frontend usa Angular standalone, TypeScript estrito, SCSS, Bootstrap e SSR.
À medida que as funcionalidades forem criadas, a estrutura deve evoluir para:

```text
src/app/
├── core/       # serviços singleton, interceptors e infraestrutura
├── shared/     # componentes, pipes e utilitários reutilizáveis
└── features/   # funcionalidades separadas por domínio
```

Cada feature pode conter `pages`, `components`, `services`, `models` e `routes`
quando necessário. Evitar pastas vazias e abstrações antes de haver uso real.

Regras:

- componentes são standalone;
- páginas orquestram a tela e componentes menores cuidam da apresentação;
- acesso HTTP fica em services, nunca diretamente em componentes;
- contratos da API são tipados;
- estilos globais ficam em `src/styles.scss`; estilos específicos ficam junto
  ao componente;
- preservar compatibilidade com SSR, sem acessar `window`, `document` ou
  `localStorage` sem verificar a plataforma;
- instalar dependências localmente com npm e manter o `package-lock.json`.

## Nomenclatura

- Java: classes em `PascalCase`, métodos e atributos em `camelCase`;
- TypeScript: arquivos em `kebab-case` e símbolos conforme as convenções Angular;
- banco: tabelas e colunas em `snake_case`;
- endpoints: substantivos no plural em `kebab-case`, sob `/api`;
- migrations: `V<numero>__<descricao_em_snake_case>.sql`;
- branches e commits devem descrever uma alteração coesa.

## Qualidade

Antes de considerar uma alteração concluída:

```bash
cd backend
./mvnw test

cd ../frontend
npm test -- --watch=false
npm run build
```

Funcionalidades novas devem incluir testes proporcionais ao risco. Mudanças de
contrato entre frontend e backend devem ser atualizadas nos dois lados.

## Ambiente e instalações

As ferramentas são instaladas somente no usuário Linux atual, preservando o
isolamento entre os perfis Pessoal e Trabalho. Java é gerenciado pelo SDKMAN! e
Node pelo NVM. Não usar `sudo` para instalar ferramentas de desenvolvimento.

Toda mudança de ferramenta, versão ou procedimento de instalação deve ser
registrada em [ambiente.md](ambiente.md), incluindo motivo, comandos, data e
local da instalação.
