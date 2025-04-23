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
    private Desafio challenge;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

    private LocalDate enrollmentDate = LocalDate.now();

    private boolean completed = false;

    private LocalDate completionDate;

    @ManyToMany
    @JoinTable(
            name = "desafio_participante_filmes_vistos",
            joinColumns = @JoinColumn(name = "desafio_participante_id"),
            inverseJoinColumns = @JoinColumn(name = "filme_id")
    )
    private Set<Filme> watchedFilms = new HashSet<>();

    private boolean rewardUnlocked = false;

    public DesafioParticipante() {
    }

    public DesafioParticipante(Desafio challenge, User user) {
        this.challenge = challenge;
        this.user = user;
    }

    public int getProgress() {
        int totalFilms = challenge.getFilmes().size();
        if (totalFilms == 0) return 0;

        return (watchedFilms.size() * 100) / totalFilms;
    }

    public void markFilmAsWatched(Filme film) {
        watchedFilms.add(film);
        checkCompletion();
    }

    public void checkCompletion() {
        if (watchedFilms.size() >= challenge.getFilmes().size()) {
            completed = true;
            completionDate = LocalDate.now();
            rewardUnlocked = true;
        }
    }
}
