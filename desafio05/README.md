# 🛒 DS Commerce — Projeto Spring Boot Estruturado

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-3.4.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring%20Security-OAuth2-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white"/>
  <img src="https://img.shields.io/badge/H2-Database-003545?style=for-the-badge&logo=h2&logoColor=white"/>
  <img src="https://img.shields.io/badge/License-MIT-blue?style=for-the-badge"/>
</p>

> API REST de e-commerce desenvolvida com Java e Spring Boot, com autenticação OAuth2 + JWT, controle de acesso por perfis de usuário (ADMIN/CLIENT) e operações completas de produtos, categorias e pedidos.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Modelo de Dados](#-modelo-de-dados)
- [Arquitetura em Camadas](#-arquitetura-em-camadas)
- [Como Executar](#-como-executar)
- [Endpoints](#-endpoints)
- [Como Testar](#-como-testar)
- [Regras de Acesso](#-regras-de-acesso)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Dados de Teste](#-dados-de-teste)
- [Boas Práticas Implementadas](#-boas-práticas-implementadas)
- [Aprendizados](#-aprendizados)
- [Autor](#-autor)
- [Agradecimentos](#-agradecimentos)
- [Licença](#-licença)

---

## 💡 Sobre o Projeto

O **DS Commerce** é uma API REST de e-commerce desenvolvida como desafio prático do curso **Java Spring Professional** da [DevSuperior](https://devsuperior.com.br). O objetivo é construir uma aplicação Spring Boot estruturada em camadas, com autenticação e autorização baseadas em OAuth2 com tokens JWT.

O sistema permite que:
- Usuários **não logados** consultem produtos publicamente.
- Usuários **CLIENT** façam pedidos e visualizem apenas os seus próprios pedidos.
- Usuários **ADMIN** gerenciem o catálogo completo de produtos e visualizem qualquer pedido.

---

## 🛠 Tecnologias Utilizadas

### Backend
| Tecnologia                         | Versão         |
|------------------------------------|----------------|
| Java                               | 21             |
| Spring Boot                        | 3.4.x          |
| Spring Security                    | 6.x            |
| Spring OAuth2 Authorization Server | 1.x            |
| Spring Data JPA                    | 3.x            |
| Hibernate                          | 6.x            |
| Maven                              | Wrapper (mvnw) |

### Banco de Dados
| Tecnologia | Uso |
|---|---|
| H2 Database | Banco em memória para perfil `test` |

### Segurança
| Tecnologia | Uso |
|---|---|
| OAuth2 + JWT | Autenticação stateless com tokens auto-contidos |
| BCrypt | Hash de senhas |
| RSA 2048-bit | Assinatura dos tokens JWT |

---

## ✅ Funcionalidades

- ✅ Autenticação via OAuth2 com grant type customizado (`password`)
- ✅ Tokens JWT assinados com chave RSA
- ✅ Controle de acesso por roles (`ROLE_ADMIN`, `ROLE_CLIENT`)
- ✅ CRUD completo de produtos (restrito a ADMIN)
- ✅ Listagem pública de produtos e categorias
- ✅ Criação e consulta de pedidos
- ✅ Regra: CLIENT só acessa seus próprios pedidos
- ✅ Endpoint `/users/me` retorna dados do usuário logado
- ✅ Validação de dados com Bean Validation
- ✅ Tratamento centralizado de exceções
- ✅ CORS configurado
- ✅ Console H2 habilitado no perfil `test`

---

## 🗄 Modelo de Dados
```
  ┌──────────────────┐                    ┌──────────────────────────┐
  │     Product      │                    │           User           │
  │──────────────────│                    │──────────────────────────│
  │ id : Long        │                    │ id : Long                │
  │ name : String    │                    │ name : String            │
  │ description      │                    │ email : String           │
  │ price : Double   │                    │ phone : String           │
  │ imgUrl : String  │                    │ birthDate : LocalDate    │
  └────────┬─────────┘                    │ password : String        │
           │ 1..*           - products    │ roles : Role[]           │
           │                              └────────────┬─────────────┘
  ┌────────▼─────────┐                                 │ 1
  │     Category     │   ┌──────────────┐              │ client
  │──────────────────│   │  OrderItem   │   ┌──────────▼─────────┐
  │ id : Long        │   │──────────────│   │       Order        │
  │ name : String    │   │ quantity:Int │   │────────────────────│
  └──────────────────┘   │ price:Double │   │ id : Long          │
         1..*            └──────┬───────┘   │ moment : Instant   │
      - categories              │  *        │ status: OrderStatus│
                                └──────────►└─────────┬──────────┘
                                   - orders           │ 1
                                                      │ order
                                                 0..1 │
                                            ┌─────────▼──────────┐
                                            │      Payment       │
                                            │────────────────────│
                                            │ id : Long          │
                                            │ moment : Instant   │
                                            └────────────────────┘

  <<enum>> OrderStatus
  ┌──────────────────┐
  │ WAITING_PAYMENT  │
  │ PAID             │
  │ SHIPPED          │
  │ DELIVERED        │
  │ CANCELED         │
  └──────────────────┘

```

---

## 🏗 Arquitetura em Camadas
```
┌──────────────────────────────────────────────┐
│                    CLIENT                    │
│          (Postman / Frontend / etc)          │
└───────────────────────┬──────────────────────┘
                        │ HTTP Request
                        ▼
┌──────────────────────────────────────────────┐
│            SECURITY FILTER CHAIN             │
│  OAuth2 Token Validation / JWT Decode        │
│  ResourceServerConfig + AuthorizationServer  │
└───────────────────────┬──────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────┐
│             CONTROLLERS (REST)               │
│    @PreAuthorize / @RequestMapping           │
│  CategoryController  OrderController         │
│  ProductController   UserController          │
└───────────────────────┬──────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────┐
│                  SERVICES                    │
│  AuthService  OrderService  ProductService   │
│  CategoryService  UserService                │
│        Regras de negócio / DTOs              │
└───────────────────────┬──────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────┐
│               REPOSITORIES                   │
│            Spring Data JPA                   │
└───────────────────────┬──────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────┐
│              H2 DATABASE (test)              │
└──────────────────────────────────────────────┘
```

---

## 🚀 Como Executar

### Pré-requisitos

- Java 21
- Maven 3.4.x
- Git

### Clone o Repositório
```bash
git clone https://github.com/iolymmoliveira/desafios_java_spring.git
cd desafios_java_spring/desafio05
```

### Configure o Perfil

O projeto usa o perfil `test` com banco H2. Verifique o `application.properties`:
```properties
spring.profiles.active=test
```

### Execute com Maven
```bash
./mvnw spring-boot:run
```

Ou via sua IDE favorita, rodando a classe principal `DscommerceApplication.java`.

### Acesse o Console H2
```
URL:      http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User:     sa
Password: (deixe em branco)
```

---

## 📡 Endpoints

### 🔓 Autenticação

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| POST | `/oauth2/token` | Público | Gera token JWT |

**Request — Login:**
```bash   
  curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic bXljbGllbnRpZDpteWNsaWVudHNlY3JldA==' \
--data-urlencode 'username=maria@gmail.com' \
--data-urlencode 'password=123456' \
--data-urlencode 'grant_type=password'
```

**Response:**
```
{
  "access_token": "eyJraWQiOiI...",
  "token_type": "Bearer",
  "expires_in": 86400
}
```

---

### 📦 Produtos

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| GET | `/products` | Público | Lista todos os produtos (paginado) |
| GET | `/products/{id}` | Público | Busca produto por ID |
| POST | `/products` | ADMIN | Cria novo produto |
| PUT | `/products/{id}` | ADMIN | Atualiza produto |
| DELETE | `/products/{id}` | ADMIN | Remove produto |

**Request — Criar Produto (ADMIN):**
```
curl --location 'http://localhost:8080/products' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJraWQiOiJhOTdhMjc3OS1hZmY4LTQ3YzAtYTljMS1iZTUwNjQyZjk4MzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJteWNsaWVudGlkIiwiYXVkIjoibXljbGllbnRpZCIsIm5iZiI6MTc3MjQ3MTg5NSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzcyNTU4Mjk1LCJpYXQiOjE3NzI0NzE4OTUsImp0aSI6IjJiZjU2ZDM2LTE1YTAtNGU2Ni04MmMzLTg0MzdmYmU4MTQ2MCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQ0xJRU5UIl0sInVzZXJuYW1lIjoibWFyaWFAZ21haWwuY29tIn0.EEC6MGGSIzL-DwTtF2wHdkzHmur9o163yKBiVknnry28JCFWCqYBUVLZBPSvB4xPVQdB3fw7d7ykHcg0KaavHz4DbIeXN5cudJ1l1487tmsQvt328kcUnN4zif3FtB_YIBYxL8ojwF5CR00CVPo3ytDKQ7e2Banlkrp5eIcjdXwLciVDdaIFsU4xlSQNZd3QSTzW_4VBnTkMRM29t-YBvij8qd2IeR6grWmXpUHHctVTiqzoumHxleuBHjWOLFvpC6bUgdDY5RCpkYPJdRMdY_xHM6nIcXZV5XZAUoi0CwuAjzkce7AYIA11ix0eNZeA9E0ZhQjnPc5oCasT67zu0Q' \
--data '{
    "name": "Meu produto",
    "description": "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Qui ad, adipisci illum ipsam velit et odit eaque reprehenderit ex maxime delectus dolore labore, quisquam quae tempora natus esse aliquam veniam doloremque quam minima culpa alias maiores commodi. Perferendis enim",
    "imgUrl": "https://raw.githubusercontent.com/devsuperior/dscatalog-resources/master/backend/img/1-big.jpg",
    "price": 50.0,
    "categories": [
        {
            "id": 2
        },
        {
            "id": 3
        }
    ]
}
'
```

---

### 🗂 Categorias

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| GET | `/categories` | Público | Lista todas as categorias |

---

### 🛍 Pedidos

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| GET | `/orders/{id}` | ADMIN ou CLIENT (dono) | Busca pedido por ID |
| POST | `/orders` | CLIENT | Cria novo pedido |

**Request — Criar Pedido (CLIENT):**
```json
POST /orders
Authorization: Bearer {token_client}

curl --location 'http://localhost:8080/orders' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJraWQiOiJhOTdhMjc3OS1hZmY4LTQ3YzAtYTljMS1iZTUwNjQyZjk4MzQiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJteWNsaWVudGlkIiwiYXVkIjoibXljbGllbnRpZCIsIm5iZiI6MTc3MjQ3MTg5NSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwIiwiZXhwIjoxNzcyNTU4Mjk1LCJpYXQiOjE3NzI0NzE4OTUsImp0aSI6IjJiZjU2ZDM2LTE1YTAtNGU2Ni04MmMzLTg0MzdmYmU4MTQ2MCIsImF1dGhvcml0aWVzIjpbIlJPTEVfQ0xJRU5UIl0sInVzZXJuYW1lIjoibWFyaWFAZ21haWwuY29tIn0.EEC6MGGSIzL-DwTtF2wHdkzHmur9o163yKBiVknnry28JCFWCqYBUVLZBPSvB4xPVQdB3fw7d7ykHcg0KaavHz4DbIeXN5cudJ1l1487tmsQvt328kcUnN4zif3FtB_YIBYxL8ojwF5CR00CVPo3ytDKQ7e2Banlkrp5eIcjdXwLciVDdaIFsU4xlSQNZd3QSTzW_4VBnTkMRM29t-YBvij8qd2IeR6grWmXpUHHctVTiqzoumHxleuBHjWOLFvpC6bUgdDY5RCpkYPJdRMdY_xHM6nIcXZV5XZAUoi0CwuAjzkce7AYIA11ix0eNZeA9E0ZhQjnPc5oCasT67zu0Q' \
--data '{
    "items": [
        {
            "productId": 1,
            "quantity": 2
        },
        {
            "productId": 5,
            "quantity": 1
        }
    ]
}
'
```

---

### 👤 Usuários

| Método | Endpoint | Acesso | Descrição |
|--------|----------|--------|-----------|
| GET | `/users/me` | Autenticado | Retorna dados do usuário logado |

---

## 🧪 Como Testar

### 1. Obter token de CLIENT (Maria)
```
POST http://localhost:8080/oauth2/token
Basic Auth: myclientid / myclientsecret
Body (x-www-form-urlencoded):
  grant_type = password
  username   = maria@gmail.com
  password   = 123456
  
  curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic bXljbGllbnRpZDpteWNsaWVudHNlY3JldA==' \
--data-urlencode 'username=maria@gmail.com' \
--data-urlencode 'password=123456' \
--data-urlencode 'grant_type=password'
```

### 2. Obter token de ADMIN (Alex)
```
POST http://localhost:8080/oauth2/token
Basic Auth: myclientid / myclientsecret
Body (x-www-form-urlencoded):
  grant_type = password
  username   = alex@gmail.com
  password   = 123456
  
  curl --location 'http://localhost:8080/oauth2/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--header 'Authorization: Basic bXljbGllbnRpZDpteWNsaWVudHNlY3JldA==' \
--data-urlencode 'username=alex@gmail.com' \
--data-urlencode 'password=123456' \
--data-urlencode 'grant_type=password'
```

### 3. Cenários de Teste

| Cenário | Usuário | Endpoint | Resultado Esperado |
|---------|---------|----------|--------------------|
| Listar produtos sem login | — | GET /products | ✅ 200 OK |
| Buscar produto sem login | — | GET /products/1 | ✅ 200 OK |
| Criar produto sem login | — | POST /products | ❌ 401 Unauthorized |
| Criar produto como CLIENT | Maria | POST /products | ❌ 403 Forbidden |
| Criar produto como ADMIN | Alex | POST /products | ✅ 201 Created |
| Ver pedido próprio | Maria | GET /orders/1 | ✅ 200 OK |
| Ver pedido alheio | Maria | GET /orders/2 | ❌ 403 Forbidden |
| Ver qualquer pedido | Alex | GET /orders/1 | ✅ 200 OK |
| Ver usuário logado | Maria | GET /users/me | ✅ 200 OK |

---

## 🔐 Regras de Acesso
```
Endpoints Públicos:
  GET  /products        → Qualquer um
  GET  /products/{id}   → Qualquer um
  GET  /categories      → Qualquer um

Endpoints Autenticados (qualquer role):
  GET  /users/me        → Usuário logado

Endpoints CLIENT:
  POST /orders          → Criar pedido
  GET  /orders/{id}     → Somente o dono do pedido

Endpoints ADMIN:
  POST   /products      → Criar produto
  PUT    /products/{id} → Atualizar produto
  DELETE /products/{id} → Remover produto
  GET    /orders/{id}   → Ver qualquer pedido
```

---

## ⚠️ Tratamento de Erros

| Status | Situação |
|--------|----------|
| 400 | Dados inválidos (Bean Validation) |
| 401 | Token ausente ou inválido |
| 403 | Sem permissão para o recurso |
| 404 | Recurso não encontrado |
| 422 | Erro de negócio / entidade não processável |

---

## 👥 Dados de Teste

### Usuários

| Nome | E-mail | Senha | Role |
|------|--------|-------|------|
| Maria Brown | maria@gmail.com | 123456 | ROLE_CLIENT |
| Alex Green | alex@gmail.com | 123456 | ROLE_CLIENT, ROLE_ADMIN |

### Categorias

| ID | Nome |
|----|------|
| 1 | Livros |
| 2 | Eletrônicos |
| 3 | Computadores |

### Produtos (amostra)

| ID | Nome | Preço | Categoria |
|----|------|-------|-----------|
| 1 | The Lord of the Rings | R$ 90,50 | Livros |
| 2 | Smart TV | R$ 2.190,00 | Eletrônicos, Computadores |
| 3 | Macbook Pro | R$ 1.250,00 | Computadores |
| 4 | PC Gamer | R$ 1.200,00 | Computadores |
| 5 | Rails for Dummies | R$ 100,99 | Livros |

### Pedidos

| ID | Cliente | Status |
|----|---------|--------|
| 1 | Maria | PAID |
| 2 | Alex | DELIVERED |
| 3 | Maria | WAITING_PAYMENT |

---

## ✨ Boas Práticas Implementadas

- **DTO Pattern** — Separação entre entidades JPA e objetos de transferência
- **Arquitetura em camadas** — Controller → Service → Repository
- **Stateless** — Sessões desabilitadas, autenticação via JWT
- **Princípio do menor privilégio** — Endpoints protegidos por padrão
- **Senhas criptografadas** — BCrypt com salt automático
- **CORS configurado** — Controle de origens permitidas
- **Bean Validation** — Validação declarativa nas DTOs
- **Custom Grant Type** — Implementação do fluxo `password` para o OAuth2 Authorization Server 3.x

---

## 📚 Aprendizados

- Configuração do **Spring Authorization Server 1.x** com grant type customizado
- Diferença entre `hasRole()` e `hasAuthority()` no Spring Security
- Como o prefixo `ROLE_` é tratado automaticamente pelo `hasRole()`
- Importância do `@EnableMethodSecurity` para habilitar `@PreAuthorize`
- Configuração do conversor JWT para extrair authorities corretamente do token
- Estruturação de `SecurityFilterChain` com múltiplas regras de acesso

---

## 👨‍💻 Autor

**Ioly Oliveira** — Desenvolvedor Backend Java  
[GitHub](https://github.com/iolymmoliveira)

---

## 🙏 Agradecimentos

- [DevSuperior](https://devsuperior.com.br) pelo curso e material de apoio
- [Nélio Alves](https://github.com/acenelio) pela didática e estrutura do projeto de referência
- Comunidade Spring pelo excelente ecossistema

---

## 📄 Licença

Este projeto está sob a licença MIT.
```
MIT License — Copyright (c) 2024 Ioly Oliveira
```