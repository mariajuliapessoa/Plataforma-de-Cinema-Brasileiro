package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.domain.repositories.ComentarioRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ComentarioService {

    private final ComentarioRepository comentarioRepository;

    public ComentarioService(ComentarioRepository comentarioRepository) {
        this.comentarioRepository = comentarioRepository;
    }

    // Criar ou Editar um comentário
    public Comentario salvarComentario(Comentario comentario) {
        comentarioRepository.salvar(comentario);
        return comentario;
    }

    // Buscar comentário por ID
    public Optional<Comentario> buscarComentarioPorId(UUID id) {
        return comentarioRepository.buscarPorId(id);
    }

    // Listar todos os comentários
    public List<Comentario> listarTodosComentarios() {
        return comentarioRepository.listarTodos();
    }

    // Remover um comentário
    public void removerComentario(UUID id) {
        comentarioRepository.remover(id);
    }

    public List<Comentario> buscarComentarioPorIdUsuario(UUID id) {
        return comentarioRepository.buscarPorIdUsuario(id);
    }
}
