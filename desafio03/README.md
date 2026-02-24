# 🚀 CRUD de Clientes - API REST

[![Java](https://img.shields.io/badge/Java-21-orange)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.2-brightgreen)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)

> Projeto desenvolvido como parte do desafio do curso **Formação Java Professional** da **[DevSuperior](https://devsuperior.com.br)**

API REST completa para gerenciamento de clientes, implementando operações CRUD (Create, Read, Update, Delete) com validações, tratamento de exceções e boas práticas de desenvolvimento.

---

## 📋 Índice

- [Sobre o Projeto](#-sobre-o-projeto)
- [Tecnologias Utilizadas](#-tecnologias-utilizadas)
- [Funcionalidades](#-funcionalidades)
- [Modelo de Dados](#-modelo-de-dados)
- [Arquitetura](#-arquitetura)
- [Como Executar](#-como-executar)
- [Endpoints da API](#-endpoints-da-api)
- [Testes no Postman](#-testes-no-postman)
- [Tratamento de Erros](#-tratamento-de-erros)
- [Autor](#-autor)

---

## 💡 Sobre o Projeto

Este projeto implementa uma API RESTful para gerenciamento de clientes, seguindo as melhores práticas de desenvolvimento com Spring Boot. O sistema permite:

- Cadastro de novos clientes
- Consulta de clientes (individual e paginada)
- Atualização de dados de clientes
- Exclusão de clientes
- Validação de dados com mensagens customizadas
- Tratamento robusto de exceções

---

## 🛠 Tecnologias Utilizadas

### Backend
- **Java 21** - Linguagem de programação
- **Spring Boot 4.0.2** - Framework principal
- **Spring Data JPA** - Camada de persistência
- **Spring Web** - API REST
- **Bean Validation** - Validação de dados

### Banco de Dados
- **H2 Database** - Banco em memória para desenvolvimento e testes

### Ferramentas
- **Maven** - Gerenciamento de dependências
- **Postman** - Testes de API

---

## ✨ Funcionalidades

### CRUD Completo
- ✅ **Busca paginada** de clientes com ordenação
- ✅ **Busca individual** por ID
- ✅ **Cadastro** de novos clientes
- ✅ **Atualização** de dados de clientes existentes
- ✅ **Exclusão** de clientes

### Validações
- ✅ Nome obrigatório (não pode ser vazio)
- ✅ Data de nascimento não pode ser futura
- ✅ CPF único (não permite duplicados)

### Tratamento de Erros
- ✅ **404** - Recurso não encontrado(cliente inexistente)
- ✅ **409** - Conflito (CPF duplicado)
- ✅ **422** - Dados inválidos (com mensagens detalhadas por campo)

---

## 📊 Modelo de Dados

### Entidade: Client

| Campo | Tipo | Restrições |
|-------|------|------------|
| id | Long | Chave primária, auto-incremento |
| name | String | Obrigatório, não vazio |
| cpf | String | Único |
| income | Double | - |
| birthDate | LocalDate | Não pode ser data futura |
| children | Integer | - |

### Exemplo de JSON:
```json
{
  "id": 1,
  "name": "Freddie Mercury",
  "cpf": "12345678901",
  "income": 15000.0,
  "birthDate": "1946-09-05",
  "children": 0
}
```

---

## 🏗 Arquitetura

O projeto segue a **arquitetura em camadas** (Layered Architecture):

```
┌─────────────────────────────────────┐
│         Controller Layer            │  ← API REST (HTTP)
│   (ClientController)                │
└───────────────┬─────────────────────┘
                │
┌───────────────▼─────────────────────┐
│          Service Layer              │  ← Lógica de Negócio
│   (ClientService)                   │
└───────────────┬─────────────────────┘
                │
┌───────────────▼─────────────────────┐
│        Repository Layer             │  ← Acesso a Dados
│   (ClientRepository)                │
└───────────────┬─────────────────────┘
                │
┌───────────────▼─────────────────────┐
│          Database (H2)              │  ← Persistência
└─────────────────────────────────────┘
```

### Estrutura de Pacotes:
```
com.iolyOliveira.desafio03/
├── controllers/          # Endpoints REST
│   └── handlers/         # Tratamento global de exceções
├── dto/                  # Objetos de transferência de dados
├── entities/             # Entidades JPA
├── repositories/         # Interfaces JPA Repository
├── services/             # Lógica de negócio
│   └── exceptions/       # Exceções customizadas
```

---

## 🚀 Como Executar

### Pré-requisitos
- Java 21 ou superior
- Maven 4.0.2
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Passos:

1. **Clone o repositório:**
```bash
git clone git@github.com:iolymmoliveira/desafios_java_spring.git
cd desafio03
```

2. **Compile o projeto:**
```bash
mvn clean install
```

3. **Execute a aplicação:**
```bash
mvn spring-boot:run
```

Ou execute a classe principal `Desafio03Application.java` na sua IDE.

4. **Acesse a aplicação:**
- **API:** http://localhost:8080
- **H2 Console:** http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: *(deixe em branco)*

---

## 📡 Endpoints da API

### Base URL
```
http://localhost:8080
```

### Resumo dos Endpoints

| Método | Endpoint | Descrição | Status de Sucesso |
|--------|----------|-----------|-------------------|
| GET | `/clients` | Lista todos os clientes (paginado) | 200 OK |
| GET | `/clients/{id}` | Busca cliente por ID | 200 OK |
| POST | `/clients` | Cria novo cliente | 201 Created |
| PUT | `/clients/{id}` | Atualiza cliente existente | 200 OK |
| DELETE | `/clients/{id}` | Deleta cliente | 204 No Content |

---

### 📖 Detalhamento dos Endpoints

#### 1. Listar Clientes (Paginado)
```http
GET /clients?page=0&size=10&sort=name
```

**Resposta (200 OK):**
```json
{
  "content": [
    {
      "birthDate": "1950-06-19",
      "children": 2,
      "cpf": "66677788899",
      "id": 16,
      "income": 13900.0,
      "name": "Ann Wilson"
    },
    {
      "birthDate": "1962-02-06",
      "children": 2,
      "cpf": "32165498700",
      "id": 4,
      "income": 13800.0,
      "name": "Axl Rose"
    },
    ...
  ],
  "empty": false,
  "first": true,
  "last": false,
  "number": 0,
  "numberOfElements": 10,
  "pageable": {
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "unpaged": false
  },
  "size": 10,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "totalElements": 20,
  "totalPages": 2
}
```

---

#### 2. Buscar Cliente por ID
```http
GET /clients/1
```

**Resposta (200 OK):**
```json
{
  "birthDate": "1946-09-05",
  "children": 0,
  "cpf": "12345678901",
  "id": 1,
  "income": 15000.0,
  "name": "Freddie Mercury"
}
```

**Resposta (404 Not Found):**
```json
{
  "timestamp": "2026-02-14T14:34:10.068059Z",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/clients/999"
}
```

---

#### 3. Criar Novo Cliente
```http
POST /clients
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Marie Jane Whatson",
  "cpf": "12345678903",
  "income": 2000.0,
  "birthDate": "1976-02-02",
  "children": 2
}
```

**Resposta (201 Created):**
```json
{
  "birthDate": "1976-02-02",
  "children": 2,
  "cpf": "12345678903",
  "id": 21,
  "income": 2000.0,
  "name": "Marie Jane Whatson"
}
```

**Headers de Resposta:**
```
Location: http://localhost:8080/clients/21
```

---

#### 4. Atualizar Cliente
```http
PUT /clients/1
Content-Type: application/json
```

**Body:**
```json
{
  "name": "Farrokh Bulsara",
  "cpf": "12345678901",
  "income": 20000.0,
  "birthDate": "1946-09-05",
  "children": 1
}
```

**Resposta (200 OK):**
```json
{
  "birthDate": "1946-09-05",
  "children": 1,
  "cpf": "12345678901",
  "id": 1,
  "income": 20000.0,
  "name": "Farrokh Bulsara"
}
```

---

#### 5. Deletar Cliente
```http
DELETE /clients/1
```

**Resposta (204 No Content):**
*(Sem corpo de resposta)*

---

## 🧪 Testes no Postman

### Testes Manuais

#### ✅ Teste 1: Listar todos os clientes
```
GET http://localhost:8080/clients?page=0&size=10&sort=name
```
**Esperado:** 200 OK com lista de clientes

---

#### ✅ Teste 2: Buscar cliente existente
```
GET http://localhost:8080/clients/1
```
**Esperado:** 200 OK com dados do cliente

---

#### ✅ Teste 3: Buscar cliente inexistente
```
GET http://localhost:8080/clients/999
```
**Esperado:** 404 Not Found

---

#### ✅ Teste 4: Criar cliente válido
```
POST http://localhost:8080/clients
Content-Type: application/json

{
  "name": "João Silva",
  "cpf": "11122233344",
  "income": 5000.0,
  "birthDate": "1990-01-15",
  "children": 2
}
```
**Esperado:** 201 Created

---

#### ✅ Teste 5: Criar cliente com nome vazio
```
POST http://localhost:8080/clients
Content-Type: application/json

{
  "name": "",
  "cpf": "55566677788",
  "income": 3000.0,
  "birthDate": "1995-05-20",
  "children": 1
}
```
**Esperado:** 422 Unprocessable Entity
```json
{
  "timestamp": "2026-02-14T14:44:27.895009500Z",
  "status": 422,
  "error": "Dados inválidos",
  "path": "/clients",
  "fieldErrors": [
    {
      "fieldName": "name",
      "message": "Nome não pode ser vazio"
    }
  ]
}
```

---

#### ✅ Teste 6: Criar cliente com data futura
```
POST http://localhost:8080/clients
Content-Type: application/json

{
  "name": "João Sorrentino",
  "cpf": "99988877766",
  "income": 4000.0,
  "birthDate": "2030-12-31",
  "children": 0
}
```
**Esperado:** 422 Unprocessable Entity
```json
{
  "timestamp": "2026-02-14T14:45:14.121932600Z",
  "status": 422,
  "error": "Dados inválidos",
  "path": "/clients",
  "fieldErrors": [
    {
      "fieldName": "birthDate",
      "message": "Data de nascimento não pode ser futura"
    }
  ]
}
```

---

#### ✅ Teste 7: Criar cliente com CPF duplicado
```
POST http://localhost:8080/clients
Content-Type: application/json

{
  "name": "Charlie Robson",
  "cpf": "98765432100",
  "income": 5000.0,
  "birthDate": "1990-01-01",
  "children": 0
}
```
**Esperado:** 409 Conflict
```json
{
  "timestamp": "2026-02-14T14:47:33.470565800Z",
  "status": 409,
  "error": "CPF já cadastrado",
  "path": "/clients"
}
```

---

#### ✅ Teste 8: Atualizar cliente existente
```
PUT http://localhost:8080/clients/8
Content-Type: application/json

{
    "name": "Paul David Hewson",
    "cpf": "36925814700",
    "income": 17500.0,
    "birthDate": "1960-05-10",
    "children": 4
}
```
**Esperado:** 200 OK

---

#### ✅ Teste 9: Atualizar cliente inexistente
```
PUT http://localhost:8080/clients/22
Content-Type: application/json

{
    "name": "Paul David Hewson",
    "cpf": "36925814700",
    "income": 17500.0,
    "birthDate": "1960-05-10",
    "children": 4
}
```
**Esperado:** 404 Not Found

---

#### ✅ Teste 10: Deletar cliente existente
```
DELETE http://localhost:8080/clients/20
```
**Esperado:** 204 No Content

---

#### ✅ Teste 11: Deletar cliente inexistente
```
DELETE http://localhost:8080/clients/999
```
**Esperado:** 404 Not Found

---

## ⚠️ Tratamento de Erros

A API retorna respostas padronizadas para diferentes tipos de erro:

### 404 - Not Found
**Quando:** Recurso não existe (GET, PUT, DELETE)

```json
{
  "timestamp": "2026-02-13T22:30:00.000Z",
  "status": 404,
  "error": "Recurso não encontrado",
  "path": "/clients/999"
}
```

---

### 409 - Conflict
**Quando:** CPF duplicado

```json
{
  "timestamp": "2026-02-13T22:30:00.000Z",
  "status": 409,
  "error": "CPF já cadastrado",
  "path": "/clients"
}
```

---

### 422 - Unprocessable Entity
**Quando:** Dados inválidos (validação)

```json
{
  "timestamp": "2026-02-13T22:30:00.000Z",
  "status": 422,
  "error": "Dados inválidos",
  "path": "/clients",
  "errors": [
    {
      "fieldName": "name",
      "message": "Nome não pode ser vazio"
    },
    {
      "fieldName": "birthDate",
      "message": "Data de nascimento não pode ser futura"
    }
  ]
}
```

---

## 📊 Dados de Teste

O projeto inclui **20 clientes pré-cadastrados** com nomes de cantores de rock famosos:

- Freddie Mercury (Queen)
- Brian Johnson (AC/DC)
- Robert Plant (Led Zeppelin)
- Axl Rose (Guns N' Roses)
- Kurt Cobain (Nirvana)
- Joan Jett (Joan Jett & the Blackhearts)
- Stevie Nicks (Fleetwood Mac)
- Janis Joplin (Big Brother and the Holding Company)
- E mais...

---

## 📝 Regras de Validação

| Campo | Regra | Mensagem de Erro |
|-------|-------|------------------|
| name | Não pode ser vazio | "Campo obrigatório" |
| birthDate | Não pode ser data futura | "Não pode ser data futura" |
| cpf | Deve ser único | "CPF já cadastrado" |

---

## 🎯 Boas Práticas Implementadas

- ✅ Arquitetura em camadas (Controller → Service → Repository)
- ✅ DTOs para transferência de dados
- ✅ Validação com Bean Validation
- ✅ Tratamento global de exceções com `@ControllerAdvice`
- ✅ Exceções customizadas para regras de negócio
- ✅ Paginação e ordenação de resultados
- ✅ Códigos HTTP semânticos
- ✅ Mensagens de erro descritivas
- ✅ Transações com `@Transactional`
- ✅ Separação de responsabilidades (SRP)

---

## 📚 Aprendizados

Este projeto consolidou conhecimentos em:

- Spring Boot e ecossistema Spring
- API REST e princípios RESTful
- JPA/Hibernate e mapeamento objeto-relacional
- Validação de dados com Bean Validation
- Tratamento de exceções em APIs
- Paginação e ordenação de resultados
- Arquitetura em camadas
- Boas práticas de desenvolvimento

---

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

## 👨‍💻 Autor

**Ioly Oliveira**

- GitHub: [@iolymmoliveira](https://github.com/iolymmoliveira)
- LinkedIn: [iolymmoliveira](https://www.linkedin.com/in/iolymmoliveira/)
- Email: iolymmoliveira@gmail.com

---

## 🙏 Agradecimentos

- [DevSuperior](https://devsuperior.com.br) - Pelo excelente curso
- [Spring](https://spring.io) - Pelo framework incrível
- Comunidade Java/Spring - Pelo suporte e conhecimento compartilhado

---

<div align="center">
  Desenvolvido com ❤️ como parte da Formação Java Professional
</div>
