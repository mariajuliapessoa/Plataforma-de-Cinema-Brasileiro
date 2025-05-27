Feature: Gestão de notificações

  Scenario: Cadastro de nova notificação
    Given que o usuário "João" já está cadastrado com email "joao@example.com"
    And uma notificação do tipo "DESAFIO" para o usuario "{id}" com a mensagem "Bem-vindo ao sistema"
    When a notificação for salva
    Then a notificação deve estar disponível na listagem
