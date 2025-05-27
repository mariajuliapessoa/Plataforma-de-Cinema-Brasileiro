package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.services.DebateService;
import com.cesar.bracine.domain.valueobjects.DebateId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DebateSteps {

    private final DebateService debateService;
    private final UsuarioSteps usuarioSteps;

    public Debate debateSalvo;
    private List<Debate> debates;

    public DebateSteps(DebateService debateService, UsuarioSteps usuarioSteps) {
        this.debateService = debateService;
        this.usuarioSteps = usuarioSteps;
    }

    @Given("um debate com titulo {string} e criador {string}")
    public void criarDebate(String titulo, String idCriador) {
        String criadorIdFinal = idCriador.equals("{id}") ? usuarioSteps.usuario.getId().getValue().toString() : idCriador;

        debateSalvo = new Debate(
                null,
                titulo,
                new UsuarioId(UUID.fromString(criadorIdFinal))
        );
    }

    @When("o debate for salvo")
    public void salvarDebate() {
        debateSalvo = debateService.salvarDebate(debateSalvo, usuarioSteps.usuario);
    }

    @Then("o debate deve estar disponível na listagem")
    public void verificarDebateNaListagem() {
        debates = debateService.listarTodosDebates();
        boolean encontrado = debates.stream()
                .anyMatch(d -> d.getTitulo().equalsIgnoreCase(debateSalvo.getTitulo()));
        assertTrue(encontrado);
    }
}
