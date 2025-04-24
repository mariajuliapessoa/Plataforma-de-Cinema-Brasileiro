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
@Table(name = "lista_filmes")
public class ListaFilmes {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    private String descricao;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criador_id", nullable = false)
    private User criador;
    
    private boolean publica = true;
    
    private boolean colaborativa = false;
    
    private boolean permiteVotacao = false;
    
    private LocalDateTime dataCriacao = LocalDateTime.now();
    
    @ManyToMany
    @JoinTable(
        name = "lista_filmes_colaboradores",
        joinColumns = @JoinColumn(name = "lista_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> colaboradores = new HashSet<>();
    
    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemLista> itens = new ArrayList<>();
    
    @OneToMany(mappedBy = "lista", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioList> comentarios = new ArrayList<>();
    
    public ListaFilmes() {
    }
    
    public ListaFilmes(String nome, String descricao, User criador, boolean publica, boolean colaborativa) {
        this.nome = nome;
        this.descricao = descricao;
        this.criador = criador;
        this.publica = publica;
        this.colaborativa = colaborativa;
    }
    
    public boolean podeEditar(User usuario) {
        return criador.equals(usuario) || (colaborativa && colaboradores.contains(usuario));
    }
    
    public boolean podeVisualizar(User usuario) {
        return publica || criador.equals(usuario) || colaboradores.contains(usuario);
    }
} 