package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "desafio")
public class Desafio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String titulo;
    
    private String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id", nullable = false)
    private User criador;
    
    private boolean ativo = true;
    
    private LocalDate dataInicio = LocalDate.now();
    
    private LocalDate dataFim;
    
    @ManyToMany
    @JoinTable(
        name = "desafio_filmes",
        joinColumns = @JoinColumn(name = "desafio_id"),
        inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    private Set<Filme> filmes = new HashSet<>();
    
    @OneToMany(mappedBy = "desafio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DesafioParticipante> participantes = new ArrayList<>();
    
    private int pontosConquista = 100;
    
    private String premioDesafio;
    
    public Desafio() {
    }
    
    public Desafio(String titulo, String descricao, User criador, LocalDate dataFim, int pontosConquista, String premioDesafio) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.criador = criador;
        this.dataFim = dataFim;
        this.pontosConquista = pontosConquista;
        this.premioDesafio = premioDesafio;
    }
    
    public int getTotalFilmes() {
        return filmes.size();
    }
    
    public boolean isDesafioAtivo() {
        return ativo && (dataFim == null || !LocalDate.now().isAfter(dataFim));
    }
} 