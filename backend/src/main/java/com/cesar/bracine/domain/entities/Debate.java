package com.cesar.bracine.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Debate {
    private final UUID id;
    private final String titulo;
    private final Usuario criador;
    private final List<Comentario> comentarios;

    public Debate(UUID id, String titulo, Usuario criador, List<Comentario> comentarios) {
        this.id = id != null ? id : UUID.randomUUID();
        this.titulo = validarTitulo(titulo);
        this.criador = Objects.requireNonNull(criador);
        this.comentarios = comentarios != null ? new ArrayList<>(comentarios) : new ArrayList<>();
    }

    public Debate(UUID id, String titulo, Usuario criador) {
        this.id = id != null ? id : UUID.randomUUID();
        this.titulo = validarTitulo(titulo);
        this.criador = Objects.requireNonNull(criador);
        this.comentarios = new ArrayList<>();
    }

    private String validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do debate não pode ser vazio");
        }
        return titulo.trim();
    }

    public void adicionarComentario(Comentario comentario) {
        if (comentario == null) {
            throw new IllegalArgumentException("Comentário não pode ser nulo");
        }
        this.comentarios.add(comentario);
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getCriador() {
        return criador;
    }

    public List<Comentario> getComentarios() {
        return Collections.unmodifiableList(comentarios);
    }
}