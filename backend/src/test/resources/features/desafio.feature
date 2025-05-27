Feature: Gestão de desafios

  Scenario: Cadastro de novo desafio
    Given que o usuário "João" deseja se cadastrar
    When ele fornece o nome "João", nome de usuário "joao123", email "joao@example.com", e senha "senha123"
    And um desafio com titulo "Maratona", descricao "Assistir 5 filmes", pontos 50 para o usuario "{id}"
    When o desafio for salvo
    Then o desafio deve estar disponível na listagem
