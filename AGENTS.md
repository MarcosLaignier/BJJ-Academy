# AGENTS.md

## Objetivo

Este repositório segue padrões para manter o ambiente de desenvolvimento organizado,
reprodutível e isolado entre os perfis **Pessoal** e **Trabalho**.

---

# Regras Gerais

## 1. Instalações por usuário

Sempre que existir uma alternativa, instalar ferramentas **somente no usuário logado**.

**Preferir:**

- ~/.local
- ~/.config
- ~/.cache
- ~/.ssh
- ~/.m2
- ~/.gradle
- ~/.npm
- ~/.nvm

**Evitar:**

- sudo npm install -g
- pip install global
- gem install global
- cargo install com sudo
- alterações globais em /usr/local quando não forem necessárias

---

## 2. Nunca usar sudo por conveniência

Antes de sugerir ou executar um comando com `sudo`, verificar se existe uma forma
equivalente de instalar apenas para o usuário atual.

A instalação global deve ser a última opção.

---

## 3. Isolamento dos perfis

Cada usuário do Linux deve possuir configurações independentes.

Inclui:

- Conta ChatGPT
- Codex
- IntelliJ IDEA
- Git
- GitHub
- SSH
- Maven
- Gradle
- Node
- Python
- Histórico do terminal

Nada deve depender do outro perfil.

---

## 4. Gerenciadores preferidos

Sempre utilizar gerenciadores por usuário.

| Tecnologia | Preferência |
|------------|-------------|
| Java | SDKMAN! |
| Node | NVM |
| Python | pyenv / pipx / venv |
| Rust | rustup |
| Go | GOPATH do usuário |
| Maven | ~/.m2 |
| Gradle | ~/.gradle |
| Git | ~/.gitconfig |
| SSH | ~/.ssh |
| Codex | Instalação em ~/.local/bin |

---

## 5. Documentação

Toda instalação deve registrar:

- Ferramenta
- Versão
- Motivo
- Data (quando relevante)
- Comandos utilizados
- Local de instalação

---

## 6. Antes de responder

Ao sugerir comandos ou configurações:

1. Priorizar instalação por usuário.
2. Evitar alterações globais.
3. Preservar a separação entre os perfis Pessoal e Trabalho.
4. Explicar quando uma instalação global for realmente necessária.

---

## 7. Objetivo final

O ambiente deve ser:

- Reprodutível
- Organizado
- Versionado
- Fácil de restaurar
- Independente entre usuários
