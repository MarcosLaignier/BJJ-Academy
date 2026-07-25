# BJJ Academy

Landing page pública e futura plataforma de gestão para uma academia de jiu-jitsu.

## Estrutura

- `frontend`: Angular 22, SSR, Bootstrap 5, ng-bootstrap e SCSS.
- `backend`: Java 17, Spring Boot 4, Maven, JPA, Flyway, H2 e PostgreSQL.

As convenções de arquitetura, nomenclatura e qualidade estão em
[docs/padroes-do-projeto.md](docs/padroes-do-projeto.md).

## Executar o frontend

```bash
cd frontend
npm install
npm start
```

Acesse `http://localhost:4201`. A porta 4201 evita conflito com aplicações do
outro perfil Linux que utilizam a porta padrão 4200.

## Executar o backend

```bash
cd backend
./mvnw spring-boot:run
```

No IntelliJ IDEA, selecione a configuração compartilhada `BJJ Academy API` e
clique em **Run**. O frontend permanece separado e deve ser iniciado com
`npm start` dentro de `frontend`.

No profile `dev`, a API cria a conta `admin@fenix.local` e imprime uma senha
temporária no console do IntelliJ. Como o H2 é mantido em memória, uma nova senha
é gerada sempre que o backend é reiniciado. Acesse `http://localhost:4201/login`.

A API usa o profile `dev` e o H2 em memória por padrão. O console fica disponível em
`http://localhost:8080/h2-console`.

Para PostgreSQL, use o profile `prod` e informe:

```bash
export SPRING_PROFILES_ACTIVE=prod
export DATABASE_URL=jdbc:postgresql://localhost:5432/bjj_academy
export DATABASE_USERNAME=bjj_academy
export DATABASE_PASSWORD=troque-esta-senha
./mvnw spring-boot:run
```

Não versione senhas ou arquivos `.env`.

## Ambiente de desenvolvimento

As instalações são isoladas no usuário Linux atual, conforme o `AGENTS.md`.
Consulte [docs/ambiente.md](docs/ambiente.md) para versões, motivo, data, comandos e locais.
