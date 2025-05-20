package com.cesar.bracine.bdd.memoria.servico;

import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AvaliacaoServiceBDD {

    private final List<Avaliacao> avaliacoes = new ArrayList<>();

    public Avaliacao salvar(Filme filme, User user, int estrelas, String comentario) {
        if (comentario == null || comentario.trim().length() < 20) {
            throw new IllegalArgumentException("Comentário obrigatório com no mínimo 20 caracteres.");
        }
        Avaliacao avaliacao = new Avaliacao(filme, user, estrelas, comentario);
        filme.getAvaliacoes().add(avaliacao);
        avaliacoes.add(avaliacao);
        System.out.println(">> Avaliação salva: " + comentario);
        return avaliacao;
    }

    public List<Avaliacao> listarPorFilme(Filme filme) {
        List<Avaliacao> list = avaliacoes.stream()
                .filter(a -> a.getFilm().equals(filme))
                .toList();
        System.out.println(">> Avaliações encontradas para o filme '" + filme.getTitulo() + "': " + list.size());
        return list;
    }

    public void remover(Avaliacao avaliacao) {
        avaliacoes.remove(avaliacao);
        avaliacao.getFilm().getAvaliacoes().remove(avaliacao);
    }

    public Optional<Avaliacao> buscarPorFilmeEUsuario(Filme filme, User user) {
        return avaliacoes.stream()
                .filter(a -> a.getFilm().equals(filme) && a.getUser().equals(user))
                .findFirst();
    }

    public double calcularMedia(Filme filme) {
        return listarPorFilme(filme).stream()
                .mapToInt(Avaliacao::getStars)
                .average().orElse(0.0);
    }

    public void curtir(Avaliacao avaliacao) {
        avaliacao.like();
    }
}
