Feature: Cadastro e gerenciamento de usuário

  Scenario: Criar um novo usuário com sucesso
    Given que o usuário "João" deseja se cadastrar
    When ele fornece o nome "João", nome de usuário "joao123", email "joao@example.com", e senha "senha123"
    Then o sistema deve criar o usuário com sucesso

  Scenario: Alterar a senha de um usuário
    Given que o usuário com email "joao@example.com" já está cadastrado
    When ele altera sua senha para "novaSenha123"
    Then o sistema deve atualizar a senha do usuário
