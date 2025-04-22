# language: pt
Funcionalidade: Participar de desafios
  Como um usuário do sistema
  Eu quero poder participar de desafios relacionados a filmes
  Para descobrir novos filmes e ganhar conquistas

  Contexto:
    Dado que existe um usuário cadastrado com o username "usuario_teste"
    E que existe um filme "Bacurau" cadastrado no sistema
    E que o usuário está autenticado

  Cenário: Entrar em um desafio ativo
    Dado que existe um desafio ativo chamado "Assista 5 filmes do Cinema Novo"
    Quando o usuário acessar o desafio "Assista 5 filmes do Cinema Novo"
    Então deve ser adicionado ao desafio

  Cenário: Ver progresso em desafio
    Dado que o usuário participa de um desafio
    Quando ele acessar a aba "Meus desafios"
    Então o sistema deve exibir seu progresso

  Cenário: Assistir filme e marcar como visto
    Dado que o filme faz parte de um desafio
    Quando o usuário clicar em "Marcar como assistido"
    Então o sistema atualiza seu progresso no desafio

  Cenário: Receber conquista ao concluir desafio
    Dado que o usuário completou todos os filmes do desafio
    Quando ele acessar o desafio novamente
    Então o sistema deve exibir uma conquista desbloqueada

  Cenário: Ver ranking dos participantes
    Dado que o usuário participa de um desafio
    Quando ele acessar a aba "Ranking"
    Então ele deve ver a posição dele e de outros usuários

  Cenário: Criar desafio personalizado (admin)
    Dado que o administrador está logado
    Quando ele clicar em "Criar novo desafio"
    Então o desafio será salvo e publicado

  Cenário: Sair de um desafio
    Dado que o usuário não deseja mais participar
    Quando ele clicar em "Sair do desafio"
    Então o sistema deve removê-lo do desafio

  Cenário: Notificação de novo desafio disponível
    Dado que um novo desafio foi publicado
    Quando o usuário abrir a plataforma
    Então ele deve receber uma notificação sobre o desafio 