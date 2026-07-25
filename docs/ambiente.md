# Ambiente de desenvolvimento

Registro criado em 25/07/2026.

| Ferramenta | Versão | Motivo | Local |
| --- | --- | --- | --- |
| SDKMAN! | 5.23.0 | Gerenciar versões de Java por usuário | `~/.sdkman` |
| Eclipse Temurin JDK | 17.0.16 | Executar e compilar o backend | `~/.sdkman/candidates/java/17.0.16-tem` |
| NVM | 0.40.3 | Gerenciar versões de Node por usuário | `~/.nvm` |
| Node.js | 24.18.0 | Executar as ferramentas do Angular 22 | `~/.nvm/versions/node/v24.18.0` |
| npm | 11.16.0 | Instalar e executar dependências do frontend | Junto ao Node no `~/.nvm` |
| IntelliJ IDEA | 2025.3 (build IU-253.28294.334) | IDE do projeto, em instalação nativa sem sandbox Flatpak | `~/.local/opt/idea-IU-253.28294.334` |
| Lombok | 1.18.46 | Reduzir código repetitivo nas entidades e DTOs Java | Dependência Maven em `~/.m2/repository/org/projectlombok/lombok` |

## Comandos utilizados

```bash
curl -fsSL https://get.sdkman.io | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 17.0.16-tem

curl -fsSL https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.3/install.sh | bash
export NVM_DIR="$HOME/.nvm"
source "$NVM_DIR/nvm.sh"
nvm install 24
nvm alias default 24

npx --yes @angular/cli@22 new frontend --directory frontend --routing \
  --style=scss --ssr --standalone --skip-git --package-manager npm --defaults
npm install bootstrap @ng-bootstrap/ng-bootstrap bootstrap-icons

curl -fL https://download.jetbrains.com/idea/idea-2025.3.tar.gz \
  -o ~/.cache/JetBrains/downloads/idea-2025.3.tar.gz
sha256sum -c idea-2025.3.tar.gz.sha256
flatpak uninstall --user com.jetbrains.IntelliJ-IDEA-Community
tar -xzf ~/.cache/JetBrains/downloads/idea-2025.3.tar.gz -C ~/.local/opt
ln -sfn ~/.local/opt/idea-IU-253.28294.334 ~/.local/opt/intellij-idea
ln -sfn ~/.local/opt/intellij-idea/bin/idea.sh ~/.local/bin/idea

cd backend
./mvnw test
```

Nenhum comando utilizou `sudo` e nenhuma ferramenta de desenvolvimento foi instalada
globalmente para outros usuários.
