# language: pt

Funcionalidade: Buscar e navegar por filmes na plataforma

  Cenário: Buscar filme pelo título exato
    Dado que o usuário está na tela inicial
    Quando ele digitar "Bacurau" na barra de busca
    Então o sistema deve exibir o filme "Bacurau"

  Cenário: Buscar por palavra-chave
    Dado que o usuário acessa a busca avançada
    Quando ele digitar a palavra "sertão"
    Então o sistema deve exibir todos os filmes que contenham essa palavra em seu título ou descrição

  Cenário: Buscar por gênero
    Dado que o usuário está na aba de filtros
    Quando ele selecionar o gênero "Documentário"
    Então o sistema deve exibir todos os filmes classificados como documentário

  Cenário: Buscar por diretor
    Dado que o usuário deseja encontrar filmes de um diretor específico
    Quando ele digitar "Kleber Mendonça Filho" na busca
    Então o sistema deve retornar os filmes dirigidos por ele

  Cenário: Busca sem resultados
    Dado que o usuário busca por um termo inexistente
    Quando ele digita "XYZ123" na barra de busca
    Então o sistema deve exibir a mensagem "Nenhum resultado encontrado"

  Cenário: Visualização de detalhes do filme
    Dado que o usuário visualiza os resultados da busca
    Quando ele clicar em um filme
    Então o sistema deve exibir a página de detalhes desse filme

  Cenário: Aplicar filtros (ano, nota, etc)
    Dado que o usuário deseja refinar a busca
    Quando ele selecionar filtros como "Ano de lançamento" e "Nota mínima 4"
    Então o sistema deve mostrar apenas os filmes que atendem a esses critérios

  Cenário: Ver filmes populares em destaque
    Dado que o usuário acessa a home da plataforma
    Quando ele rolar até a seção "Populares"
    Então o sistema deve exibir os filmes mais assistidos e bem avaliados recentemente