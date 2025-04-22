package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "denuncia_comentario")
public class DenunciaComentario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comentario_id", nullable = false)
    private ComentarioLista comentario;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
    
    @Column(nullable = false, length = 500)
    private String motivo;
    
    @Column(nullable = false)
    private LocalDateTime dataDenuncia = LocalDateTime.now();
    
    private boolean revisada = false;
    
    private LocalDateTime dataRevisao;
    
    private boolean procedente = false;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "revisor_id")
    private User revisor;
    
    public DenunciaComentario() {
    }
    
    public DenunciaComentario(ComentarioLista comentario, User usuario, String motivo) {
        this.comentario = comentario;
        this.usuario = usuario;
        this.motivo = motivo;
    }
    
    public void revisar(User revisor, boolean procedente) {
        this.revisor = revisor;
        this.procedente = procedente;
        this.revisada = true;
        this.dataRevisao = LocalDateTime.now();
    }
} 
} 