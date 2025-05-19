package com.cesar.bracine.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "debates")
public class DebateEntity {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @ManyToOne(optional = false)
    private UsuarioEntity criador;

    @Column(nullable = false)
    private Instant dataCriacao;

    @OneToMany(mappedBy = "debate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioEntity> comentarios = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (dataCriacao == null) {
            dataCriacao = Instant.now();
        }
    }

    public DebateEntity() {
        this.id = UUID.randomUUID();
    }
}
