package com.cesar.bracine.bdd.memoria.servico;

import com.cesar.bracine.model.*;
import com.cesar.bracine.model.user.User;

import java.time.LocalDateTime;
import java.util.*;

public class DebateFilmeServiceBDD {

    private final List<DebateFilme> debates = new ArrayList<>();
    private final List<ComentarioDebate> comentarios = new ArrayList<>();
    private DebateFilme debateAtual;
    private final Map<User, List<Notificacao>> notificacoes = new HashMap<>();

    public DebateFilme criarDebate(Filme filme, String titulo, String descricao, User criador) {
        DebateFilme debate = new DebateFilme(filme, titulo, descricao, criador);
        debates.add(debate);
        debateAtual = debate;
        return debate;
    }

    public void comentar(User autor, String texto) {
        ComentarioDebate comentario = new ComentarioDebate();
        comentario.setAuthor(autor);
        comentario.setText(texto);
        comentario.setDiscussion(debateAtual);
        debateAtual.adicionarComentario(comentario);
        comentarios.add(comentario);
        notificarParticipantes(comentario);
    }

    public void responder(ComentarioDebate pai, User autor, String texto) {
        ComentarioDebate resposta = new ComentarioDebate();
        resposta.setAuthor(autor);
        resposta.setText(texto);
        resposta.setDiscussion(debateAtual);
        resposta.setParentComment(pai);
        pai.addReply(resposta);
        comentarios.add(resposta);
    }

    public void curtir(ComentarioDebate comentario, User user) {
        comentario.addLike(user);
    }

    public void denunciar(ComentarioDebate comentario) {
        comentario.report();
    }

    public List<DebateFilme> listarDebatesEmAlta() {
        return debates.stream()
                .filter(d -> d.getComentarios().size() >= 3)
                .toList();
    }

    public void agendarEvento(DebateFilme debate, LocalDateTime horario) {
        debate.setDataEventoAoVivo(horario);
        debateAtual = debate;
    }

    public boolean podeParticiparEvento(DebateFilme debate) {
        return debate.isEventoAtivo();
    }

    public void notificarParticipantes(ComentarioDebate comentario) {
        DebateFilme debate = comentario.getDiscussion();
        for (User participante : debate.getParticipantes()) {
            notificacoes
                    .computeIfAbsent(participante, u -> new ArrayList<>())
                    .add(new Notificacao(participante, "Novo comentário em " + debate.getTitulo(), "COMENTARIO", null));
        }
    }

    public List<Notificacao> getNotificacoes(User user) {
        return notificacoes.getOrDefault(user, Collections.emptyList());
    }

    public ComentarioDebate getComentarioMaisRecente() {
        return comentarios.isEmpty() ? null : comentarios.get(comentarios.size() - 1);
    }

    public DebateFilme getDebateAtual() {
        return debateAtual;
    }
}
