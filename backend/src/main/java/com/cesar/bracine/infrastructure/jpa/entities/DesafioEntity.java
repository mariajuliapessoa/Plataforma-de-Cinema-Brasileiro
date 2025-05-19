package com.cesar.bracine.infrastructure.jpa.entities;

import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "desafios")
public class DesafioEntity {

    @Id
    private UUID id;

    private String titulo;
    private String descricao;
    private int pontos;

    @ManyToOne
    private UsuarioEntity destinatario;

    private LocalDate dataCriacao;
    private LocalDate prazo;
    private boolean concluido;

}