Feature: Gestão de debates

  Scenario: Cadastro de novo debate
    Given que o usuário "João" deseja se cadastrar
    When ele fornece o nome "João", nome de usuário "joao123", email "joao@example.com", e senha "senha123"
    And um debate com titulo "Discussão sobre filmes" e criador "{id}"
    When o debate for salvo
    Then o debate deve estar disponível na listagem
