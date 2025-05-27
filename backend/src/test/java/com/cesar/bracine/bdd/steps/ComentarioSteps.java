package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.domain.services.ComentarioService;
import com.cesar.bracine.domain.valueobjects.ComentarioId;
import com.cesar.bracine.domain.valueobjects.DebateId;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComentarioSteps {

    private final ComentarioService comentarioService;
    private final UsuarioSteps usuarioSteps;
    private final FilmeSteps filmeSteps;
    private final DebateSteps debateSteps;

    private Comentario comentarioSalvo;
    private List<Comentario> comentarios;

    public ComentarioSteps(ComentarioService comentarioService, UsuarioSteps usuarioSteps, FilmeSteps filmeSteps, DebateSteps debateSteps) {
        this.comentarioService = comentarioService;
        this.usuarioSteps = usuarioSteps;
        this.filmeSteps = filmeSteps;
        this.debateSteps = debateSteps;
    }

    @Given("um comentário com texto {string}, autor {string}, filme {string}, debate {string}")
    public void criarComentario(String texto, String idAutor, String idFilme, String idDebate) {
        String autorIdFinal = idAutor.equals("{id}") ? usuarioSteps.usuario.getId().getValue().toString() : idAutor;
        String filmeIdFinal = idFilme.equals("{id}") ? filmeSteps.filmeSalvo.getId().getValue().toString() : idFilme;
        String debateIdFinal = idDebate.equals("{id}") ? debateSteps.debateSalvo.getId().toString() : idDebate;

        comentarioSalvo = new Comentario(
                null,
                texto,
                new UsuarioId(UUID.fromString(autorIdFinal)),
                new FilmeId(UUID.fromString(filmeIdFinal)),
                new DebateId(UUID.fromString(debateIdFinal)),
                Instant.now()
        );
    }

    @When("o comentário for salvo")
    public void salvarComentario() {
        comentarioSalvo = comentarioService.salvarComentario(comentarioSalvo);
    }

    @Then("o comentário deve estar disponível na listagem")
    public void verificarComentarioNaListagem() {
        comentarios = comentarioService.listarTodosComentarios();
        boolean encontrado = comentarios.stream()
                .anyMatch(c -> c.getTexto().equalsIgnoreCase(comentarioSalvo.getTexto()));
        assertTrue(encontrado);
    }
}
