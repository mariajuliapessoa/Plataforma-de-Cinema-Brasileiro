package com.cesar.bracine.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "avaliacoes")
public class AvaliacaoEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String texto;

    @ManyToOne(optional = false)
    private UsuarioEntity autor;

    @Column(nullable = false)
    private Instant dataCriacao;

    @ManyToOne(optional = false)
    private FilmeEntity filme;

    @Column(nullable = false)
    private int nota;

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID();
        }
        if (dataCriacao == null) {
            dataCriacao = Instant.now();
        }
    }
}
