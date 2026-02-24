# 📊 Consulta Vendas

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"/>
  <img alt="Spring Boot" src="https://img.shields.io/badge/Spring_Boot-2.7.3-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white"/>
  <img alt="JPA" src="https://img.shields.io/badge/Spring_Data_JPA-Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white"/>
  <img alt="H2" src="https://img.shields.io/badge/H2-Database-1021FF?style=for-the-badge&logo=h2&logoColor=white"/>
  <img alt="Maven" src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white"/>
  <img alt="License" src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge"/>
</p>

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Modelo de Dados](#-modelo-de-dados)
- [Arquitetura em Camadas](#-arquitetura-em-camadas)
- [Como Executar](#-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Testes no Postman](#-testes-no-postman)
- [Regras de Validação](#-regras-de-validação)
- [Boas Práticas Implementadas](#-boas-práticas-implementadas)
- [Aprendizados](#-aprendizados)
- [Autor](#-autor)
- [Agradecimentos](#-agradecimentos)

---

## 💡 Sobre o Projeto

Este projeto é um desafio prático da **Formação Java Professional** da [DevSuperior](https://devsuperior.com.br), no módulo de **Back end**, capítulo sobre **JPA, consultas SQL e JPQL**.

O sistema gerencia **vendas (Sale)** e **vendedores (Seller)**, expondo uma API REST capaz de:

- Gerar um **relatório paginado de vendas** filtrado por período e nome do vendedor
- Gerar um **sumário de vendas por vendedor** totalizando o valor vendido no período

O foco principal é a implementação de **consultas JPQL customizadas** com Spring Data JPA, com tratamento de parâmetros opcionais e paginação nativa do Spring.

---

## 🛠 Tecnologias Utilizadas

### Back end
| Tecnologia | Versão | Finalidade |
|---|--------|---|
| Java | 17     | Linguagem principal |
| Spring Boot | 2.7.3  | Framework de aplicação |
| Spring Data JPA | —      | Abstração de persistência |
| Hibernate | —      | ORM / implementação JPA |
| H2 Database | —      | Banco em memória para testes |
| Maven | —      | Gerenciamento de dependências |

### Ferramentas
| Ferramenta | Finalidade |
|---|---|
| Postman | Testes manuais da API |
| IntelliJ IDEA / Eclipse | Desenvolvimento |
| Git / GitHub | Versionamento |

---

## ✅ Funcionalidades

- ✅ Busca de venda por ID
- ✅ Relatório paginado de vendas com filtros opcionais de:
  - Data inicial
  - Data final
  - Trecho do nome do vendedor (case-insensitive)
- ✅ Sumário de vendas por vendedor com soma total no período
- ✅ Tratamento automático de datas ausentes (padrão: últimos 12 meses)
- ✅ Paginação e ordenação via query params do Spring (`page`, `size`, `sort`)

---

## 🗃 Modelo de Dados

```
┌────────────────────────┐         ┌────────────────────────┐
│         tb_seller      │         │        tb_sales        │
├────────────────────────┤         ├────────────────────────┤
│ id          BIGINT PK  │◄────┐   │ id          BIGINT PK  │
│ name        VARCHAR    │     └───│ seller_id   BIGINT FK  │
│ email       VARCHAR    │         │ visited     INTEGER    │
│ phone       VARCHAR    │         │ deals       INTEGER    │
└────────────────────────┘         │ amount      DOUBLE     │
                                   │ date        DATE       │
                                   └────────────────────────┘

Relacionamento: Um Seller possui muitas Sales (OneToMany / ManyToOne)
```

### Entidades

**Seller** — representa o vendedor:
```
id | name | email | phone
```

**Sale** — representa uma venda:
```
id | visited | deals | amount | date | seller_id (FK)
```

---

## 🏗 Arquitetura em Camadas

```
┌──────────────────────────────────────────────────────┐
│                   CLIENT (Postman / Browser)          │
└───────────────────────┬──────────────────────────────┘
                        │  HTTP Request
                        ▼
┌──────────────────────────────────────────────────────┐
│              CONTROLLER LAYER                        │
│  SaleController                                      │
│  • Recebe parâmetros como String                     │
│  • Delega ao Service                                 │
│  • Retorna ResponseEntity                            │
└───────────────────────┬──────────────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────────────┐
│              SERVICE LAYER                           │
│  SaleService                                         │
│  • Trata datas ausentes (lógica de negócio)          │
│  • Chama o Repository                                │
│  • Retorna DTOs                                      │
└───────────────────────┬──────────────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────────────┐
│              REPOSITORY LAYER                        │
│  SaleRepository (JpaRepository + JPQL)               │
│  • searchReport(...) → Page<SaleReportDTO>           │
│  • searchSummary(...) → List<SaleSummaryDTO>         │
└───────────────────────┬──────────────────────────────┘
                        │
                        ▼
┌──────────────────────────────────────────────────────┐
│              DATA LAYER                              │
│  H2 In-Memory Database                               │
│  tb_seller | tb_sales                                │
└──────────────────────────────────────────────────────┘
```

### DTOs utilizados

| DTO | Campos | Usado em |
|---|---|---|
| `SaleMinDTO` | id, amount, date | GET `/sales/{id}` |
| `SaleReportDTO` | id, date, amount, sellerName | GET `/sales/report` |
| `SaleSummaryDTO` | sellerName, total | GET `/sales/summary` |

---

## 🚀 Como Executar

### Pré-requisitos

- [Java 17+](https://adoptium.net/) instalado
- [Maven 3.8+](https://maven.apache.org/) instalado (ou use o wrapper `./mvnw`)
- [Git](https://git-scm.com/) instalado

### Passo a Passo

**1. Clone o repositório**
```bash
git clone git@github.com:iolymmoliveira/desafios_java_spring.git
```

**2. Acesse o módulo do desafio** *(se o repositório contiver múltiplos projetos)*
```bash
cd desafio04
```

**3. Execute a aplicação**
```bash
# Com Maven Wrapper (recomendado)
./mvnw spring-boot:run

# Ou com Maven instalado globalmente
mvn spring-boot:run
```

**4. A API estará disponível em:**
```
http://localhost:8080
```

**5. (Opcional) Acesse o console H2:**
```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa
Password: (vazio)
```

---

## 📡 Endpoints da API

### Base URL
```
http://localhost:8080
```

---

### 🔍 Buscar venda por ID

```http
GET /sales/{id}
```

**Resposta de sucesso `200 OK`:**
```json
{
  "id": 1,
  "amount": 18196.0,
  "date": "2025-06-16"
}
```

---

### 📋 Relatório de Vendas (paginado)

```http
GET /sales/report
```

**Resposta de sucesso `200 OK`:**
```json
{
  "content": [
    {
      "id": 1,
      "date": "2025-06-16",
      "amount": 18196.0,
      "sellerName": "Loki Odinson"
    },
    {
      "id": 2,
      "date": "2025-06-14",
      "amount": 4255.0,
      "sellerName": "Logan"
    },
    {
      "id": 3,
      "date": "2025-06-14",
      "amount": 13249.0,
      "sellerName": "Padme"
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageSize": 20,
    "pageNumber": 0,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalPages": 2,
  "totalElements": 37,
  "size": 20,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "numberOfElements": 20,
  "first": true,
  "empty": false
}
```

---

### 📊 Sumário de Vendas por Vendedor

```http
GET /sales/summary
```

**Resposta de sucesso `200 OK`:**
```json
[
  { "sellerName": "Anakin", "total": 110571.0 },
  { "sellerName": "Logan", "total": 83587.0 },
  { "sellerName": "Loki Odinson", "total": 150597.0 },
  { "sellerName": "Padme", "total": 135902.0 },
  { "sellerName": "Thor Odinson", "total": 144896.0 }
]
```

---

## 🧪 Testes no Postman


---

### Teste 1 — Sumário por período específico

```
GET http://localhost:8080/sales/summary?minDate=2022-01-01&maxDate=2022-06-30
```

**Resultado esperado:**
```json
[
  { "sellerName": "Anakin", "total": 110571.0 },
  { "sellerName": "Logan", "total": 83587.0 },
  { "sellerName": "Loki Odinson", "total": 150597.0 },
  { "sellerName": "Padme", "total": 135902.0 },
  { "sellerName": "Thor Odinson", "total": 144896.0 }
]
```

---

### Teste 2 — Sumário sem parâmetros (últimos 12 meses)

```
GET http://localhost:8080/sales/summary
```

Retorna o sumário dos últimos 12 meses a partir da data atual do sistema.

---

### Teste 3 — Relatório sem parâmetros (últimos 12 meses)

```
GET http://localhost:8080/sales/report
```

Retorna o relatório paginado dos últimos 12 meses.

---

### Teste 4 — Relatório com filtros completos

```
GET http://localhost:8080/sales/report?minDate=2022-05-01&maxDate=2022-05-31&name=odinson
```

**Resultado esperado:**
```json
{
  "content": [
    { "id": 9,  "date": "2022-05-22", "amount": 19476.0, "sellerName": "Loki Odinson" },
    { "id": 10, "date": "2022-05-18", "amount": 20530.0, "sellerName": "Thor Odinson" },
    { "id": 12, "date": "2022-05-06", "amount": 21753.0, "sellerName": "Loki Odinson" }
  ],
  "totalElements": 3,
  "totalPages": 1
}
```

> 💡 O filtro `name=odinson` é **case-insensitive**: encontra tanto "Loki Odinson" quanto "Thor Odinson".

---

## 📏 Regras de Validação

| Parâmetro | Regra |
|---|---|
| `maxDate` ausente | Usa a data atual do sistema |
| `minDate` ausente | Usa `maxDate - 1 ano` |
| `name` ausente | Considera texto vazio (retorna todos os vendedores) |
| `name` case-insensitive | `UPPER(seller.name) LIKE UPPER('%name%')` |
| Paginação | Controlada pelos parâmetros `page`, `size`, `sort` do Spring |

---

## ✨ Boas Práticas Implementadas

- **Arquitetura em camadas** bem definida (Controller → Service → Repository)
- **DTOs** para não expor entidades diretamente na API
- **JPQL com construtor DTO** (`new com.devsuperior...DTO(...)`) evitando mapeamento manual
- **Paginação nativa** com `Pageable` do Spring Data
- **Parâmetros opcionais** tratados na camada de serviço, não no controller
- **`ResponseEntity<T>` tipado** nos retornos do controller
- **Separação de responsabilidades**: lógica de negócio no Service, consultas no Repository

---

## 📚 Aprendizados

- Criação de **consultas JPQL customizadas** com `@Query` e projeção via construtor de DTO
- Uso de `BETWEEN`, `LIKE`, `SUM` e `GROUP BY` em JPQL
- Tratamento de parâmetros opcionais com valores padrão dinâmicos
- Integração de `Pageable` em queries customizadas no Spring Data JPA
- Diferença entre retorno `Page<T>` (com metadados de paginação) e `List<T>`
- Uso de `LocalDate.ofInstant()` e `minusYears()` para manipulação de datas

---

## 👤 Autor

**Ioly M. M. Oliveira**

[![GitHub](https://img.shields.io/badge/GitHub-iolymmoliveira-181717?style=for-the-badge&logo=github)](https://github.com/iolymmoliveira)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-iolymmoliveira-0A66C2?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/iolymmoliveira)
[![Gmail](https://img.shields.io/badge/Gmail-iolymmoliveira@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:iolymmoliveira@gmail.com)

---

## 🙏 Agradecimentos

- [DevSuperior](https://devsuperior.com.br) — pelo conteúdo de alta qualidade e pelo desafio proposto
- [Nélio Alves](https://github.com/acenelio) — instrutor e criador do curso
- Comunidade Java/Spring - Pelo suporte e conhecimento compartilhado


---

<div align="center">
  Desenvolvido com ❤️ como parte da Formação Java Professional
</div>

---