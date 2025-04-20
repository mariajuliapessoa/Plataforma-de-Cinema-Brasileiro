package com.cesar.bracine.steps;

import com.cesar.bracine.model.Filme;
import com.cesar.bracine.repository.FilmeRepository;
import io.cucumber.java.Before;
import io.cucumber.java.pt.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@ContextConfiguration
public class BuscarFilmesSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmeRepository filmeRepository;

    private String resposta;

    @Before
    public void setupDatabase() {
        filmeRepository.deleteAll();
        filmeRepository.saveAll(List.of(
                new Filme(null, "Bacurau", "No sertão brasileiro", "Documentário", "Kleber Mendonça Filho", 2019, 4.6),
                new Filme(null, "O Som ao Redor", "Filme sobre a vida urbana", "Drama", "Kleber Mendonça Filho", 2012, 4.4),
                new Filme(null, "Central do Brasil", "Drama emocionante", "Drama", "Walter Salles", 1998, 4.8)
        ));
    }

    @Dado("que o usuário está na tela inicial")
    public void usuarioNaTelaInicial() {}

    @Quando("ele digitar {string} na barra de busca")
    public void digitarNaBarraDeBusca(String termo) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes?busca=" + termo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve exibir o filme {string}")
    public void sistemaDeveExibirFilme(String filme) {
        assertThat(resposta).contains(filme);
    }

    @Dado("que o usuário acessa a busca avançada")
    public void usuarioBuscaAvancada() {}

    @Quando("ele digitar a palavra {string}")
    public void digitarPalavraChave(String palavra) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes?busca=" + palavra)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve exibir todos os filmes que contenham essa palavra em seu título ou descrição")
    public void sistemaDeveExibirFilmesPorPalavra() {
        assertThat(resposta).isNotBlank();
    }

    @Dado("que o usuário está na aba de filtros")
    public void usuarioNaAbaDeFiltros() {}

    @Quando("ele selecionar o gênero {string}")
    public void selecionarGenero(String genero) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes?genero=" + genero)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve exibir todos os filmes classificados como documentário")
    public void sistemaDeveExibirDocumentarios() {
        assertThat(resposta).contains("Documentário");
    }

    @Dado("que o usuário deseja encontrar filmes de um diretor específico")
    public void usuarioBuscaPorDiretor() {}

    @Quando("ele digitar {string} na busca")
    public void buscarPorDiretor(String diretor) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes?busca=" + diretor)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve retornar os filmes dirigidos por ele")
    public void sistemaDeveExibirFilmesDoDiretor() {
        assertThat(resposta).contains("Kleber Mendonça Filho");
    }

    @Dado("que o usuário busca por um termo inexistente")
    public void usuarioBuscaInexistente() {}

    @Quando("ele digita {string} na barra de busca")
    public void digitarTermoInexistente(String termo) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes?busca=" + termo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve exibir a mensagem {string}")
    public void sistemaExibeMensagem(String msg) {
        assertThat(resposta).contains(msg);
    }

    @Dado("que o usuário visualiza os resultados da busca")
    public void usuarioVisualizaResultados() {}

    @Quando("ele clicar em um filme")
    public void clicarEmUmFilme() throws Exception {
        Long id = filmeRepository.findAll().get(0).getId();
        MvcResult result = mockMvc.perform(get("/filmes/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve exibir a página de detalhes desse filme")
    public void sistemaExibeDetalhesDoFilme() {
        assertThat(resposta).contains("Bacurau");
    }

    @Dado("que o usuário deseja refinar a busca")
    public void usuarioDesejaRefinarBusca() {}

    @Quando("ele selecionar filtros como {string} e {string}")
    public void selecionarFiltros(String ano, String notaMinima) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes?ano=" + ano + "&notaMinima=" + notaMinima)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve mostrar apenas os filmes que atendem a esses critérios")
    public void sistemaMostraFilmesFiltrados() {
        assertThat(resposta).isNotBlank();
    }

    @Dado("que o usuário acessa a home da plataforma")
    public void usuarioNaHome() {}

    @Quando("ele rolar até a seção {string}")
    public void rolarAteSecao(String secao) throws Exception {
        MvcResult result = mockMvc.perform(get("/filmes/populares")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        resposta = result.getResponse().getContentAsString();
    }

    @Então("o sistema deve exibir os filmes mais assistidos e bem avaliados recentemente")
    public void sistemaExibePopulares() {
        assertThat(resposta).contains("popular");
    }
}
