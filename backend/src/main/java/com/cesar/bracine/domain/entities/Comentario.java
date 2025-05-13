package com.cesar.bracine.domain.entities;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Comentario {
    private final String id;
    private final String texto;
    private final Usuario autor;
    private final Instant dataCriacao;
    private final Filme filme;
    private final Debate debate;

    public Comentario(String texto, Usuario autor, Filme filme, Debate debate) {
        this.id = UUID.randomUUID().toString();
        this.texto = validarTexto(texto);
        this.autor = Objects.requireNonNull(autor);
        this.dataCriacao = Instant.now();
        this.filme = filme;
        this.debate = debate;
    }

    protected Comentario(String texto, Usuario autor, Filme filme, Instant dataCriacao) {
        this.id = UUID.randomUUID().toString();
        this.texto = validarTexto(texto);
        this.autor = Objects.requireNonNull(autor);
        this.dataCriacao = Objects.requireNonNull(dataCriacao);
        this.filme = filme;
        this.debate = null;
    }

    private String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto do comentário não pode ser vazio");
        }
        return texto.trim();
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
