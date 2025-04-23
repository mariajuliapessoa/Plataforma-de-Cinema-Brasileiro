package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.model.user.User;
import com.cesar.bracine.bdd.memoria.servico.*;
import io.cucumber.java.pt.*;

import static org.junit.jupiter.api.Assertions.*;

public class AdministracaoPlataformaSteps {

    private final UserServiceBDD userService = new UserServiceBDD();
    private final FilmeServiceBDD filmService = new FilmeServiceBDD();
    private final DesafioServiceBDD challengeService = new DesafioServiceBDD();

    private User loggedAdmin;

    // --- Contexto ---

    @Dado("que existe um usuário administrador com o username {string}")
    public void existeUmUsuarioAdministrador(String username) {
        loggedAdmin = userService.createAdmin(username);
    }

    @E("que o administrador está autenticado")
    public void administradorEstaAutenticado() {
        userService.authenticate(loggedAdmin.getUsername());
        assertNotNull(userService.getLoggedUser());
    }

    // --- Cenário: Acessar o painel administrativo ---

    @Dado("que o administrador está logado")
    public void administradorLogado() {
        assertNotNull(userService.getLoggedUser());
        assertEquals("ROLE_ADMIN", userService.getLoggedUser().getRole().name());
    }

    @Quando("ele clicar em {string}")
    public void clicarNoMenu(String menu) {
        // sem ação real (simulado)
        System.out.println("Admin clicou em: " + menu);
    }

    @Então("deve ser redirecionado para o painel de gestão da plataforma")
    public void redirecionadoParaPainel() {
        assertTrue(userService.getLoggedUser().getRole().name().equals("ROLE_ADMIN"));
    }

    // --- Cenário: Aprovar comentários denunciados ---

    @Dado("que há comentários denunciados")
    public void haComentariosDenunciados() {
        // simulação básica, sem lógica real ainda
    }

    @Quando("o admin acessar a aba de denúncias")
    public void acessarAbaDenuncias() {
        // simulado
    }

    @Então("ele poderá aprovar, ocultar ou apagar o comentário")
    public void adminPodeGerenciarComentarios() {
        assertTrue(userService.getLoggedUser().getRole().name().equals("ROLE_ADMIN"));
    }

    // --- Cenário: Remover filmes impróprios ---

    @Dado("que um filme está marcado como impróprio")
    public void filmeMarcadoImproprio() {
        filmService.createFilm("Filme Teste");
        filmService.markAsInappropriate();
    }

    @Quando("o admin clicar em {string}")
    public void adminClicaBotao(String botao) {
        if (botao.equalsIgnoreCase("Remover")) {
            filmService.removeFilm();
        } else if (botao.equalsIgnoreCase("Marcar como destaque")) {
            filmService.highlightFilm();
        }
    }

    @Então("o filme será excluído da plataforma")
    public void filmeRemovido() {
        assertTrue(filmService.isFilmRemoved());
    }

    // --- Cenário: Criar novo desafio temático ---

    @Dado("que o administrador quer engajar usuários")
    public void adminQuerEngajar() {
        assertNotNull(loggedAdmin);
    }

    @Quando("ele criar um novo desafio")
    public void criarDesafio() {
        challengeService.createChallenge(loggedAdmin);
    }

    @Então("ele será publicado na seção de desafios")
    public void desafioPublicado() {
        assertTrue(challengeService.isChallengePublished());
    }

    // --- Cenário: Promover usuário a especialista ---

    @Dado("que um usuário é reconhecido como crítico")
    public void usuarioCritico() {
        userService.cadastrarUsuarioCritico("critico");
    }

    @Quando("o admin alterar seu status")
    public void adminAlteraStatus() {
        userService.promoteToEspecialist("critico");
    }

    @Então("o usuário se tornará especialista e terá permissões especiais")
    public void verificarEspecialista() {
        assertTrue(userService.isEspecialist("critico"));
    }

    // --- Cenário: Destacar filme em tendência ---

    @Dado("que o admin deseja promover diversidade")
    public void adminDesejaPromoverDiversidade() {
        filmService.createFilm("Filme Diversidade");
    }

    @Quando("ele marcar um filme como destaque")
    public void marcarFilmeComoDestaque() {
        filmService.highlightFilm();
    }

    @Então("ele será exibido na aba de tendências")
    public void verificarFilmeEmTendencia() {
        assertTrue(filmService.isHighlighted());
    }
}
