# language: pt
Funcionalidade: Iniciar e participar de debates sobre filmes
  Como um usuário do sistema
  Eu quero poder participar de debates sobre filmes
  Para compartilhar opiniões e discutir sobre cinema brasileiro

  Contexto:
    Dado que existe um usuário cadastrado com o username "usuario_teste"
    E que existe um filme "Bacurau" cadastrado no sistema
    E que o usuário está autenticado

  Cenário: Entrar em sala de discussão de um filme
    Dado que o usuário está na página do filme
    Quando ele clicar em "Ver discussão"
    Então será direcionado à sala de debate daquele filme

  Cenário: Postar comentário
    Dado que o usuário está em uma sala de discussão
    Quando ele escrever e enviar um comentário
    Então o comentário deve aparecer na discussão

  Cenário: Responder comentário de outro usuário
    Dado que há um comentário existente
    Quando o usuário clicar em "Responder"
    Então ele poderá adicionar uma resposta diretamente abaixo

  Cenário: Curtir comentário
    Dado que o usuário visualiza comentários
    Quando ele clicar em "Curtir"
    Então o número de curtidas aumenta em 1

  Cenário: Denunciar comentário ofensivo
    Dado que um comentário viola as regras
    Quando o usuário clicar em "Denunciar"
    Então o sistema deve notificar os moderadores

  Cenário: Ver debates em destaque
    Dado que há discussões com muita atividade
    Quando o usuário acessar a aba "Debates em alta"
    Então ele verá os mais comentados recentemente

  Cenário: Participar de evento com especialista
    Dado que um evento de debate está agendado
    Quando o horário chegar
    Então o usuário poderá entrar na sala ao vivo

  Cenário: Receber notificação de novo comentário
    Dado que o usuário participa de uma discussão
    Quando houver um novo comentário
    Então o sistema deve enviar uma notificação 