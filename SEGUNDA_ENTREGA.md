# Segunda Entrega - Plataforma de Cinema Brasileiro

## Histórias de Usuário - Gerenciamento e Exploração de Filmes

### Listagem de Filmes
> Como usuário,
> Quero visualizar a lista de filmes disponíveis na plataforma,
> Para que eu possa ter uma visão geral do catálogo e escolher um filme para assistir ou obter mais detalhes.

### Busca de Filmes
> Como usuário,
> Quero poder buscar filmes utilizando filtros (como gênero, ano, etc.) ou palavras-chave relevantes,
> Para que eu possa localizar rapidamente o conteúdo desejado.

### Navegação por Páginas
> Como usuário,
> Quero poder navegar pelas páginas da lista de filmes,
> Para que eu possa explorar todo o catálogo de forma estruturada, especialmente ao lidar com uma grande quantidade de filmes.

### Visualização de um Filme Específico
> Como usuário,
> Quero visualizar os detalhes completos de um filme, incluindo informações como a sinopse, elenco, diretor, ano de lançamento e avaliações de outros usuários,
> Para que eu tenha um panorama completo sobre a obra antes de decidir assisti-la.

## Histórias de Usuário - Avaliações de Filmes

### Listagem de Avaliações de um Filme Específico
> Como usuário,
> Quero ver as avaliações feitas por outras pessoas sobre um filme específico,
> Para que eu possa entender a recepção do filme pela comunidade antes de assisti-lo ou após assisti-lo.

### Criação de Avaliação
> Como usuário,
> Quero poder criar minha própria avaliação após assistir a um filme,
> Para que eu possa compartilhar minha opinião (nota e comentário) com outros usuários da plataforma.

### Listagem de "Minhas Avaliações"
> Como usuário,
> Quero acessar uma lista de todas as avaliações que criei,
> Para que eu possa acompanhar minhas opiniões e revisitar os filmes que avaliei ao longo do tempo.

## Histórias de Usuário - Debates sobre Filmes

### Listagem de Debates
> Como usuário,
> Quero visualizar os debates disponíveis sobre filmes,
> Para que eu possa encontrar tópicos de discussão que me interessam e participar das conversas.

### Criação de Debates
> Como usuário,
> Quero poder criar um novo debate sobre um filme,
> Para que eu possa iniciar uma nova thread de discussão para que outros usuários possam participar.

### Criação de Comentários no Debate
> Como usuário,
> Quero poder criar comentários em um debate existente,
> Para que eu possa expressar minhas opiniões e contribuir para a conversa.

### Responder Comentários em Debates
> Como usuário,
> Quero poder responder a comentários específicos dentro de um debate,
> Para que eu possa interagir diretamente com outros participantes e aprofundar as discussões.

## Histórias de Usuário - Desafios

### Visualização de Desafios
> Como usuário,
> Quero ver uma lista de desafios disponíveis na plataforma,
> Para que eu possa conhecer as atividades propostas e decidir se quero participar.

### Página de Desafio
> Como usuário,
> Quero visualizar a página de um desafio específico ao me interessar por um,
> Para que eu tenha detalhes completos sobre o desafio e possa tomar a decisão de participar.

## Padrões de Projeto Implementados

### 1. Proxy (Caching Proxy)
- **Arquivo:** `CachingTMDBClientProxy.java`  
- **Local:** `src/main/java/com/cesar/bracine/infrastructure/tmdb/CachingTMDBClientProxy.java`  

### 2. Template Method
- **Arquivo:** `RepositoryAbstratoImpl.java`  
- **Local:** `src/main/java/com/cesar/bracine/infrastructure/jpa/repository/template/RepositoryAbstratoImpl.java`

## Instruções para Execução Local

#### Pré-requisitos:
- Java 21
- Node.js 22.14 (ou versão compatível)
- Git instalado

#### Passos:

1. **Clonar o repositório:**
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd Plataforma-de-Cinema-Brasileiro
   ```

2. **Backend (Spring Boot):**
   ```bash
   cd backend
   ```
   - Criar arquivo `.env` na raiz do backend com:
```
DATABASE_HOST=ep-purple-thunder-a5hhbpng-pooler.us-east-2.aws.neon.tech
DATABASE_NAME=bracinebd
DATABASE_PASSWORD=npg_8Tia7EWNCQDx
DATABASE_PORT=5432
DATABASE_USER=bracinebd_owner
JWT_SECRET_KEY=OEFoIVMqUDlqeEx6S1hxOUQ2S216UXEzVGY2dkJxWnNVNE5kUjBhV3pYa0xtT2VIblZ5R3RCcFRjUXNSd1V6WHk
TMDB_API_KEY=4517133a042a415c2bffe672b68610e1
```
   - Executar:
     ```bash
     ./mvnw spring-boot:run  # Linux/macOS
     ./mvnw.cmd spring-boot:run  # Windows
     ```

3. **Frontend (Next.js):**
   ```bash
   cd ../frontend
   ```
   - Criar arquivo `.env` na raiz do frontend com:
     ```
     NEXT_PUBLIC_API_BASE_URL="http://localhost:8080"
     ```
   - Instalar dependências e executar:
     ```bash
     npm install
     npm run dev
     ```

4. **Acesso:**
   - Backend: http://localhost:8080
   - Frontend: http://localhost:3000
