Feature: Gestão de comentários

  Scenario: Cadastro de novo comentário
    Given que o usuário "João" deseja se cadastrar
    When ele fornece o nome "João", nome de usuário "joao123", email "joao@example.com", e senha "senha123"
    And um comentário com texto "Ótimo filme!", autor "{id}", filme "11111111-1111-1111-1111-111111111111", debate "22222222-2222-2222-2222-222222222222"
    When o comentário for salvo
    Then o comentário deve estar disponível na listagem
