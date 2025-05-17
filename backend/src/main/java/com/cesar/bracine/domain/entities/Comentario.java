package com.cesar.bracine.domain.entities;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Comentario {
    private final UUID id;
    private final String texto;
    private final Usuario autor;
    private final Instant dataCriacao;
    private final Filme filme;
    private final Debate debate;

    public Comentario(UUID id, String texto, Usuario autor, Filme filme, Debate debate, Instant dataCriacao) {
        this.id = id != null ? id : UUID.randomUUID();
        this.texto = validarTexto(texto);
        this.autor = Objects.requireNonNull(autor);
        this.dataCriacao = dataCriacao != null ? dataCriacao : Instant.now();
        this.filme = filme;
        this.debate = debate;
    }

    protected Comentario(String texto, Usuario autor, Filme filme, Instant dataCriacao) {
        this(null, texto, autor, filme, null, dataCriacao);
    }

    public Comentario(String texto, Usuario autor, Filme filme, Debate debate) {
        this(UUID.randomUUID(), texto, autor, filme, debate, Instant.now());
    }
    
    private String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto do comentário não pode ser vazio");
        }
        return texto.trim();
    }

    public UUID getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public Usuario getAutor() {
        return autor;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public Filme getFilme() {
        return filme;
    }

    public Debate getDebate() {
        return debate;
    }
}
