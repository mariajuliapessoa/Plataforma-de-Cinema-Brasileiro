package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    // Create or Edit (salvar ou atualizar)
    void salvar(Usuario usuario);

    // Read (buscar por ID)
    Optional<Usuario> buscarPorId(UUID id);

    // Read (buscar por email)
    Optional<Usuario> buscarPorEmail(String email);

    // ListAll (listar todos os usuários)
    List<Usuario> listarTodos();

    // Delete (remover por ID)
    void remover(UUID id);
}
