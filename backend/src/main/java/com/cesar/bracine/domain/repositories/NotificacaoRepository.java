package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacaoRepository {

    // Create or Edit
    void salvar(Notificacao notificacao, Usuario usuario);

    // Read (buscar por ID)
    Optional<Notificacao> buscarPorId(UUID id);

    List<Notificacao> buscarPorDestinatario(UUID usuarioId);

    // ListAll
    List<Notificacao> listarTodos();

    // Delete
    void remover(UUID id);
}
