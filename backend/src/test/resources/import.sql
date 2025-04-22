INSERT INTO filme (titulo, descricao, genero, diretor, ano, nota) VALUES
('Bacurau', 'No sertão brasileiro', 'Documentário', 'Kleber Mendonça Filho', 2019, 4.6),
('O Som ao Redor', 'Filme sobre a vida urbana', 'Drama', 'Kleber Mendonça Filho', 2012, 4.4),
('Central do Brasil', 'Drama emocionante', 'Drama', 'Walter Salles', 1998, 4.8),
('Que horas ela volta?', 'Uma empregada doméstica deixa sua filha aos cuidados de familiares e vai trabalhar para uma família rica em São Paulo', 'Drama', 'Anna Muylaert', 2015, 4.2),
('Vidas Secas', 'Adaptação do livro de Graciliano Ramos sobre uma família de retirantes no sertão', 'Drama', 'Nelson Pereira dos Santos', 1963, 4.7),
('O Auto da Compadecida', 'Adaptação da obra de Ariano Suassuna', 'Comédia', 'Guel Arraes', 2000, 4.9);

-- Inserir usuários para testes de avaliações
INSERT INTO users (id, username, password, email, role, created_at) VALUES
('8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', 'usuario_teste', '$2a$10$8HyF.c3S6CwHIyn9aJxPu.AlK/XCwH0fSRj9XQzrO5StM1PExGE8i', 'usuario_teste@email.com', 'USER', '2023-01-01'),
('7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', 'outro_usuario', '$2a$10$8HyF.c3S6CwHIyn9aJxPu.AlK/XCwH0fSRj9XQzrO5StM1PExGE8i', 'outro_usuario@email.com', 'USER', '2023-01-02');

-- Inserir algumas avaliações para testes
INSERT INTO avaliacao (filme_id, user_id, estrelas, comentario, curtidas, data_hora) VALUES
(4, '7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', 5, 'Este filme é realmente incrível! A atuação de Regina Casé é brilhante. Recomendo a todos!', 3, '2023-06-10 14:30:00'),
(1, '7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', 4, 'Excelente filme! Uma crítica social muito bem elaborada e com cenas impactantes.', 2, '2023-05-20 10:15:00');

-- Inserir listas de filmes para testes
INSERT INTO lista_filmes (nome, descricao, criador_id, publica, colaborativa, permite_votacao, data_criacao) VALUES
('Clássicos do Sertão', 'Uma seleção de filmes que retratam a vida no sertão brasileiro', '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', true, false, true, '2023-05-15 09:30:00'),
('Diretores Brasileiros', 'Lista com filmes de grandes diretores do cinema nacional', '7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', true, true, false, '2023-05-18 14:45:00'),
('Minha Lista Privada', 'Lista pessoal de filmes favoritos', '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', false, false, false, '2023-06-01 17:20:00');

-- Adicionar filmes às listas
INSERT INTO item_lista (lista_id, filme_id, adicionado_por_id, data_adicionado, votos) VALUES
(1, 1, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', '2023-05-15 10:00:00', 5),
(1, 5, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', '2023-05-15 10:05:00', 3),
(1, 6, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', '2023-05-15 10:10:00', 7),
(2, 1, '7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', '2023-05-18 15:00:00', 2),
(2, 2, '7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', '2023-05-18 15:10:00', 1),
(3, 3, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', '2023-06-01 17:30:00', 0),
(3, 4, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', '2023-06-01 17:35:00', 0);

-- Adicionar colaboradores às listas
INSERT INTO lista_filmes_colaboradores (lista_id, user_id) VALUES
(2, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f');

-- Adicionar comentários nas listas
INSERT INTO comentario_lista (lista_id, usuario_id, texto, data_hora) VALUES
(1, '7e8d4c2b-1a5f-4e9d-8c7b-6f5d4e3c2b1a', 'Excelente seleção de filmes sobre o sertão!', '2023-05-20 11:30:00'),
(2, '8f9d5e6a-7c3b-4162-9d9a-8f2b1c5d3e4f', 'Kleber Mendonça Filho é um dos meus diretores favoritos.', '2023-05-25 16:15:00');