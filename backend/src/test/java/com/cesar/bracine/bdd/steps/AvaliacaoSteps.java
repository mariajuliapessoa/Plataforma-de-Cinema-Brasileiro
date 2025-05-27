package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import com.cesar.bracine.domain.services.AvaliacaoService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AvaliacaoSteps {

    private final AvaliacaoService avaliacaoService;
    private final UsuarioSteps usuarioSteps;

    private Avaliacao avaliacaoSalva;
    private List<Avaliacao> avaliacoes;

    public AvaliacaoSteps(AvaliacaoService avaliacaoService, UsuarioSteps usuarioSteps) {
        this.avaliacaoService = avaliacaoService;
        this.usuarioSteps = usuarioSteps;
    }

    @Given("uma avaliação com texto {string}, autor {string}, filme {string}, nota {int}")
    public void criarAvaliacao(String texto, String idAutor, String idFilme, int nota) {
        String autorIdFinal = idAutor.equals("{id}") ? usuarioSteps.usuario.getId().getValue().toString() : idAutor;

        avaliacaoSalva = new Avaliacao(
                texto,
                new UsuarioId(UUID.fromString(autorIdFinal)),
                new FilmeId(UUID.fromString(idFilme)),
                nota
        );
    }

    @When("a avaliação for salva")
    public void salvarAvaliacao() {
        avaliacaoSalva = avaliacaoService.salvarAvaliacao(avaliacaoSalva);
    }

    @Then("a avaliação deve estar disponível na listagem")
    public void verificarAvaliacaoNaListagem() {
        avaliacoes = avaliacaoService.listarTodasAvaliacoes();
        boolean encontrado = avaliacoes.stream()
                .anyMatch(a -> a.getTexto().equalsIgnoreCase(avaliacaoSalva.getTexto()) &&
                        a.getNota() == avaliacaoSalva.getNota());
        assertTrue(encontrado);
    }
}
