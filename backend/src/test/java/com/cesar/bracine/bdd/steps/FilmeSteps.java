package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.services.FilmeService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmeSteps {

    private final FilmeService filmeService;

    public Filme filmeSalvo;
    private List<Filme> filmes;

    public FilmeSteps(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @Given("um filme com titulo {string}, diretor {string}, ano {int}, generos {string}, país {string}, banner {string}")
    public void criarFilme(String titulo, String diretor, int ano, String generos, String pais, String banner) {
        List<String> listaGeneros = Arrays.asList(generos.split(","));
        filmeSalvo = new Filme(null, titulo, diretor, ano, listaGeneros, pais, banner);
    }

    @When("o filme for salvo")
    public void salvarFilme() {
        System.out.println("filme salvo: " + filmeSalvo.toString());
        filmeSalvo = filmeService.salvarFilme(filmeSalvo);
    }

    @Then("o filme deve estar disponível na listagem")
    public void verificarFilmeNaListagem() {
        filmes = filmeService.listarTodosFilmes();
        System.out.println("FILMES SALVOS: " + filmes);
        boolean encontrado = filmes.stream()
                .anyMatch(f -> f.getTitulo().equalsIgnoreCase(filmeSalvo.getTitulo()));
        assertTrue(encontrado);
    }
}
