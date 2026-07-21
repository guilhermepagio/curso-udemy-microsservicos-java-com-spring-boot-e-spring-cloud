# 🚀 Como Executar

## 📋 Pré-requisitos

| Ferramenta | Versão | Necessidade |
|-----------|--------|-------------|
| **JDK** | 11+ | Obrigatório |
| **Maven** | 3.x | Obrigatório |
| **Docker** | 20.x+ | Necessário para profiles `dev`/`prod` com PostgreSQL |
| **Postman** | Qualquer | Recomendado (collection inclusa em `docs/`) |

---

## 🖥️ Execução Local (Profile Dev)

Neste modo, os serviços `hr-worker` e `hr-user` que usam banco de dados podem se conectar a uma instância PostgreSQL local.

### 1. Subir os bancos PostgreSQL locais

```bash
# Banco do hr-worker (porta 5432)
docker run -d \
  --name hr-worker-pg12-dev \
  -e POSTGRES_DB=db_hr_worker \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234567 \
  -p 5432:5432 \
  postgres:12-alpine

# Banco do hr-user (porta 5433)
docker run -d \
  --name hr-user-pg12-dev \
  -e POSTGRES_DB=db_hr_user \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234567 \
  -p 5433:5432 \
  postgres:12-alpine
```

### 2. Inicializar os schemas

Após os containers estarem rodando, execute os scripts DDL + seed:

```bash
# Schema do hr-worker
docker exec -i hr-worker-pg12-dev psql -U postgres -d db_hr_worker < hr-worker/create.sql

# Schema do hr-user
docker exec -i hr-user-pg12-dev psql -U postgres -d db_hr_user < hr-user/create.sql
```

### 3. Build do projeto

```bash
# Na raiz do projeto
mvn clean install -DskipTests
```

### 4. Iniciar os serviços (ordem importa!)

Os serviços devem ser iniciados na seguinte ordem para garantir que as dependências estejam disponíveis:

```bash
# 1️⃣ Config Server (deve ser o primeiro — fornece configs para todos)
java -jar hr-config-server/target/hr-config-server-0.0.1-SNAPSHOT.jar

# 2️⃣ Eureka Server (service discovery)
java -jar hr-eureka-server/target/hr-eureka-server-0.0.1-SNAPSHOT.jar

# 3️⃣ hr-worker (ajustar profile para dev)
java -jar hr-worker/target/hr-worker-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# 4️⃣ hr-user (ajustar profile para dev)
java -jar hr-user/target/hr-user-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# 5️⃣ hr-oauth
java -jar hr-oauth/target/hr-oauth-0.0.1-SNAPSHOT.jar

# 6️⃣ hr-payroll
java -jar hr-payroll/target/hr-payroll-0.0.1-SNAPSHOT.jar

# 7️⃣ API Gateway Zuul (último — precisa descobrir os serviços no Eureka)
java -jar hr-api-gateway-zuul/target/hr-api-gateway-zuul-0.0.1-SNAPSHOT.jar
```

> **💡 Dica:** No VS Code, use o arquivo [`launch.json`](launch.json) com as configurações de debug prontas para cada serviço. Copie-o para `.vscode/launch.json`.

### Sobrescrevendo o Config Server URI para desenvolvimento local

Por padrão, os `bootstrap.properties` apontam para `http://hr-config-server:8888` (hostname Docker). Para rodar localmente, sobrescreva:

```bash
java -jar hr-worker/target/hr-worker-0.0.1-SNAPSHOT.jar \
  --spring.cloud.config.uri=http://localhost:8888 \
  --spring.profiles.active=dev \
  --eureka.client.service-url.defaultZone=http://localhost:8761/eureka/
```

---

## 🐳 Execução com Docker (Profile Prod)

Neste modo, todos os serviços rodam em containers Docker conectados por uma rede interna. Os hostnames dos containers substituem `localhost`.

### 1. Criar a rede Docker

```bash
docker network create hr-net
```

### 2. Build das imagens

Antes de construir as imagens, faça o build Maven na raiz:

```bash
mvn clean install -DskipTests
```

Em seguida, construa cada imagem Docker:

