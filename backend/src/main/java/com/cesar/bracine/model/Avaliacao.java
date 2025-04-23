package com.cesar.bracine.model;

import com.cesar.bracine.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "avaliacao")
public class Avaliacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Integer stars;
    
    @Column(nullable = false, length = 500)
    private String comment;
    
    private Integer likes = 0;
    
    private LocalDateTime hourDate = LocalDateTime.now();

    public Avaliacao() {
    }

    public void like() {
        this.likes = this.likes == null ? 1 : this.likes + 1;
    }

    public Avaliacao(Filme film, User user, Integer stars, String comment) {
        this.film = film;
        this.user = user;
        this.stars = stars;
        this.comment = comment;
    }
}

