package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "notificacao")
public class Notificacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
    
    @Column(nullable = false)
    private String mensagem;
    
    private String tipo;
    
    @Column(nullable = false)
    private LocalDateTime dataHora = LocalDateTime.now();
    
    private boolean lida = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desafio_id")
    private Desafio desafio;
    
    public Notificacao() {
    }
    
    public Notificacao(User usuario, String mensagem, String tipo) {
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.tipo = tipo;
    }
    
    public Notificacao(User usuario, String mensagem, String tipo, Desafio desafio) {
        this.usuario = usuario;
        this.mensagem = mensagem;
        this.tipo = tipo;
        this.desafio = desafio;
    }
} 