```bash
# Config Server
docker build -t hr-config-server:v1 ./hr-config-server

# Eureka Server
docker build -t hr-eureka-server:v1 ./hr-eureka-server

# API Gateway Zuul
docker build -t hr-api-gateway-zuul:v1 ./hr-api-gateway-zuul

# Worker
docker build -t hr-worker:v1 ./hr-worker

# Payroll
docker build -t hr-payroll:v1 ./hr-payroll

# User
docker build -t hr-user:v1 ./hr-user

# OAuth
docker build -t hr-oauth:v1 ./hr-oauth
```

### 3. Subir os bancos PostgreSQL (na rede Docker)

```bash
# Banco do hr-worker
docker run -d \
  --name hr-worker-pg12 \
  --network hr-net \
  -e POSTGRES_DB=db_hr_worker \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234567 \
  -p 5432:5432 \
  postgres:12-alpine

# Banco do hr-user
docker run -d \
  --name hr-user-pg12 \
  --network hr-net \
  -e POSTGRES_DB=db_hr_user \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=1234567 \
  -p 5433:5432 \
  postgres:12-alpine
```

### 4. Inicializar os schemas no PostgreSQL

```bash
# Schema do hr-worker
docker exec -i hr-worker-pg12 psql -U postgres -d db_hr_worker < hr-worker/create.sql

# Schema do hr-user
docker exec -i hr-user-pg12 psql -U postgres -d db_hr_user < hr-user/create.sql
```

### 5. Subir os containers (respeitar a ordem!)

```bash
# 1️⃣ Config Server
docker run -d \
  --name hr-config-server \
  --network hr-net \
  -p 8888:8888 \
  hr-config-server:v1

# ⏳ Aguarde ~30s para o Config Server inicializar

# 2️⃣ Eureka Server
docker run -d \
  --name hr-eureka-server \
  --network hr-net \
  -p 8761:8761 \
  hr-eureka-server:v1

# ⏳ Aguarde ~30s para o Eureka inicializar

# 3️⃣ hr-worker (profile prod já configurado no bootstrap.properties)
docker run -d \
  --name hr-worker \
  --network hr-net \
  hr-worker:v1

# 4️⃣ hr-user
docker run -d \
  --name hr-user \
  --network hr-net \
  hr-user:v1

# 5️⃣ hr-oauth
docker run -d \
  --name hr-oauth \
  --network hr-net \
  hr-oauth:v1

# 6️⃣ hr-payroll
docker run -d \
  --name hr-payroll \
  --network hr-net \
  hr-payroll:v1

# 7️⃣ API Gateway Zuul
docker run -d \
  --name hr-api-gateway-zuul \
  --network hr-net \
  -p 8765:8765 \
  hr-api-gateway-zuul:v1
```

> **📌 Importante:** Os nomes dos containers (`--name`) devem coincidir com os hostnames configurados nos arquivos de propriedades (ex: `hr-config-server`, `hr-eureka-server`, `hr-worker-pg12`, `hr-user-pg12`).

### 6. Verificar se tudo está rodando

```bash
# Verificar containers
docker ps

# Acessar o dashboard do Eureka
# http://localhost:8761

# Testar o Config Server
# http://localhost:8888/hr-worker/default
```

---

## 📊 Resumo de Portas Expostas

| Serviço | Porta Host | Porta Container | Acessível externamente? |
|---------|-----------|----------------|------------------------|
| Config Server | 8888 | 8888 | Sim (para debug) |
| Eureka Server | 8761 | 8761 | Sim (dashboard) |
| API Gateway Zuul | **8765** | 8765 | ✅ **Ponto único de entrada** |
| PostgreSQL (hr-worker) | 5432 | 5432 | Sim (para debug/dev) |
| PostgreSQL (hr-user) | 5433 | 5432 | Sim (para debug/dev) |
| hr-worker | — | Aleatória | Não (via Gateway) |
| hr-payroll | — | Aleatória | Não (via Gateway) |
| hr-user | — | Aleatória | Não (via Gateway) |
| hr-oauth | — | Aleatória | Não (via Gateway) |
