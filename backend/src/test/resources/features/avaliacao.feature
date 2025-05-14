Feature: Avaliação de filmes

  Scenario: Criar uma avaliação para um filme
    Given que o filme "Vingadores" está cadastrado
    And o usuário "João" está cadastrado
    When o usuário "João" avalia o filme "Vingadores" com nota 4
    Then a avaliação para o filme "Vingadores" deve ser criada com sucesso
