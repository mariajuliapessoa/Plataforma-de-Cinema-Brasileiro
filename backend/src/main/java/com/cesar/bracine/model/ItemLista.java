package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "item_lista")
public class ItemLista {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lista_id", nullable = false)
    private ListaFilmes lista;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adicionado_por_id", nullable = false)
    private User adicionadoPor;
    
    private LocalDateTime dataAdicionado = LocalDateTime.now();
    
    private Integer votos = 0;
    
    @ManyToMany
    @JoinTable(
        name = "item_lista_votantes",
        joinColumns = @JoinColumn(name = "item_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private java.util.Set<User> votantes = new java.util.HashSet<>();
    
    public ItemLista() {
    }
    
    public ItemLista(ListaFilmes lista, Filme filme, User adicionadoPor) {
        this.lista = lista;
        this.filme = filme;
        this.adicionadoPor = adicionadoPor;
    }
    
    public boolean adicionarVoto(User usuario) {
        if (!votantes.contains(usuario)) {
            votantes.add(usuario);
            votos++;
            return true;
        }
        return false;
    }
    
    public boolean removerVoto(User usuario) {
        if (votantes.contains(usuario)) {
            votantes.remove(usuario);
            votos--;
            return true;
        }
        return false;
    }
} 