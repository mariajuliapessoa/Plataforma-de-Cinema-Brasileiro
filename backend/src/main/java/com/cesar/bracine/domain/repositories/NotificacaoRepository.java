package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Notificacao;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificacaoRepository {

    // Create or Edit
    void salvar(Notificacao notificacao);

    // Read (buscar por ID)
    Optional<Notificacao> buscarPorId(UUID id);

    // ListAll
    List<Notificacao> listarTodos();

    // Delete
    void remover(UUID id);
}
