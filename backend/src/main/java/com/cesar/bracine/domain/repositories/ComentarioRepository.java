package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Comentario;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ComentarioRepository {

    // Create or Edit
    void salvar(Comentario comentario);

    // Read (buscar por ID)
    Optional<Comentario> buscarPorId(UUID id);

    // ListAll
    List<Comentario> listarTodos();

    // Delete
    void remover(UUID id);

    List<Comentario> buscarPorIdUsuario(UUID id);
}
