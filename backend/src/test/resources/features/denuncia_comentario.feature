# language: pt
Funcionalidade: Denunciar comentários de avaliações
  Como um usuário do sistema
  Eu quero poder denunciar comentários inadequados
  Para que a plataforma possa manter um ambiente saudável e respeitoso

  Contexto:
    Dado que existe um usuário cadastrado com o username "usuario_comum"
    E que existe um usuário administrador com o username "admin"
    E que existe um filme "Bacurau" cadastrado no sistema
    E que existem avaliações com comentários para o filme
    E que o usuário está autenticado como "usuario_comum"

  Cenário: Denunciar um comentário inadequado
    Dado que o usuário visualiza um comentário inadequado
    Quando ele clicar no botão "Denunciar comentário"
    E selecionar o motivo "Conteúdo ofensivo"
    E preencher a descrição "Este comentário contém linguagem ofensiva e inapropriada"
    E enviar a denúncia
    Então o sistema deve registrar a denúncia com sucesso
    E exibir uma mensagem de confirmação

  Cenário: Denunciar comentário sem fornecer descrição
    Dado que o usuário visualiza um comentário inadequado
    Quando ele clicar no botão "Denunciar comentário"
    E selecionar o motivo "Spam"
    E não preencher a descrição
    E enviar a denúncia
    Então o sistema deve exibir uma mensagem solicitando uma descrição

  Cenário: Visualizar denúncias feitas pelo usuário
    Dado que o usuário realizou denúncias anteriormente
    Quando ele acessar a página "Minhas Denúncias"
    Então ele deve visualizar todas as denúncias que realizou
    E o status de cada denúncia

  Cenário: Administrador visualiza lista de denúncias
    Dado que o usuário está autenticado como "admin"
    Quando ele acessar a página de administração de denúncias
    Então ele deve visualizar todas as denúncias não revisadas
    E poder filtrar por tipo de motivo

  Cenário: Administrador revisa uma denúncia
    Dado que o usuário está autenticado como "admin"
    E que existem denúncias não revisadas no sistema
    Quando ele selecionar uma denúncia para revisar
    E marcar a denúncia como "procedente"
    E selecionar a ação "Remover comentário"
    E finalizar a revisão
    Então o sistema deve atualizar o status da denúncia como revisada
    E o comentário denunciado deve ser removido
    E o usuário que fez a denúncia deve ser notificado

  Cenário: Administrador rejeita uma denúncia
    Dado que o usuário está autenticado como "admin"
    E que existem denúncias não revisadas no sistema
    Quando ele selecionar uma denúncia para revisar
    E marcar a denúncia como "improcedente"
    E incluir justificativa "O comentário não viola nossas diretrizes"
    E finalizar a revisão
    Então o sistema deve atualizar o status da denúncia como revisada
    E o comentário denunciado deve permanecer visível
    E o usuário que fez a denúncia deve ser notificado

  Cenário: Estatísticas de denúncias para administradores
    Dado que o usuário está autenticado como "admin"
    Quando ele acessar o painel de estatísticas de denúncias
    Então ele deve visualizar gráficos com quantidade de denúncias por motivo
    E taxa de denúncias procedentes vs improcedentes
    E tempo médio de resposta às denúncias
