# 📮 API — Endpoints & Collection Postman

## Importando no Postman

O diretório [`docs/`](.) contém arquivos prontos para importar no Postman:

| Arquivo | Descrição |
|---------|-----------|
| [`postman-collection.json`](postman-collection.json) | Collection com todos os endpoints organizados por serviço |
| [`postman-environment.json`](postman-environment.json) | Variáveis de ambiente (url-base, token, credentials) |

## Variáveis de ambiente

| Variável | Descrição | Valor sugerido |
|----------|-----------|---------------|
| `url-base-gateway-zuul` | URL base do Gateway | `http://localhost:8765` |
| `url-base-config-server` | URL base do Config Server | `http://localhost:8888` |
| `client-name` | Nome do client OAuth2 | `myappname123` |
| `client-secret` | Secret do client OAuth2 | `myappsecret123` |
| `username` | Email do usuário para login | `leia@gmail.com` |
| `token` | JWT (preenchido automaticamente após login) | — |

> **💡 Dica:** O request de **Login** no Postman possui um script de teste que salva automaticamente o `access_token` na variável `token` do environment.

---

## Endpoints disponíveis

Todos os endpoints são acessados através do **API Gateway Zuul** (`http://localhost:8765`).

### 🔑 hr-oauth

| Endpoint | Método | Descrição | Auth |
|----------|--------|-----------|------|
| `/hr-oauth/oauth/token` | POST | Login — gera JWT | Basic (client credentials) |
| `/hr-oauth/users/search?email=` | GET | Buscar usuário por email | Bearer Token |

### 👷 hr-worker

| Endpoint | Método | Descrição | Auth |
|----------|--------|-----------|------|
| `/hr-worker/workers` | GET | Listar todos os workers | Bearer Token (OPERATOR/ADMIN) |
| `/hr-worker/workers/{id}` | GET | Buscar worker por ID | Bearer Token (OPERATOR/ADMIN) |
| `/hr-worker/workers/configs` | GET | Exibir configurações carregadas | Bearer Token (OPERATOR/ADMIN) |
| `/hr-worker/actuator/refresh` | POST | Recarregar configurações do Config Server | Bearer Token (ADMIN) |

### 💰 hr-payroll

| Endpoint | Método | Descrição | Auth |
|----------|--------|-----------|------|
| `/hr-payroll/payments/{id}/days/{days}` | GET | Calcular pagamento de um worker | Bearer Token (ADMIN) |

### 👤 hr-user

| Endpoint | Método | Descrição | Auth |
|----------|--------|-----------|------|
| `/hr-user/users/{id}` | GET | Buscar usuário por ID | Bearer Token (ADMIN) |
| `/hr-user/users/search?email=` | GET | Buscar usuário por email | Bearer Token (ADMIN) |

### ⚙️ hr-config-server (acesso direto — porta 8888)

| Endpoint | Método | Descrição |
|----------|--------|-----------|
| `/hr-worker/default` | GET | Ver configs do worker (profile default) |
| `/hr-worker/test` | GET | Ver configs do worker (profile test) |
| `/hr-worker/dev` | GET | Ver configs do worker (profile dev) |
| `/hr-user/dev` | GET | Ver configs do user (profile dev) |
| `/hr-user/prod` | GET | Ver configs do user (profile prod) |
