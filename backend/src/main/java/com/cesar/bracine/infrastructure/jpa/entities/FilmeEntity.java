package com.cesar.bracine.infrastructure.jpa.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "filmes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmeEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String diretor;

    @Column(nullable = false, length = 1000)
    private String sinopse;

    @Column(nullable = false)
    private int anoLancamento;

    @Column(nullable = false)
    private double avaliacao;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "filme_generos", joinColumns = @JoinColumn(name = "filme_id"))
    @Column(name = "genero")
    private List<String> generos;

    @Column(nullable = false)
    private String paisOrigem;

    @Column(nullable = false)
    private String bannerUrl;
}
