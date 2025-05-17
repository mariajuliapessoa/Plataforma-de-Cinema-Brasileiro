Feature: Sistema de Pontuação do Usuário

  Scenario: Adicionar pontos a um usuário
    Given que o sistema possui um usuário "Carlos" com email "carlos_pontos@example.com"
    When ele recebe 50 pontos
    Then o sistema deve registrar os pontos corretamente com total de 50

  Scenario: Tentar adicionar pontos a um usuário inexistente
    When eu tento adicionar 10 pontos ao usuário com ID inexistente
    Then o sistema deve lançar um erro de "Usuário não encontrado"
