package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.enums.TipoNotificacao;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Notificacao {

    private final UUID id;
    private final Usuario destinatario;
    private final String mensagem;
    private final TipoNotificacao tipo;
    private final Instant dataCriacao;
    private boolean lida;

    public Notificacao(UUID id, Usuario destinatario, String mensagem, TipoNotificacao tipo, Instant dataCriacao, boolean lida) {
        this.id = id != null ? id : UUID.randomUUID();
        this.destinatario = Objects.requireNonNull(destinatario, "Destinatário não pode ser nulo");
        this.mensagem = validarMensagem(mensagem);
        this.tipo = Objects.requireNonNull(tipo, "Tipo de notificação é obrigatório");
        this.dataCriacao = dataCriacao != null ? dataCriacao : Instant.now();
        this.lida = lida;
    }

    public Notificacao(Usuario destinatario, String mensagem, TipoNotificacao tipo) {
        this(null, destinatario, mensagem, tipo, Instant.now(), false);
    }

    private String validarMensagem(String mensagem) {
        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new IllegalArgumentException("Mensagem da notificação é obrigatória");
        }
        return mensagem.trim();
    }

    // Regras de domínio
    public void marcarComoLida() {
        if (this.lida) {
            throw new IllegalStateException("Notificação já está marcada como lida");
        }
        this.lida = true;
    }

    public boolean estaLida() {
        return lida;
    }

    // Getters
    public UUID getId() {
        return id;
    }

    public Usuario getDestinatario() {
        return destinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public TipoNotificacao getTipo() {
        return tipo;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }
}
