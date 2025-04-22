package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "comentario_debate")
public class ComentarioDebate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debate_id", nullable = false)
    private DebateFilme debate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private User autor;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String texto;
    
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comentario_pai_id")
    private ComentarioDebate comentarioPai;
    
    @OneToMany(mappedBy = "comentarioPai", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioDebate> respostas = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "comentario_curtidas",
        joinColumns = @JoinColumn(name = "comentario_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<User> curtidas = new HashSet<>();
    
    private int totalCurtidas = 0;
    
    private boolean denunciado = false;
    
    private int totalDenuncias = 0;
    
    private boolean revisado = false;
    
    private boolean oculto = false;
    
    private boolean destaque = false;
    
    private boolean especialista = false;
    
    public ComentarioDebate() {
    }
    
    public ComentarioDebate(DebateFilme debate, User autor, String texto) {
        this.debate = debate;
        this.autor = autor;
        this.texto = texto;
    }
    
    public ComentarioDebate(DebateFilme debate, User autor, String texto, ComentarioDebate comentarioPai) {
        this.debate = debate;
        this.autor = autor;
        this.texto = texto;
        this.comentarioPai = comentarioPai;
    }
    
    public void adicionarCurtida(User usuario) {
        if (!curtidas.contains(usuario)) {
            curtidas.add(usuario);
            totalCurtidas = curtidas.size();
        }
    }
    
    public void removerCurtida(User usuario) {
        if (curtidas.contains(usuario)) {
            curtidas.remove(usuario);
            totalCurtidas = curtidas.size();
        }
    }
    
    public void denunciar() {
        denunciado = true;
        totalDenuncias++;
    }
    
    public void adicionarResposta(ComentarioDebate resposta) {
        respostas.add(resposta);
    }
    
    public boolean isAutorEspecialista() {
        return especialista;
    }
} 