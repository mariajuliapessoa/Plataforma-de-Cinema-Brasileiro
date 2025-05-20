package com.cesar.bracine.bdd.memoria.servico;

import com.cesar.bracine.model.Filme;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilmeServiceBDD {

    private Filme currentFilm;
    private final List<Filme> filmesCadastrados = new ArrayList<>();

    public Filme createFilm(String title) {
        currentFilm = new Filme();
        currentFilm.setTitulo(title);
        filmesCadastrados.add(currentFilm);
        return currentFilm;
    }

    public Filme createFilm(String title, String descricao, String genero, String diretor, Integer ano, Double nota) {
        Filme filme = new Filme();
        filme.setTitulo(title);
        filme.setDescricao(descricao);
        filme.setGenero(genero);
        filme.setDiretor(diretor);
        filme.setAno(ano);
        filme.setNota(nota);
        filmesCadastrados.add(filme);
        return filme;
    }

    public List<Filme> buscarPorTituloExato(String titulo) {
        return filmesCadastrados.stream()
                .filter(f -> f.getTitulo().equalsIgnoreCase(titulo))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarPorPalavraChave(String termo) {
        return filmesCadastrados.stream()
                .filter(f -> (f.getTitulo() + " " + f.getDescricao()).toLowerCase().contains(termo.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarPorGenero(String genero) {
        return filmesCadastrados.stream()
                .filter(f -> genero.equalsIgnoreCase(f.getGenero()))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarPorDiretor(String nomeDiretor) {
        return filmesCadastrados.stream()
                .filter(f -> nomeDiretor.equalsIgnoreCase(f.getDiretor()))
                .collect(Collectors.toList());
    }

    public List<Filme> buscarPorFiltros(Integer ano, Double notaMinima) {
        return filmesCadastrados.stream()
                .filter(f -> (ano == null || ano.equals(f.getAno())) &&
                        (notaMinima == null || (f.getNota() != null && f.getNota() >= notaMinima)))
                .collect(Collectors.toList());
    }

    public List<Filme> getPopulares() {
        return filmesCadastrados.stream()
                .filter(f -> f.getNota() != null && f.getNota() >= 4.0)
                .collect(Collectors.toList());
    }

    public void markAsInappropriate() {
        currentFilm.setDescricao("marked as inappropriate");
    }

    public void removeFilm() {
        filmesCadastrados.remove(currentFilm);
        currentFilm = null;
    }

    public void highlightFilm() {
        currentFilm.setNota(5.0);
        currentFilm.setGenero("Highlight");
    }

    public Filme getCurrentFilm() {
        return currentFilm;
    }

    public boolean isFilmRemoved() {
        return !filmesCadastrados.contains(currentFilm);
    }

    public boolean isHighlighted() {
        return currentFilm != null && "Highlight".equals(currentFilm.getGenero());
    }

    public List<Filme> getFilmesCadastrados() {
        return filmesCadastrados;
    }
}
