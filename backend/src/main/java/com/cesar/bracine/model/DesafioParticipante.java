package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "desafio_participante")
public class DesafioParticipante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "desafio_id", nullable = false)
    private Desafio desafio;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User usuario;
    
    private LocalDate dataInscricao = LocalDate.now();
    
    private boolean concluido = false;
    
    private LocalDate dataConclusao;
    
    @ManyToMany
    @JoinTable(
        name = "desafio_participante_filmes_vistos",
        joinColumns = @JoinColumn(name = "desafio_participante_id"),
        inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    private Set<Filme> filmesVistos = new HashSet<>();
    
    private boolean conquistaDesbloqueada = false;
    
    public DesafioParticipante() {
    }
    
    public DesafioParticipante(Desafio desafio, User usuario) {
        this.desafio = desafio;
        this.usuario = usuario;
    }
    
    public int getProgresso() {
        int totalFilmes = desafio.getTotalFilmes();
        if (totalFilmes == 0) return 0;
        
        return (filmesVistos.size() * 100) / totalFilmes;
    }
    
    public void marcarFilmeComoVisto(Filme filme) {
        filmesVistos.add(filme);
        verificarConclusao();
    }
    
    public void verificarConclusao() {
        if (filmesVistos.size() >= desafio.getFilmes().size()) {
            concluido = true;
            dataConclusao = LocalDate.now();
            conquistaDesbloqueada = true;
        }
    }
} 