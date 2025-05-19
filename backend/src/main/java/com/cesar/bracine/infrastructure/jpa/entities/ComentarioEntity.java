package com.cesar.bracine.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "comentarios")
public class ComentarioEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String texto;

    @ManyToOne(optional = false)
    private UsuarioEntity autor;

    @Column(nullable = false)
    private Instant dataCriacao;

    @ManyToOne
    private FilmeEntity filme;

    @ManyToOne
    private DebateEntity debate;


    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = Instant.now();
        }
    }

    public ComentarioEntity() {
        this.id = UUID.randomUUID();
        this.dataCriacao = Instant.now();
    }
}
