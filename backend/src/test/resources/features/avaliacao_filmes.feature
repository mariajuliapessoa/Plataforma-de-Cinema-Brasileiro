# language: pt
Funcionalidade: Avaliar filmes com notas e comentários
  Como um usuário do sistema
  Eu quero poder avaliar filmes com notas e comentários
  Para que eu possa compartilhar minha opinião sobre os filmes

  Contexto:
    Dado que existe um usuário cadastrado com o username "usuario_teste"
    E que existe um filme "Que horas ela volta?" cadastrado no sistema
    E que o usuário está autenticado

  Cenário: Avaliação válida
    Dado que o usuário está na página do filme "Que horas ela volta?"
    Quando ele preencher 4 estrelas e escrever um comentário de 30 caracteres
    Então a avaliação deve ser salva com sucesso

  Cenário: Avaliar sem comentário
    Dado que o usuário tenta avaliar um filme
    Quando ele não escreve nenhum comentário
    Então o sistema deve exibir uma mensagem de erro solicitando pelo menos 20 caracteres

  Cenário: Avaliar com comentário curto demais
    Dado que o usuário insere um comentário com menos de 20 caracteres
    Quando ele tenta enviar a avaliação
    Então o sistema deve impedir o envio e exibir uma mensagem de erro

  Cenário: Atualizar avaliação existente
    Dado que o usuário já avaliou um filme
    Quando ele alterar a nota ou o comentário
    Então o sistema deve substituir a avaliação anterior pela nova

  Cenário: Remover avaliação
    Dado que o usuário deseja apagar sua avaliação
    Quando ele clicar em "Remover avaliação"
    Então o sistema deve excluir a avaliação permanentemente

  Cenário: Ver avaliações de outros usuários
    Dado que existem avaliações de outros usuários para o filme
    Quando o usuário acessa a página de um filme
    Então ele deve visualizar as avaliações públicas dos outros usuários

  Cenário: Curtir comentário de outro usuário
    Dado que o usuário leu um comentário interessante
    Quando ele clicar no botão "Curtir"
    Então o número de curtidas daquele comentário deve aumentar

  Cenário: Ver média de avaliações
    Dado que existem avaliações para o filme
    Quando a página do filme for carregada
    Então o sistema deve exibir a média das notas recebidas 