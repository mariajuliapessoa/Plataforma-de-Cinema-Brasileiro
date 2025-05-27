package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.enums.TipoNotificacao;
import com.cesar.bracine.domain.services.NotificacaoService;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NotificacaoSteps {

    private final NotificacaoService notificacaoService;
    private final UsuarioSteps usuarioSteps;

    private Notificacao notificacaoSalva;
    private List<Notificacao> notificacoes;

    public NotificacaoSteps(NotificacaoService notificacaoService, UsuarioSteps usuarioSteps) {
        this.notificacaoService = notificacaoService;
        this.usuarioSteps = usuarioSteps;
    }

    @Given("uma notificação do tipo {string} para o usuario {string} com a mensagem {string}")
    public void criarNotificacao(String tipo, String idUsuario, String mensagem) {
        TipoNotificacao tipoNotif = TipoNotificacao.valueOf(tipo);
        String idFinal = idUsuario.equals("{id}") ? usuarioSteps.usuario.getId().getValue().toString() : idUsuario;

        notificacaoSalva = new Notificacao(
                new UsuarioId(UUID.fromString(idFinal)),
                mensagem,
                tipoNotif
        );
    }

    @When("a notificação for salva")
    public void salvarNotificacao() {
        notificacaoSalva = notificacaoService.salvarNotificacao(notificacaoSalva);
    }

    @Then("a notificação deve estar disponível na listagem")
    public void verificarNotificacaoNaListagem() {
        notificacoes = notificacaoService.listarTodasNotificacoes();
        boolean encontrado = notificacoes.stream()
                .anyMatch(n -> n.getMensagem().equalsIgnoreCase(notificacaoSalva.getMensagem()));
        assertTrue(encontrado);
    }
}
