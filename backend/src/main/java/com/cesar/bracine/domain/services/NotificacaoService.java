package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.NotificacaoRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacaoService(NotificacaoRepository notificacaoRepository, UsuarioRepository usuarioRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Criar ou Editar uma notificação
    public Notificacao salvarNotificacao(Notificacao notificacao) {
        Usuario usuario = usuarioRepository.buscarPorId(notificacao.getDestinatario().getValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        notificacaoRepository.salvar(notificacao, usuario);
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
