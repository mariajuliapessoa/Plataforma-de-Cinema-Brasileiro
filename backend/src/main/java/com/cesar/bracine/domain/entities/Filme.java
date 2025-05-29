package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.valueobjects.FilmeId;

import java.time.Year;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Filme {

    private final FilmeId id;
    private final String titulo;
    private final String diretor;
    private final String sinopse;
    private final int anoLancamento;
    private final double avaliacao;
    private final List<String> generos;
    private final String paisOrigem;
    private final String bannerUrl;

    public Filme(FilmeId id, String titulo, String diretor, String sinopse, int anoLancamento, double avaliacao, List<String> generos, String paisOrigem, String bannerUrl) {
        this.id = id != null ? id : new FilmeId(UUID.randomUUID());
        this.titulo = validarTitulo(titulo);
        this.diretor = validarDiretor(diretor);
        this.sinopse = validarSinopse(sinopse);
        this.anoLancamento = validarAnoLancamento(anoLancamento);
        this.avaliacao = validarAvaliacao(avaliacao);
        this.generos = validarGeneros(generos);
        this.paisOrigem = validarPaisOrigem(paisOrigem);
        this.bannerUrl = validarBannerUrl(bannerUrl);
    }

    public Filme(String titulo, String diretor, String descricao, int anoLancamento, double avaliacao, List<String> generos, String paisOrigem, String bannerUrl) {
        this(null, titulo, diretor, descricao, anoLancamento, avaliacao, generos, paisOrigem, bannerUrl);
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

    private double validarAvaliacao(double avaliacao) {
        if (avaliacao < 0)
            throw new IllegalArgumentException("Avaliação deve ser maior que zero");
        return avaliacao;
    }

    private String validarSinopse(String descricao) {
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição é obrigatório");
        }
        return descricao.trim();
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

    public FilmeId getId() {
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

    public String getSinopse() {
        return sinopse;
    }

    public double getAvaliacao() {
        return avaliacao;
    }
}
