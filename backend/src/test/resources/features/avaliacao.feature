Feature: Gestão de avaliações

  Scenario: Cadastro de nova avaliação
    Given que o usuário "João" deseja se cadastrar
    When ele fornece o nome "João", nome de usuário "joao123", email "joao@example.com", e senha "senha123"
    And uma avaliação com texto "Excelente!", autor "{id}", filme "11111111-1111-1111-1111-111111111111", nota 5
    When a avaliação for salva
    Then a avaliação deve estar disponível na listagem
