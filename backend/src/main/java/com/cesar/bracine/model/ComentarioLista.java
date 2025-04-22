package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comentario_lista")
public class ComentarioLista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_id", nullable = false)
    private ListaFilmes lista;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
    
    @Column(nullable = false, length = 500)
    private String texto;
    
    private LocalDateTime dataHora = LocalDateTime.now();
    
    public ComentarioLista() {
    }
    
    public ComentarioLista(ListaFilmes lista, User usuario, String texto) {
        this.lista = lista;
        this.usuario = usuario;
        this.texto = texto;
    }
} 