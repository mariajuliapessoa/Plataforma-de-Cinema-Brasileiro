package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.bdd.memoria.servico.*;
import com.cesar.bracine.model.*;
import com.cesar.bracine.model.user.User;
import io.cucumber.java.pt.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DebateFilmeSteps {

    private final UserServiceBDD userService = new UserServiceBDD();
    private final FilmeServiceBDD filmeService = new FilmeServiceBDD();
    private final DebateFilmeServiceBDD debateService = new DebateFilmeServiceBDD();

    private Filme filme;
    private User usuario;
    private ComentarioDebate comentarioPai;

    @Dado("que o usuário está em uma sala de discussão")
    public void que_o_usuário_está_em_uma_sala_de_discussão() {
        filme = new Filme("Bacurau");
        usuario = new User("usuario_teste");
        debateService.criarDebate(filme, "Discussão Bacurau", "Debate sobre o filme Bacurau", usuario);
    }

    @Quando("ele escrever e enviar um comentário")
    public void ele_escrever_e_enviar_um_comentário() {
        debateService.comentar(usuario, "Primeiro comentário do debate!");
    }

    @Então("o comentário deve aparecer na discussão")
    public void o_comentário_deve_aparecer_na_discussão() {
        ComentarioDebate comentario = debateService.getComentarioMaisRecente();
        assertNotNull(comentario);
        assertEquals("Primeiro comentário do debate!", comentario.getText());
    }

    @Dado("que há um comentário existente")
    public void que_há_um_comentário_existente() {
        filme = new Filme("Bacurau");
        usuario = new User("usuario_teste");
        debateService.criarDebate(filme, "Discussão Bacurau", "Debate sobre o filme Bacurau", usuario);
        comentarioPai = new ComentarioDebate();
        comentarioPai.setText("Comentário original");
        comentarioPai.setAuthor(usuario);
        comentarioPai.setDiscussion(debateService.getDebateAtual());
        debateService.getDebateAtual().adicionarComentario(comentarioPai);
    }

    @Quando("o usuário clicar em {string}")
    public void o_usuário_clicar_em(String botao) {
        if (botao.equalsIgnoreCase("Responder")) {
            debateService.responder(comentarioPai, usuario, "Resposta ao comentário original");
        } else if (botao.equalsIgnoreCase("Denunciar")) {
            debateService.denunciar(comentarioPai);
        }
    }

    @Então("ele poderá adicionar uma resposta diretamente abaixo")
    public void ele_poderá_adicionar_uma_resposta_diretamente_abaixo() {
        assertFalse(comentarioPai.getReplies().isEmpty());
        assertEquals("Resposta ao comentário original", comentarioPai.getReplies().get(0).getText());
    }

    @Dado("que um comentário viola as regras")
    public void comentarioViolando() {
        usuario = new User("usuario_teste");
        filme = new Filme("Bacurau");
        debateService.criarDebate(filme, "Debate Regras", "Discussão sobre regras", usuario);
        comentarioPai = new ComentarioDebate();
        comentarioPai.setText("Comentário ofensivo");
        comentarioPai.setAuthor(usuario);
        comentarioPai.setDiscussion(debateService.getDebateAtual());
        debateService.getDebateAtual().adicionarComentario(comentarioPai);
    }

    @Então("o sistema deve notificar os moderadores")
    public void notificarModeradores() {
        assertTrue(comentarioPai.isReported());
    }

    @Dado("que há discussões com muita atividade")
    public void discussoesComAtividade() {
        usuario = new User("usuario_teste");
        filme = new Filme("Bacurau");
        debateService.criarDebate(filme, "Debate Ativo", "Muito movimentado", usuario);
        for (int i = 0; i < 3; i++) {
            debateService.comentar(usuario, "Comentário " + i);
        }
    }

    @Quando("o usuário acessar a aba {string}")
    public void acessarAba(String aba) {
        assertEquals("Debates em alta", aba);
    }

    @Então("ele verá os mais comentados recentemente")
    public void verDebatesPopulares() {
        List<DebateFilme> emAlta = debateService.listarDebatesEmAlta();
        assertFalse(emAlta.isEmpty());
    }

    @Dado("que um evento de debate está agendado")
    public void eventoAgendado() {
        usuario = new User("usuario_teste");
        DebateFilme debate = new DebateFilme(new Filme("Bacurau"), "Debate Especialista", "Com especialista", usuario);
        debateService.agendarEvento(debate, LocalDateTime.now().minusMinutes(5));
    }

    @Quando("o horário chegar")
    public void horarioChegar() {
        assertTrue(debateService.podeParticiparEvento(debateService.getDebateAtual()));
    }

    @Então("o usuário poderá entrar na sala ao vivo")
    public void entrarSalaAoVivo() {
        assertTrue(debateService.getDebateAtual().isEventoAtivo());
    }

    @Dado("que o usuário participa de uma discussão")
    public void usuarioParticipaDiscussao() {
        usuario = new User("usuario_teste");
        filme = new Filme("Bacurau");
        debateService.criarDebate(filme, "Notificações", "Notificações novas", usuario);
        DebateFilme debate = debateService.getDebateAtual();
        debate.adicionarParticipante(usuario);
    }

    @Quando("houver um novo comentário")
    public void novoComentario() {
        debateService.comentar(usuario, "Novo comentário gerador de notificação");
    }

    @Então("o sistema deve enviar uma notificação")
    public void notificacaoRecebida() {
        List<Notificacao> notificacoes = debateService.getNotificacoes(usuario);
        assertFalse(notificacoes.isEmpty());
    }
}
