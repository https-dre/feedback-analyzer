# Feedback Analyzer

![Powered by Gemini](https://img.shields.io/badge/Powered%20by-Google%20Gemini-8E75B2?style=flat&logo=googlegemini&logoColor=white)

![Java](https://img.shields.io/badge/Java-25%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.9-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## Processamento Inteligente em Lote com IA

Este projeto é uma solução de backend desenvolvida em Java com Spring Boot e Spring Batch para analisar e categorizar feedbacks de clientes em larga escala. A aplicação integra-se à API do Google Gemini (via LangChain4j) para realizar análise de sentimento e extração de insights automatizada.

O diferencial técnico do projeto reside na orquestração eficiente do processamento em lote. Foi implementada uma estratégia customizada de controle de fluxo e throttling no Spring Batch para respeitar os limites rígidos de Rate Limiting (RPM) dos modelos de IA, garantindo a ingestão contínua de dados sem erros de exaustão de recursos. A arquitetura segue boas práticas de design com uso de Java Records, DTOs blindados e projeções JPA otimizadas para performance.

## Como testar?

**Ops**:
Certifique-se de ter o [Docker](https://www.docker.com/get-started/) e o 
[Docker Compose](https://docs.docker.com/compose/install/) instalados em seu computador.

**1. Clone o Repositório:**

Abra seu Terminal/Command-Prompt e execute os comandos abaixo:

```bash
# Clone o repositório
git clone https://github.com/https-dre/feedback-analyzer.git

# Entre na pasta
cd feedback-analyzer
```

**2. Defina variáveis de ambiente:**

Em um arquivo `.env`:

```bash
DB_URL=postgresql://localhost:5432/feedback-analyzer # URL do Banco de Dados PostgreSQL
DB_USER=root # usuário do banco de dados
DB_PASSWORD=root # senha de acesso do seu usuário
GEMINI_API_KEY=... # uma chave de API do Gemini.
```

**Ops:**

- Não se esqueça de criar o banco corretamente;
- Seu usuário deve ter permissão para criar e gerir tabelas (necessário para as Migrations do Flyway).

**3. Verifique seu [application.yaml](./src/main/resources/application.yaml):**

Configurações recomendadas para testes:
```yaml
feedback:
    batch:
        chunk-size: 1
        sleep-duration: 15000 # Delay entre as chamadas para o Gemini (criado para não exceder os limites de API)
```

**4. Execute o projeto:**

Usando o docker-compose, podemos executar a aplicação e o banco de dados facilmente, usando o comando abaixo:

```bash
docker-compose up -d

```

Ao executar pela primeira vez, o Flyway e o Spring Batch devem executar suas migrações corretamente.

A documentação da API estará disponível em: http://localhost:8080/v1/docs