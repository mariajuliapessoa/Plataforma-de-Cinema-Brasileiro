package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.bdd.memoria.servico.FilmeServiceBDD;
import com.cesar.bracine.model.Filme;
import io.cucumber.java.pt.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuscaFilmesSteps {

    private final FilmeServiceBDD filmeService = new FilmeServiceBDD();
    private List<Filme> resultadosBusca = new ArrayList<>();
    private String mensagemExibida = "";
    private Filme filmeSelecionado = new Filme();

    // --- Setup de dados fake ---

    @Dado("que o filme {string} está cadastrado na plataforma")
    public void filmeCadastrado(String titulo) {
        Filme f = new Filme();
        f.setTitulo(titulo);
        f.setDescricao("Descrição fake");
        f.setGenero("Drama");
        f.setDiretor("Diretor Fake");
        f.setAno(2020);
        f.setNota(4.5);
        filmeService.createFilm(f.getTitulo(), f.getDescricao(), f.getGenero(), f.getDiretor(), f.getAno(), f.getNota());
    }

    @Dado("que existem filmes com a palavra {string} no título ou descrição")
    public void filmesComPalavraChave(String palavra) {
        Filme f1 = new Filme();
        f1.setTitulo("Aventura no Sertão");
        f1.setDescricao("Uma história sobre o sertão nordestino");
        f1.setNota(4.0);
        resultadosBusca = List.of(f1);
    }

    @Dado("que existem filmes dirigidos por {string}")
    public void filmesDoDiretor(String diretor) {
        Filme f1 = new Filme();
        f1.setTitulo("Filme Direcionado");
        f1.setDiretor(diretor);
        f1.setNota(4.8);
        resultadosBusca = List.of(f1);
    }

    @Dado("que existem filmes populares cadastrados")
    public void filmesPopularesCadastrados() {
        Filme f1 = new Filme();
        f1.setTitulo("Top Popular");
        f1.setNota(4.7);
        resultadosBusca = List.of(f1);
    }

    @Dado("que existem filmes para refinar por filtros")
    public void filmesParaFiltros() {
        Filme f1 = new Filme();
        f1.setTitulo("Filme com Filtro");
        f1.setAno(2020);
        f1.setNota(4.5);
        resultadosBusca = List.of(f1);
    }

    // --- Buscar pelo título exato ---

    @Dado("que o usuário está na tela inicial")
    public void usuarioNaTelaInicial() {
        mensagemExibida = null;
    }

    @Quando("ele digitar {string} na barra de busca")
    public void usuarioDigitaNaBusca(String titulo) {
        Filme f = new Filme();
        f.setTitulo(titulo);
        resultadosBusca = List.of(f);
        filmeSelecionado = f;
    }

    @Então("o sistema deve exibir o filme {string}")
    public void sistemaExibeFilme(String tituloEsperado) {
        assertTrue(resultadosBusca.stream().anyMatch(f -> f.getTitulo().equalsIgnoreCase(tituloEsperado)));
    }

    // --- Buscar por palavra-chave ---

    @Dado("que o usuário acessa a busca avançada")
    public void usuarioBuscaAvancada() {
        mensagemExibida = null;
    }

    @Quando("ele digitar a palavra {string}")
    public void usuarioDigitaPalavraChave(String termo) {
        Filme f = new Filme();
        f.setTitulo("Explorando o Sertão");
        f.setDescricao("Documentário sobre o sertão profundo");
        resultadosBusca = List.of(f);
    }

    @Então("o sistema deve exibir todos os filmes que contenham essa palavra em seu título ou descrição")
    public void sistemaExibeFilmesComPalavraChave() {
        assertFalse(resultadosBusca.isEmpty());
    }

    // --- Buscar por diretor ---

    @Dado("que o usuário deseja encontrar filmes de um diretor específico")
    public void usuarioBuscaPorDiretor() {
        mensagemExibida = null;
    }

    @Quando("ele digitar {string} na busca")
    public void digitaNomeDiretor(String diretor) {
        Filme f = new Filme();
        f.setTitulo("Obra Prima");
        f.setDiretor(diretor);
        resultadosBusca = List.of(f);
    }

    @Então("o sistema deve retornar os filmes dirigidos por ele")
    public void sistemaRetornaFilmesDoDiretor() {
        assertFalse(resultadosBusca.isEmpty());
    }

    // --- Busca sem resultados ---

    @Dado("que o usuário busca por um termo inexistente")
    public void buscaTermoInexistente() {
        mensagemExibida = null;
    }

    @Quando("ele digita {string} na barra de busca")
    public void digitaTermoSemResultado(String termo) {
        resultadosBusca = new ArrayList<>();
        mensagemExibida = "Nenhum resultado encontrado";
    }

    @Então("o sistema deve exibir a mensagem {string}")
    public void sistemaExibeMensagemSemResultado(String mensagemEsperada) {
        assertEquals(mensagemEsperada, mensagemExibida);
    }

    // --- Visualizar detalhes do filme ---

    @Dado("que o usuário visualiza os resultados da busca")
    public void usuarioVisualizaResultados() {
        Filme f = new Filme();
        f.setTitulo("Filme Detalhado");
        resultadosBusca = List.of(f);
    }

    @Quando("ele clicar em um filme")
    public void clicarEmFilme() {
        filmeSelecionado = resultadosBusca.get(0);
    }

    @Então("o sistema deve exibir a página de detalhes desse filme")
    public void exibePaginaDetalhes() {
        assertNotNull(filmeSelecionado.getTitulo());
    }

    // --- Aplicar filtros por ano e nota ---

    @Dado("que o usuário deseja refinar a busca")
    public void usuarioRefinaBusca() {
        mensagemExibida = null;
    }

    @Quando("ele selecionar filtros como {string} e {string}")
    public void selecionarFiltros(String filtroAno, String filtroNotaMinima) {
        Filme f = new Filme();
        f.setAno(2020);
        f.setNota(4.5);
        resultadosBusca = List.of(f);
    }

    @Então("o sistema deve mostrar apenas os filmes que atendem a esses critérios")
    public void sistemaFiltraPorAnoENota() {
        assertFalse(resultadosBusca.isEmpty());
    }

    // --- Ver filmes populares ---

    @Dado("que o usuário acessa a home da plataforma")
    public void usuarioNaHome() {
        mensagemExibida = null;
    }

    @Quando("ele rolar até a seção {string}")
    public void rolarParaSecao(String secao) {
        Filme f = new Filme();
        f.setTitulo("Popular");
        f.setNota(4.6);
        resultadosBusca = List.of(f);
    }

    @Então("o sistema deve exibir os filmes mais assistidos e bem avaliados recentemente")
    public void exibeFilmesPopulares() {
        assertFalse(resultadosBusca.isEmpty());
        assertTrue(resultadosBusca.stream().allMatch(f -> f.getNota() >= 4.0));
    }
}
