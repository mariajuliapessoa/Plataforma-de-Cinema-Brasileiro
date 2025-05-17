# **Plataforma de Cinema Brasileiro**

## **Sobre o Projeto**
A **Plataforma de Cinema Brasileiro** tem como objetivo proporcionar uma experiência inovadora para a descoberta, recomendação e análise de filmes nacionais. Com funcionalidades avançadas, a plataforma oferecerá um sistema inteligente de recomendação, curadoria especializada e gamificação para engajar os usuários na valorização do cinema brasileiro.

---
## **Tecnologias Utilizadas**
- **Back-end:** Java, Spring Boot, JPA
- **Front-end:** React
- **Banco de Dados:** Banco relacional
- **Metodologias:** Domain-Driven Design (DDD) com os níveis estratégico, tático e operacional
- **Testes e Automação:** Cenários BDD automatizados

## **Estrutura do Projeto**
### 📁 backend/ – Camada de back-end desenvolvida em Spring Boot, estruturada segundo os princípios de Domain-Driven Design (DDD), com as seguintes camadas:

### 📁 domain/ – Núcleo da lógica de negócio. Contém:

*   `entities/` – Entidades ricas em comportamento, representando o modelo de domínio.
*   `repositories/` – Contratos (interfaces) para persistência de dados.
*   `services/` – Serviços de domínio responsáveis por regras que envolvem múltiplas entidades.
*   `enums/` – Tipos enumerados usados pelo domínio (ex: TipoNotificacao, Cargo, etc.).

### 📁 application/ – Orquestra os casos de uso da aplicação. Atua como intermediária entre o domínio e a infraestrutura. Define a lógica de aplicação.

### 📁 infrastructure/ – Implementações técnicas. Contém:

*   `jpa/entities/` – Mapeamentos JPA para as entidades persistidas no banco.
*   `jpa/repository/` – Implementações das interfaces de repositórios com Spring Data JPA.
*   `mappers/` – Conversores entre entidades do domínio e entidades JPA.

### 📁 presentation/ – Camada de entrada da aplicação (HTTP/REST). Inclui:

*   `controllers/` – Controladores REST que expõem os endpoints da API.
*   `dtos/` – Objetos de transferência de dados entre cliente e servidor (Request/Response).

### 📁 config/ – Configurações de segurança, CORS, JWT, etc.

---

## 📁 frontend/ – Aplicação cliente desenvolvida em NextJS, responsável pela interface com o usuário.

---

### 📁 docs/ – Documentação geral do projeto:

*   Diagramas de entidade, fluxos de navegação, descrições de modelos de domínio.
*   Especificações BDD (cenários comportamentais).
*   Scripts de API (Insomnia, Postman).

---

## 📁 tests/ – Testes automatizados, organizados por:

*   Testes de unidade do domínio.
*   Testes de integração com o banco de dados.
*   Testes end-to-end (e2e) baseados em cenários BDD.

---
## **Primeira Entrega**
Os seguintes artefatos serão disponibilizados:
✅ **Descrição do domínio** utilizando linguagem onipresente.

✅ **Mapa de histórias do usuário**.

✅ **Protótipos de baixa fidelidade**.

✅ **Modelagem do subdomínio** com Context Mapper (arquivo CML).

✅ **Cenários BDD** e sua automação.

✅ **Implementação inicial baseada na Arquitetura Limpa**.

**Documentação:** https://docs.google.com/document/d/1BW_QoJFYYwSnOwjPVm0AmHeAqZEcN3o3PmriGSFQBFk/edit?usp=sharing

---
## **Equipe**
👥 **Integrantes:**
- **Cláudio Alves**  
- **Eduardo Lins**  
- **Gabriel Lima**  
- **João Ferraz**  
- **Kaique Alves**  
- **Letícia Lopes**  
- **Maria Augusta Gois**  
- **Maria Júlia Pessoa**  
- **Victor Guerra**  
- **Vinícius Ventura**  

---
## **Licença**
Este projeto está sob a licença **Apache 2.0**. Consulte o arquivo **LICENSE** para mais detalhes.
