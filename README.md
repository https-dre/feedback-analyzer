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

**1. Comece clonando o repositório:**

```bash
# Clone o repositório
git clone https://github.com/https-dre/feedback-analyzer.git

# Entre na pasta
cd feedback-analyzer
```

**2. Defina variáveis de ambiente necessárias:**
 - DB_URL: "postgres://host:port/db_name" - URL do Banco de Dados PostgreSQL
 - DB_USER - usuário do banco de dados
 - DB_PASSWORD - senha de acesso do seu usuário
 - GEMINI_API_KEY - uma chave de API do Gemini.

Ops:

- Não se esqueça de criar o banco corretamente
- Certifique-se que seu usuário tem permissão para criar e gerir tabelas (necessário para as Migrations do Flyway)

**2. Verifique seu `application.yaml`:**

```yaml
feedback:
    batch:
        chunk-size: 1
        sleep-duration: 15000 # Delay entre as chamadas para o Gemini (criado para não exceder os limites de API)
```

**3. Execute o projeto:**

```bash
# Execute com Maven Wrapper (Linux/Mac)
./mvnw spring-boot:run

# Ou no Windows
.\mvnw.cmd spring-boot:run
```

Ao executar pela primeira vez, o Flyway e o Spring Batch devem executar suas migrações corretamente.

A Api estará executando em: http://localhost:8080/