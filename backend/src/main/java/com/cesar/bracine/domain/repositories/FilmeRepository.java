package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Filme;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmeRepository {

    // Create or Edit
    void salvar(Filme filme);

    // Read (buscar por ID)
    Optional<Filme> buscarPorId(UUID id);

    // ListAll
    List<Filme> listarTodos();

    // Delete
    void remover(UUID id);
}
