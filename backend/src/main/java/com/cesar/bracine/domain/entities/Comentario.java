package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.valueobjects.ComentarioId;
import com.cesar.bracine.domain.valueobjects.DebateId;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Comentario {
    private final ComentarioId id;
    private final String texto;
    private final UsuarioId autor;
    private final Instant dataCriacao;
    private final FilmeId filme;
    private final DebateId debateId;

    public Comentario(ComentarioId id, String texto, UsuarioId autor, FilmeId filme, DebateId debateId, Instant dataCriacao) {
        this.id = id != null ? id : new ComentarioId(UUID.randomUUID());
        this.texto = validarTexto(texto);
        this.autor = Objects.requireNonNull(autor);
        this.dataCriacao = dataCriacao != null ? dataCriacao : Instant.now();
        this.filme = filme;
        this.debateId = debateId;
    }

    protected Comentario(String texto, UsuarioId autor, FilmeId filme, Instant dataCriacao) {
        this(null, texto, autor, filme, null, dataCriacao);
    }

    public Comentario(String texto, UsuarioId autor, FilmeId filme, DebateId debateId) {
        this(new ComentarioId(UUID.randomUUID()), texto, autor, filme, debateId, Instant.now());
    }
    
    private String validarTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("Texto do comentário não pode ser vazio");
        }
        return texto.trim();
    }

    public ComentarioId getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public UsuarioId getAutor() {
        return autor;
    }

    public Instant getDataCriacao() {
        return dataCriacao;
    }

    public FilmeId getFilme() {
        return filme;
    }

    public DebateId getDebate() {
        return debateId;
    }
}
