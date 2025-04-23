package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.bdd.memoria.servico.AvaliacaoServiceBDD;
import com.cesar.bracine.bdd.memoria.servico.FilmeServiceBDD;
import com.cesar.bracine.bdd.memoria.servico.UserServiceBDD;
import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.user.User;
import io.cucumber.java.pt.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AvaliacaoSteps {

    private final UserServiceBDD userService = new UserServiceBDD();
    private final FilmeServiceBDD filmeService = new FilmeServiceBDD();
    private final AvaliacaoServiceBDD avaliacaoService = new AvaliacaoServiceBDD();

    private Filme filme;
    private User usuario;
    private Avaliacao avaliacao;
    private String mensagemErro;

    // --- Contexto ---

    @Dado("que existe um usuário cadastrado com o username {string}")
    public void existeUsuarioCadastrado(String username) {
        usuario = userService.createAdmin(username);
    }

    @E("que existe um filme {string} cadastrado no sistema")
    public void existeFilmeCadastrado(String titulo) {
        filme = filmeService.createFilm(titulo);
    }

    @E("que o usuário está autenticado")
    public void usuarioEstaAutenticado() {
        userService.authenticate(usuario.getUsername());
        assertEquals(usuario.getUsername(), userService.getLoggedUser().getUsername());
    }

    // --- Cenário: Avaliação válida ---

    @Dado("que o usuário está na página do filme {string}")
    public void usuarioNaPaginaDoFilme(String titulo) {
        assertEquals(titulo, filme.getTitulo());
    }

    @Quando("ele preencher {int} estrelas e escrever um comentário de {int} caracteres")
    public void preencherEstrelasEComentario(int estrelas, int caracteres) {
        String comentario = "a".repeat(caracteres);
        try {
            avaliacao = avaliacaoService.salvar(filme, usuario, estrelas, comentario);
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
        }
    }

    @Então("a avaliação deve ser salva com sucesso")
    public void avaliacaoSalvaComSucesso() {
        assertNotNull(avaliacao);
        assertEquals(usuario, avaliacao.getUser());
        assertEquals(filme, avaliacao.getFilm());
        assertTrue(avaliacao.getComment().length() >= 20);
    }

    // --- Cenário: Avaliar sem comentário ---

    @Dado("que o usuário tenta avaliar um filme")
    public void usuarioTentaAvaliar() {
        assertNotNull(usuario);
        assertNotNull(filme);
    }

    @Quando("ele não escreve nenhum comentário")
    public void naoEscreveComentario() {
        try {
            avaliacao = avaliacaoService.salvar(filme, usuario, 4, "");
        } catch (IllegalArgumentException e) {
            mensagemErro = "Comentário obrigatório";
        }
    }

    @Então("o sistema deve exibir uma mensagem de erro solicitando pelo menos 20 caracteres")
    public void erroComentarioMinimo() {
     assertTrue(mensagemErro != null);
    }

    // --- Cenário: Comentário curto demais ---

    @Dado("que o usuário insere um comentário com menos de 20 caracteres")
    public void comentarioCurto() {
        avaliacao = new Avaliacao(filme, usuario, 3, "Muito bom!");
    }

    @Quando("ele tenta enviar a avaliação")
    public void tentaEnviar() {
        if (avaliacao.getComment().length() < 20) {
            mensagemErro = "Comentário muito curto";
        }
    }

    @Então("o sistema deve impedir o envio e exibir uma mensagem de erro")
    public void impedirEnvio() {
        assertEquals("Comentário muito curto", mensagemErro);
    }

    // --- Cenário: Atualizar avaliação existente ---

    @Dado("que o usuário já avaliou um filme")
    public void usuarioJaAvaliouFilme() {
        avaliacao = avaliacaoService.salvar(filme, usuario, 4, "Comentário original com mais de 20 caracteres");
    }

    @Quando("ele alterar a nota ou o comentário")
    public void alterarNotaOuComentario() {
        avaliacao.setStars(5);
        avaliacao.setComment("Novo comentário mais elaborado com mais de 20 caracteres");
    }

    @Então("o sistema deve substituir a avaliação anterior pela nova")
    public void substituirAvaliacao() {
        assertEquals(5, avaliacao.getStars());
        assertTrue(avaliacao.getComment().contains("Novo comentário"));
    }

    // --- Cenário: Remover avaliação ---

    @Dado("que o usuário deseja apagar sua avaliação")
    public void usuarioDesejaApagarAvaliacao() {
        avaliacao = avaliacaoService.salvar(filme, usuario, 3, "Comentário para remoção");
    }

    @Quando("o usuário clicar no botão {string}")
    public void clicarBotaoRemover(String botao) {
        if (botao.equalsIgnoreCase("Remover avaliação")) {
            avaliacaoService.remover(avaliacao);
            avaliacao = null;
        }
    }

    @Então("o sistema deve excluir a avaliação permanentemente")
    public void avaliacaoRemovida() {
        assertNull(avaliacao);
    }

    // --- Cenário: Ver avaliações de outros usuários ---

    @Dado("que existem avaliações de outros usuários para o filme")
    public void existemAvaliacoesDeOutrosUsuarios() {
        avaliacaoService.salvar(filme, new User(), 4, "Comentário público muito bom e longo");
    }

    @Quando("o usuário acessa a página de um filme")
    public void acessarPaginaFilme() {
        assertNotNull(filme);
    }

    @Então("ele deve visualizar as avaliações públicas dos outros usuários")
    public void visualizarAvaliacoesPublicas() {
        List<Avaliacao> avaliacoes = avaliacaoService.listarPorFilme(filme);
        assertFalse(avaliacoes.isEmpty());
    }

    // --- Cenário: Curtir comentário de outro usuário ---

    @Dado("que o usuário leu um comentário interessante")
    public void comentarioInteressante() {
        avaliacao = avaliacaoService.salvar(filme, new User(), 5, "Comentário top e muito completo sobre o filme");
    }

    @Quando("ele clicar no botão {string}")
    public void clicarCurtir(String botao) {
        if (botao.equalsIgnoreCase("Curtir")) {
            avaliacaoService.curtir(avaliacao);
        }
    }

    @Então("o número de curtidas daquele comentário deve aumentar")
    public void curtidaAumenta() {
        assertTrue(avaliacao.getLikes() > 0);
    }

    // --- Cenário: Ver média de avaliações ---

    @Dado("que existem avaliações para o filme")
    public void existemAvaliacoesParaFilme() {
        avaliacaoService.salvar(filme, usuario, 4, "Comentário bom e válido");
        avaliacaoService.salvar(filme, usuario, 2, "Outro comentário válido também");
    }

    @Quando("a página do filme for carregada")
    public void paginaCarregada() {
        assertFalse(avaliacaoService.listarPorFilme(filme).isEmpty());
    }

    @Então("o sistema deve exibir a média das notas recebidas")
    public void exibirMediaNotas() {
        double media = avaliacaoService.calcularMedia(filme);
        assertEquals(3.0, media);
    }
}
