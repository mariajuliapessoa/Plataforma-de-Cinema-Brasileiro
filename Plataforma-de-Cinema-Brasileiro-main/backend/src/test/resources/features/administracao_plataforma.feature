# language: pt
Funcionalidade: Gerenciar as funcionalidades da plataforma
  Como um administrador do sistema
  Eu quero poder gerenciar a plataforma
  Para garantir seu funcionamento adequado e a qualidade do conteúdo

  Contexto:
    Dado que existe um usuário administrador com o username "admin"
    E que o administrador está autenticado

  Cenário: Acessar o painel administrativo
    Dado que o administrador está logado
    Quando ele clicar em "Administração"
    Então deve ser redirecionado para o painel de gestão da plataforma

  Cenário: Aprovar comentários denunciados
    Dado que há comentários denunciados
    Quando o admin acessar a aba de denúncias
    Então ele poderá aprovar, ocultar ou apagar o comentário

  Cenário: Remover filmes impróprios
    Dado que um filme está marcado como impróprio
    Quando o admin clicar em "Remover"
    Então o filme será excluído da plataforma

  Cenário: Criar novo desafio temático
    Dado que o administrador quer engajar usuários
    Quando ele criar um novo desafio
    Então ele será publicado na seção de desafios

  Cenário: Promover usuário a especialista
    Dado que um usuário é reconhecido como crítico
    Quando o admin alterar seu status
    Então o usuário se tornará especialista e terá permissões especiais

  Cenário: Destacar filme em tendência
    Dado que o admin deseja promover diversidade
    Quando ele marcar um filme como destaque
    Então ele será exibido na aba de tendências