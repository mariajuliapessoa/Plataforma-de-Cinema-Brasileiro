# language: pt
Funcionalidade: Explorar listas de filmes personalizadas
  Como um usuário do sistema
  Eu quero poder criar e gerenciar listas de filmes personalizadas
  Para compartilhar e organizar minha seleção de filmes

  Contexto:
    Dado que existe um usuário cadastrado com o username "usuario_teste"
    E que existe um filme "Bacurau" cadastrado no sistema
    E que o usuário está autenticado

  Cenário: Acessar lista pública
    Dado que existe uma lista pública chamada "Clássicos do Sertão"
    Quando o usuário acessa a aba "Listas"
    E ele clicar em uma lista pública chamada "Clássicos do Sertão"
    Então deve visualizar todos os filmes dessa lista

  Cenário: Criar nova lista colaborativa
    Dado que o usuário está logado
    Quando ele clicar em "Criar nova lista" e definir como colaborativa
    Então o sistema deve criar a lista com permissões de edição para convidados

  Cenário: Adicionar filme à lista
    Dado que o usuário criou uma lista
    Quando ele acessar um filme e clicar em "Adicionar à lista"
    Então o filme deve ser incluído na lista selecionada

  Cenário: Votar em filmes de uma lista
    Dado que existe uma lista configurada para votação
    Quando o usuário votar em um filme da lista
    Então o sistema deve registrar o voto

  Cenário: Comentar na lista
    Dado que o usuário visualiza uma lista
    Quando ele escrever um comentário
    Então o comentário deve aparecer para todos que acessarem a lista

  Cenário: Excluir lista criada
    Dado que o usuário é o criador da lista
    Quando ele clicar em "Excluir lista"
    Então o sistema deve apagar permanentemente a lista

  Cenário: Definir lista como privada
    Dado que o usuário está editando uma lista
    Quando ele marcar a opção "Privada"
    Então apenas convidados poderão visualizá-la

  Cenário: Convidar amigo para editar lista
    Dado que o usuário criou uma lista colaborativa
    E que existe outro usuário cadastrado
    Quando ele adicionar um amigo como colaborador
    Então o sistema deve permitir que o amigo também edite a lista 