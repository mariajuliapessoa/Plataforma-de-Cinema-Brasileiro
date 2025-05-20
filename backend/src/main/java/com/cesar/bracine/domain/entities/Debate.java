package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.valueobjects.ComentarioId;
import com.cesar.bracine.domain.valueobjects.DebateId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Debate {
    private final DebateId id;
    private final String titulo;
    private final UsuarioId criador;
    private final List<ComentarioId> comentarios;

    public Debate(DebateId id, String titulo, UsuarioId criador, List<ComentarioId> comentarios) {
        this.id = id != null ? id : new DebateId(UUID.randomUUID());
        this.titulo = validarTitulo(titulo);
        this.criador = Objects.requireNonNull(criador);
        this.comentarios = comentarios != null ? new ArrayList<>(comentarios) : new ArrayList<>();
    }

    public Debate(DebateId id, String titulo, UsuarioId criador) {
        this.id = id != null ? id : new DebateId(UUID.randomUUID());
        this.titulo = validarTitulo(titulo);
        this.criador = Objects.requireNonNull(criador);
        this.comentarios = new ArrayList<>();
    }

    public Debate(String titulo, UsuarioId criador) {
        this(null, titulo, criador);
    }

    private String validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do debate não pode ser vazio");
        }
        return titulo.trim();
    }

    public DebateId getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public UsuarioId getCriador() {
        return criador;
    }

    public List<ComentarioId> getComentarios() {
        return Collections.unmodifiableList(comentarios);
    }
}