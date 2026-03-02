# 🚀 Java Spring Professional

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Maven](https://img.shields.io/badge/Apache_Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=spring-security&logoColor=white)


Este repositório é um **monorepo** que documenta a minha jornada de especialização no ecossistema Spring. O foco principal é a transição de conceitos fundamentais de orientação a objetos e injeção de dependência para a construção de sistemas distribuídos, seguros e otimizados.

O projeto culmina no **Desafio 05**, que consolida o conhecimento em arquitetura de software, segurança com OAuth2/JWT e performance de banco de dados.

---

## 📌 Índice
1. [Trilha de Evolução Técnica](#-trilha-de-evolução-técnica)
2. [O Ápice: Desafio 05](#-o-ápice-desafio-05)
3. [Arquitetura e Design Patterns](#-arquitetura-e-design-patterns)
4. [Segurança e Resiliência](#-segurança-e-resiliência)
5. [Como Obter o Monorepo](#-como-obter-o-monorepo)
6. [Boas Práticas Implementadas](#-boas-práticas-implementadas)
7. [Autor](#-autor)
8. [Agradecimentos](#-agradecimentos)

---

## 🛣 Trilha de Evolução Técnica

Abaixo, os marcos de aprendizado organizados por competência técnica adquirida, mapeados conforme a progressão da formação:

| Fase | Foco Principal | Competências Dominadas |
| :--- | :--- | :--- |
| **01** | **Fundamentos & DI** | Inversão de Controle (IoC), Injeção de Dependência e desacoplamento de componentes de alto nível. |
| **02** | **Domínio & ORM** | Modelagem de domínios complexos, relacionamentos (1:1, 1:N, N:N) e mapeamento Objeto-Relacional com JPA/Hibernate. |
| **03** | **API REST & Web** | Arquitetura em camadas, padrões DTO, tratamento global de exceções e validação semântica com Bean Validation. |
| **04** | **Otimização & JPQL** | Performance em consultas, prevenção do problema N+1 (Join Fetch), consultas paginadas e SQL avançado. |
| **05** | **Security & Auth** | Implementação de fluxos OAuth2 e JWT, controle de acesso granular e proteção de recursos. |
| **06** | **Cloud & DevOps** | Conteinerização com Docker, perfis de ambiente (Dev/Prod) e automação de deploy com CI/CD. |

---

## 🏆 O Ápice: Desafio 05

O **Desafio 05** é o ponto de convergência de toda a trilha de aprendizado. Ele consiste no desenvolvimento de um ecossistema back-end completo, onde o foco central foi a resolução de problemas reais de escalabilidade, integridade de dados e segurança, aplicando padrões rigorosos de desenvolvimento corporativo.

### Core Features ✅
* **Persistência de Dados Otimizada:** Implementação de queries performáticas para grandes volumes.
* **Segurança Robusta:** Gestão de acesso baseada em perfis de usuário (Admin/Client).
* **Tratamento de Exceções Semântico:** Respostas HTTP precisas para cada erro de negócio ou de sistema.
* **Integridade de Dados:** Camada de validação rigorosa que garante a consistência do estado da aplicação.

---

## 🏗 Arquitetura e Design Patterns

O ecossistema utiliza a **Arquitetura em Camadas (Layered Architecture)**, garantindo a separação de responsabilidades (SRP) e facilitando a manutenção.

```text
       [ Camada de Rest / Controller ]  <-- Entrada e Saída (JSON/DTO)
                  |
       [ Camada de Serviço / Business ] <-- Regras de Negócio e Transações
                  |
       [ Camada de Acesso a Dados ]     <-- Repositories e JPQL/SQL
                  |
       [ Banco de Dados Relacional ]    <-- Persistência e Integridade
 ```
---

## 🛡 Segurança e Resiliência
Para projetos de nível pleno, a segurança e a performance são pilares fundamentais:

Autenticação Stateless: Uso de JWT permitindo escalabilidade horizontal da API.

RBAC (Role-Based Access Control): Controle programático de acesso em nível de endpoint.

Otimização de Banco: Uso estratégico de cache de leitura e carregamento tardio (Lazy Loading) controlado.

Transacionalidade: Garantia de atomicidade em operações complexas através da anotação @Transactional.

---

## 📦 Como Obter o Monorepo
Para acessar todos os desafios e o código-fonte completo:

```bash
# Clone o repositório completo
git clone [https://github.com/iolymmoliveira/desafios_java_spring.git](https://github.com/iolymmoliveira/desafios_java_spring.git)

# Navegue entre as pastas dos desafios
cd desafios_java_spring
```

---

## ✨ Boas Práticas Implementadas
Padrão DTO: Isolamento total das entidades JPA, evitando acoplamento entre o banco e a API.

Clean Code: Nomenclatura expressiva e métodos de responsabilidade única.

Global Exception Handling: Centralização do tratamento de erros através de @ControllerAdvice.

Database Seeding: Scripts automatizados para preparação de ambientes de teste e desenvolvimento.

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

---
