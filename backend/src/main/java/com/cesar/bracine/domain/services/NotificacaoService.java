package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.repositories.NotificacaoRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository) {
        this.notificacaoRepository = notificacaoRepository;
    }

    // Criar ou Editar uma notificação
    public Notificacao salvarNotificacao(Notificacao notificacao) {
        notificacaoRepository.salvar(notificacao);
        return notificacao;
    }

    // Buscar notificação por ID
    public Optional<Notificacao> buscarNotificacaoPorId(UUID id) {
        return notificacaoRepository.buscarPorId(id);
    }

    // Listar todas as notificações
    public List<Notificacao> listarTodasNotificacoes() {
        return notificacaoRepository.listarTodos();
    }

    // Remover uma notificação
    public void removerNotificacao(UUID id) {
        notificacaoRepository.remover(id);
    }
}
