package com.cesar.bracine.domain.entities;

import java.time.Instant;

public class Avaliacao extends Comentario {
    private int nota;

    public Avaliacao(String texto, Usuario autor, Filme filme, int nota) {
        super(texto, autor, filme, Instant.now());
        this.nota = validarNota(nota);
    }

    private int validarNota(int nota) {
        if (nota < 1 || nota > 5) throw new IllegalArgumentException("Nota deve ser entre 1 e 5");
        return nota;
    }

    public int getNota() {
        return nota;
    }
}