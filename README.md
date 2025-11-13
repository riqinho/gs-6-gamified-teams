# 📚 Global Soluction Microserviços
Projeto apresentado ao professor @acnaweb com o tema de: Soluções gamificadas para motivação em equipes hibridas. Desenvolvido pelos alunos 
Renata & Rick da Turma 3SIR 2025.

---

## 👥 Integrantes da equipe
- **Renata Almeida Lima** — RM 552588  
- **Rick Alves Domingues** — RM 552438
  
---

## Finalidade da API
Mostrar como estruturar, automatizar e manter uma aplicação moderna seguindo boas práticas de DevOps, desde o código até o deploy da imagem.

---

## 🚀 Como executar a aplicação

### Executando via Docker Compose

```

docker compose up

```

👉 A aplicação ficará disponível em: http://localhost:8081

Obs: Não é necessário usar --build, pois todas as imagens já vêm do Docker Hub.

---

## Docker Hub Image
https://hub.docker.com/repository/docker/riqinho/gs_6_gamified_teams/general

---

## Workflows
### Versionamento
```

name: Versionamento 

on:
  push:
    branches:
      - main

permissions:
  contents: write
  issues: write
  pull-requests: write

jobs:
  release-please:
    runs-on: ubuntu-latest
    steps:
      - uses: googleapis/release-please-action@v4
        with:
          token: ${{ secrets.RELEASE_PLEASE_TOKEN }}
          release-type: maven

```
- Executado sempre que um push é feito na branch main.
- Analisa os commits e gera automaticamente uma nova versão seguindo Semantic Versioning (major.minor.patch).
- Cria ou atualiza: o CHANGELOG.md, a tag da release, e o Pull Request de release.
- Publica a release no GitHub usando o tipo maven, adequado para projetos Java.

### Continuous Integration
```

name: Continuous Integration

on:
  push:
    branches:
      - feature/**
      - release
      - hotfix

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout
        uses: actions/checkout@v5

      - name: Build da Aplicacao
        run: mvn clean package -DskipTests

      - name: Execução de Testes Unitários
        run: mvn test

      - name: Teste da Criacao da imagem
        run: docker build -t gs_6_gamified_teams .

```
- Executado sempre que um push é feito para as branches feature/**, release ou hotfix. Garente que toda alteração enviada seja validade antes de continuar o fluxo de desenvolvimento
- Realiza automaticamente:
  - Checkout do código
  - Build da aplicação usando Maven (mvn clean package)
  - Execução dos testes unitários (mvn test)
  - Teste de criação da imagem Docker, garantindo que a aplicação sobe corretamente dentro de um container

### Continuos Deployment
```

name: Continuous Delivery

on:
  pull_request:
    branches:
      - develop

jobs:
  build-and-push:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout
        uses: actions/checkout@v5

      - name: Login no DockerHub
        uses: docker/login-action@v3
        with:
          username: ${{ vars.DOCKERHUB_USERNAME }}
          password: ${{ secrets.DOCKERHUB_TOKEN }}

      - name: Construção da Imagem
        run: docker build -t riqinho/gs_6_gamified_teams:latest .

      - name: Push da Imagem no DockerHub
        run: docker push riqinho/gs_6_gamified_teams:latest

```
- Executado quando um Pull Request é aberto contra a branch develop.
- Realiza automaticamente:
  - Checkout do código
  - Autenticação no Docker Hub, usando credenciais seguras (GitHub Secrets)
  - Construção da imagem Docker da aplicação
  - Envio da imagem para o Docker Hub, sempre com a tag latest
