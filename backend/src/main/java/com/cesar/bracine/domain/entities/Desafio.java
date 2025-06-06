package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.valueobjects.DesafioId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Desafio {

    private final DesafioId id;
    private final String titulo;
    private final String descricao;
    private final int pontos;
    private final UsuarioId destinatario;
    private final LocalDate dataCriacao;
    private final LocalDate prazo;
    private boolean concluido;

    public Desafio(DesafioId id, String titulo, String descricao, int pontos, UsuarioId destinatario, LocalDate dataCriacao, LocalDate prazo, boolean concluido) {
        this.id = id != null ? id : new DesafioId(UUID.randomUUID());
        this.titulo = validarTitulo(titulo);
        this.descricao = validarDescricao(descricao);
        this.pontos = validarPontos(pontos);
        this.destinatario = Objects.requireNonNull(destinatario);
        this.dataCriacao = dataCriacao != null ? dataCriacao : LocalDate.now();
        this.prazo = prazo;
        this.concluido = concluido;
    }

    public Desafio(String titulo, String descricao, int pontos, UsuarioId destinatario, LocalDate prazo) {
        this(null, titulo, descricao, pontos, destinatario, LocalDate.now(), prazo, false);
    }

    public void concluir() {
        if (this.concluido) {
            throw new IllegalStateException("Desafio já está concluído");
        }

        if (LocalDate.now().isAfter(prazo)) {
            throw new IllegalStateException("Não é possível concluir um desafio após o prazo");
        }

        this.concluido = true;
    }

    private String validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) throw new IllegalArgumentException("Título do desafio é obrigatório");
        return titulo.trim();
    }

    private String validarDescricao(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) throw new IllegalArgumentException("Descrição do desafio é obrigatória");
        return descricao.trim();
    }

    private int validarPontos(int pontos) {
        if (pontos <= 0) throw new IllegalArgumentException("Desafio deve oferecer pontos positivos");
        return pontos;
    }

    // Getters
    public DesafioId getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getPontos() {
        return pontos;
    }

    public UsuarioId getDestinatario() {
        return destinatario;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public boolean isConcluido() {
        return concluido;
    }
}
