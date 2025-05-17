Feature: Cadastro e gerenciamento de usuário

  Scenario: Criar um novo usuário com sucesso
    Given que o usuário "João" deseja se cadastrar
    When ele fornece o nome "João", nome de usuário "joao123", email "joao@example.com", e senha "senha123"
    Then o sistema deve criar o usuário com sucesso

  Scenario: Alterar a senha de um usuário
    Given que o usuário com email "joao@example.com" já está cadastrado
    When ele altera sua senha para "novaSenha123"
    Then o sistema deve atualizar a senha do usuário

  Scenario: Não permitir criação de usuário com email já cadastrado
    Given que o usuário "João" já está cadastrado com email "joao@example.com"
    When ele tenta se cadastrar novamente com o mesmo email
    Then o sistema deve impedir o cadastro com uma mensagem de erro

  Scenario: Editar dados da conta
    Given que o usuário "Carlos" está cadastrado com email "carlos@example.com"
    When ele atualiza seu nome para "Carlos Silva", nome de usuário para "carloss", e email para "c.silva@example.com"
    Then os dados do usuário devem ser atualizados com sucesso