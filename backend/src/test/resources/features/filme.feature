Feature: Gestão de filmes

  Scenario: Cadastro de novo filme
    Given um filme com titulo "Inception", diretor "Christopher Nolan", ano 2010, generos "Ação,Ficção", país "EUA", banner "http://imagem.com/banner.jpg"
    When o filme for salvo
    Then o filme deve estar disponível na listagem
