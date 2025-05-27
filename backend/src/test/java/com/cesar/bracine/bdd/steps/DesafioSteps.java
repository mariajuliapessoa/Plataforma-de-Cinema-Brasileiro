package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.services.DesafioService;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DesafioSteps {

    private final DesafioService desafioService;
    private final UsuarioSteps usuarioSteps;

    private Desafio desafioSalvo;
    private List<Desafio> desafios;

    public DesafioSteps(DesafioService desafioService, UsuarioSteps usuarioSteps) {
        this.desafioService = desafioService;
        this.usuarioSteps = usuarioSteps;
    }

    @Given("um desafio com titulo {string}, descricao {string}, pontos {int} para o usuario {string}")
    public void criarDesafio(String titulo, String descricao, int pontos, String idUsuario) {
        if (idUsuario.equals("{id}")) {
            idUsuario = usuarioSteps.usuario.getId().getValue().toString();
        }

        desafioSalvo = new Desafio(
                null,
                titulo,
                descricao,
                pontos,
                new UsuarioId(java.util.UUID.fromString(idUsuario)),
                LocalDate.now(),
                LocalDate.now().plusDays(7),
                false
        );
    }

    @When("o desafio for salvo")
    public void salvarDesafio() {
        desafioSalvo = desafioService.salvarDesafio(desafioSalvo);
    }

    @Then("o desafio deve estar disponível na listagem")
    public void verificarDesafioNaListagem() {
        desafios = desafioService.listarTodosDesafios();
        boolean encontrado = desafios.stream()
                .anyMatch(d -> d.getTitulo().equalsIgnoreCase(desafioSalvo.getTitulo()));
        assertTrue(encontrado);
    }
}
