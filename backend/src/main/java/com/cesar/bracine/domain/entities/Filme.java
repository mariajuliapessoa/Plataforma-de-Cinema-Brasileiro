package com.cesar.bracine.domain.entities;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Filme {

    private final UUID id;
    private final String titulo;
    private final String diretor;
    private final int anoLancamento;
    private final List<String> generos;
    private final String paisOrigem;
    private final String bannerUrl;

    public Filme(UUID id, String titulo, String diretor, int anoLancamento, List<String> generos, String paisOrigem, String bannerUrl) {
        this.id = id != null ? id : UUID.randomUUID();
        this.titulo = validarTitulo(titulo);
        this.diretor = validarDiretor(diretor);
        this.anoLancamento = validarAnoLancamento(anoLancamento);
        this.generos = validarGeneros(generos);
        this.paisOrigem = validarPaisOrigem(paisOrigem);
        this.bannerUrl = validarBannerUrl(bannerUrl);
    }

    public Filme(String titulo, String diretor, int anoLancamento, List<String> generos, String paisOrigem, String bannerUrl) {
        this(null, titulo, diretor, anoLancamento, generos, paisOrigem, bannerUrl);
    }

    // Validações
    private String validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty())
            throw new IllegalArgumentException("Título do filme é obrigatório");
        return titulo.trim();
    }

    private String validarDiretor(String diretor) {
        if (diretor == null || diretor.trim().isEmpty())
            throw new IllegalArgumentException("Diretor é obrigatório");
        return diretor.trim();
    }

    private int validarAnoLancamento(int ano) {
        int anoAtual = Year.now().getValue();
        if (ano < 1888 || ano > anoAtual)
            throw new IllegalArgumentException("Ano de lançamento inválido");
        return ano;
    }

    private List<String> validarGeneros(List<String> generos) {
        if (generos == null || generos.isEmpty())
            throw new IllegalArgumentException("O filme deve conter ao menos um gênero");
        return List.copyOf(generos);
    }

    private String validarPaisOrigem(String pais) {
        if (pais == null || pais.trim().isEmpty())
            throw new IllegalArgumentException("País de origem é obrigatório");
        return pais.trim();
    }

    private String validarBannerUrl(String bannerUrl) {
        if (bannerUrl == null || bannerUrl.trim().isEmpty())
            throw new IllegalArgumentException("Banner do filme é obrigatório");
        return bannerUrl.trim();
    }

    // Método de domínio
    public boolean ehClassico() {
        return (Year.now().getValue() - anoLancamento) > 20;
    }

    public UUID getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDiretor() {
        return diretor;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public List<String> getGeneros() {
        return Collections.unmodifiableList(generos);
    }

    public String getPaisOrigem() {
        return paisOrigem;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }
}
