# 🛒 Desafio: Componentes e Injeção de Dependência

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5.10-brightgreen?style=for-the-badge&logo=spring" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Maven-3.6+-blue?style=for-the-badge&logo=apache-maven" alt="Maven">
</p>

## 📋 Sobre o Projeto

Sistema desenvolvido em Java com Spring Boot para calcular o valor total de pedidos, aplicando regras de desconto e cálculo de frete. O projeto demonstra conceitos fundamentais de **componentes** e **injeção de dependência** do Spring Framework.

Este desafio faz parte da **Formação Java Spring Professional** da [DevSuperior](https://devsuperior.com.br), capítulo de Componentes e Injeção de Dependência.

## 🎯 Funcionalidades

- ✅ Cálculo automático de desconto sobre pedidos
- ✅ Cálculo de frete baseado em regras de negócio
- ✅ Processamento de múltiplos pedidos
- ✅ Saída formatada no console

## 📊 Regras de Negócio

### Cálculo do Frete

| Valor do Pedido (sem desconto) | Valor do Frete |
|-------------------------------|----------------|
| Abaixo de R$ 100,00 | R$ 20,00 |
| De R$ 100,00 até R$ 199,99 | R$ 12,00 |
| R$ 200,00 ou mais | Grátis |

### Fórmula do Valor Total

```
Valor Total = (Valor Básico × (1 - Desconto/100)) + Frete
```

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura em camadas com separação de responsabilidades:

```
📦 com.devsuperior.desafio
 ┣ 📂 entities
 ┃ ┗ 📜 Order.java              # Entidade do pedido
 ┣ 📂 services
 ┃ ┣ 📜 OrderService.java       # Lógica de cálculo do pedido
 ┃ ┗ 📜 ShippingService.java    # Lógica de cálculo do frete
 ┗ 📜 DesafioApplication.java   # Classe principal
```

### Componentes

#### 🔹 Order (Entity)
```java
- code: Integer      // Código do pedido
- basic: Double      // Valor básico
- discount: Double   // Percentual de desconto
```

#### 🔹 ShippingService
- **Responsabilidade**: Calcular o valor do frete
- **Anotação**: `@Service`
- **Método**: `shipment(Order order)`

#### 🔹 OrderService
- **Responsabilidade**: Calcular o valor total do pedido
- **Anotação**: `@Service`
- **Dependência**: Injeta `ShippingService` via `@Autowired`
- **Método**: `total(Order order)`

## 🚀 Como Executar

### Pré-requisitos

- **Java JDK 21** ou superior
- **Maven 3.6+**
- **Git** (para clonar o repositório)

### Passos

1. **Clone o repositório**
```bash
git clone git@github.com:iolymmoliveira/desafios_java_spring.git
```
Entre na pasta do repositório
```cd desafios_java_spring```

Entre na pasta específica do desafio
```cd desafio01```

2. **Execute com Maven**
```bash
mvn spring-boot:run
```

**OU**

3. **Compile e execute o JAR**
```bash
mvn clean package
java -jar target/desafio01-0.0.1-SNAPSHOT.jar
```

## 📝 Exemplos de Execução

### Exemplo 1
**Entrada:**
- Código: 1034
- Valor Básico: R$ 150,00
- Desconto: 20%

**Saída:**
```
Pedido código 1034
Valor total: R$ 132,00
```

**Cálculo:** (150,00 × 0,80) + 12,00 = 132,00

---

### Exemplo 2
**Entrada:**
- Código: 2282
- Valor Básico: R$ 800,00
- Desconto: 10%

**Saída:**
```
Pedido código 2282
Valor total: R$ 720,00
```

**Cálculo:** (800,00 × 0,90) + 0,00 = 720,00

---

### Exemplo 3
**Entrada:**
- Código: 1309
- Valor Básico: R$ 95,90
- Desconto: 0%

**Saída:**
```
Pedido código 1309
Valor total: R$ 115,90
```

**Cálculo:** (95,90 × 1,00) + 20,00 = 115,90

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem de programação
- **Spring Boot 3.5.10** - Framework para aplicações Java
- **Spring Web** - Para aplicações web (REST APIs)
- **Maven** - Gerenciamento de dependências e build

## 💡 Conceitos Aplicados

- ✅ **Componentes Spring** (`@Service`)
- ✅ **Injeção de Dependência** (`@Autowired`)
- ✅ **Inversão de Controle (IoC)**
- ✅ **Separação de Responsabilidades**
- ✅ **CommandLineRunner**
- ✅ **Clean Code**

## 📂 Estrutura de Pastas

```
desafio-componentes/
├── src/
│   └── main/
│       └── java/
│           └── com/devsuperior/desafio/
│               ├── entities/
│               │   └── Order.java
│               ├── services/
│               │   ├── OrderService.java
│               │   └── ShippingService.java
│               └── DesafioApplication.java
├── pom.xml
├── .gitignore
└── README.md
```

## 🎓 Aprendizados

Este projeto permitiu praticar:

1. **Criação de projetos Spring Boot** do zero
2. **Configuração de componentes** com anotações Spring
3. **Implementação de injeção de dependência** entre serviços
4. **Separação de responsabilidades** entre camadas
5. **Boas práticas** de desenvolvimento Java

## 📄 Licença

Este projeto foi desenvolvido para fins educacionais como parte da Formação Desenvolvedor Moderno.

## 👨‍💻 Autor

**Ioly Oliveira** - 
Desenvolvido como parte do desafio da **DevSuperior** - Formação Desenvolvedor Moderno

---

<p align="center">
  Feito com ☕ e Java
</p>
