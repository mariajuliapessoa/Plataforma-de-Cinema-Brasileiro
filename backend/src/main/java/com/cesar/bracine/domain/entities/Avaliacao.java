package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;

import java.time.Instant;

public class Avaliacao extends Comentario {
    private int nota;

    public Avaliacao(String texto, UsuarioId autor, FilmeId filme, int nota) {
        super(texto, autor, filme, Instant.now());
        this.nota = validarNota(nota);
    }

    private int validarNota(int nota) {
        if (nota < 1 || nota > 10) throw new IllegalArgumentException("Nota deve ser entre 1 e 10");
        return nota;
    }

    public int getNota() {
        return nota;
    }
}