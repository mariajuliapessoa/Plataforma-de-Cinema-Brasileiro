package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DesafioRepository {

    // Create or Edit
    void salvar(Desafio desafio, Usuario usuario);

    // Read (buscar por ID)
    Optional<Desafio> buscarPorId(UUID id);

    // ListAll
    List<Desafio> listarTodos();

    List<Desafio> buscarPorUsuario(UUID id);

    // Delete
    void remover(UUID id);
}
