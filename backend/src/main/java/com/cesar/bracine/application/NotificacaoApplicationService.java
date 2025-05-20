package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.enums.TipoNotificacao;
import com.cesar.bracine.domain.repositories.NotificacaoRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificacaoApplicationService {

    private final NotificacaoRepository notificacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public NotificacaoApplicationService(NotificacaoRepository notificacaoRepository, UsuarioRepository usuarioRepository) {
        this.notificacaoRepository = notificacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Notificacao criarNotificacao(UUID destinatarioId, String mensagem, TipoNotificacao tipo) {
        Usuario destinatario = usuarioRepository.buscarPorId(destinatarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        UsuarioId usuarioIdValueObject = new UsuarioId(destinatario.getId().getValue());

        Notificacao notificacao = new Notificacao(usuarioIdValueObject, mensagem, tipo);
        notificacaoRepository.salvar(notificacao, destinatario);
        return notificacao;
    }

    public Optional<Notificacao> buscarPorId(UUID id) {
        return notificacaoRepository.buscarPorId(id);
    }

    public List<Notificacao> listarTodas() {
        return notificacaoRepository.listarTodos();
    }

    public List<Notificacao> listarPorDestinatario(UUID usuarioId) {
        return notificacaoRepository.buscarPorDestinatario(usuarioId);
    }

    public void marcarComoLida(UUID notificacaoId) {
        Notificacao notificacao = notificacaoRepository.buscarPorId(notificacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Notificação não encontrada"));

        UsuarioId usuarioId = notificacao.getDestinatario();

        Usuario usuario = usuarioRepository.buscarPorId(usuarioId.getValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        notificacao.marcarComoLida();
        notificacaoRepository.salvar(notificacao, usuario);
    }

    public void remover(UUID id) {
        notificacaoRepository.remover(id);
    }
}
