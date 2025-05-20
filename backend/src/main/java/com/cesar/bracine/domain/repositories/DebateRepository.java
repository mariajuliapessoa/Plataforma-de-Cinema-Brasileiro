package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DebateRepository {

    // Create or Edit
    void salvar(Debate debate, Usuario usuario);

    // Read (buscar por ID)
    Optional<Debate> buscarPorId(UUID id);

    // ListAll
    List<Debate> listarTodos();

    // Delete
    void remover(UUID id);
}
