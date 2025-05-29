package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Avaliacao;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AvaliacaoRepository {

    // Create or Edit
    void salvar(Avaliacao avaliacao);

    // Read (buscar por ID)
    Optional<Avaliacao> buscarPorId(UUID id);

    List<Avaliacao> buscarPorIdFilme(UUID id);

    List<Avaliacao> buscarPorIdUsuario(UUID id);

    // ListAll
    List<Avaliacao> listarTodos();

    // Delete
    void remover(UUID id);

}