package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "debate_filme")
public class DebateFilme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;
    
    @Column(nullable = false)
    private String titulo;
    
    private String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id", nullable = false)
    private User criador;
    
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    private LocalDateTime dataEventoAoVivo;
    
    private boolean ativo = true;
    
    private boolean emDestaque = false;
    
    private int totalComentarios = 0;
    
    private int totalParticipantes = 0;
    
    @OneToMany(mappedBy = "debate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioDebate> comentarios = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "debate_participantes",
        joinColumns = @JoinColumn(name = "debate_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<User> participantes = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "debate_especialistas",
        joinColumns = @JoinColumn(name = "debate_id"),
        inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<User> especialistas = new ArrayList<>();
    
    public DebateFilme() {
    }
    
    public DebateFilme(Filme filme, String titulo, String descricao, User criador) {
        this.filme = filme;
        this.titulo = titulo;
        this.descricao = descricao;
        this.criador = criador;
    }
    
    public void adicionarParticipante(User usuario) {
        if (!participantes.contains(usuario)) {
            participantes.add(usuario);
            totalParticipantes = participantes.size();
        }
    }
    
    public void removerParticipante(User usuario) {
        if (participantes.contains(usuario)) {
            participantes.remove(usuario);
            totalParticipantes = participantes.size();
        }
    }
    
    public void adicionarComentario(ComentarioDebate comentario) {
        comentarios.add(comentario);
        totalComentarios = comentarios.size();
    }
    
    public void adicionarEspecialista(User especialista) {
        if (!especialistas.contains(especialista)) {
            especialistas.add(especialista);
        }
    }
    
    public boolean isEvento() {
        return dataEventoAoVivo != null;
    }
    
    public boolean isEventoAtivo() {
        if (dataEventoAoVivo == null) {
            return false;
        }
        
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime fimEvento = dataEventoAoVivo.plusHours(2); // Evento dura 2 horas
        
        return agora.isAfter(dataEventoAoVivo) && agora.isBefore(fimEvento);
    }
} 