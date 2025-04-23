package com.cesar.bracine.bdd.memoria.servico;

import com.cesar.bracine.model.Desafio;
import com.cesar.bracine.model.user.User;

import java.time.LocalDate;

public class DesafioServiceBDD {

    private Desafio createdChallenge;

    public void createChallenge(User creator) {
        createdChallenge = new Desafio();
        createdChallenge.setTitulo("Thematic Challenge");
        createdChallenge.setDescricao("A new challenge to engage users.");
        createdChallenge.setCriador(creator);
        createdChallenge.setDataFim(LocalDate.now().plusDays(7));
    }

    public boolean isChallengePublished() {
        return createdChallenge != null && createdChallenge.getTitulo() != null;
    }

    public Desafio getCreatedChallenge() {
        return createdChallenge;
    }
}
