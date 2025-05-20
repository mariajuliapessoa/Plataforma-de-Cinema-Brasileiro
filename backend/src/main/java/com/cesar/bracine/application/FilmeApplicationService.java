package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.infrastructure.tmdb.TMDBClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.*;

@Service
public class FilmeApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(FilmeApplicationService.class);
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String PAIS_PADRAO = "Brasil";

    private final TMDBClient tmdbClient;
    private final FilmeRepository filmeRepository;

    public FilmeApplicationService(TMDBClient tmdbClient, FilmeRepository filmeRepository) {
        this.tmdbClient = tmdbClient;
        this.filmeRepository = filmeRepository;
    }

    public Filme salvarFilme(Filme filme) {
        filmeRepository.salvar(filme);
        return filme;
    }

    public List<Filme> listarTodosFilmes() {
        return filmeRepository.listarTodos();
    }

    public Optional<Filme> buscarPorId(UUID id) {
        return filmeRepository.buscarPorId(id);
    }

    public void removerFilme(UUID id) {
        filmeRepository.remover(id);
    }

    // Importação do TMDB
    public List<Filme> importarFilmesBrasileiros() {
        try {
            Map<Integer, String> mapaGeneros = tmdbClient.buscarMapaGeneros();
            if (mapaGeneros.isEmpty()) {
                logger.warn("Nenhum gênero encontrado na API do TMDB");
                return Collections.emptyList();
            }

            List<Filme> filmesImportados = new ArrayList<>();
            int pagina = 1;
            int totalPaginas = 1;

            do {
                Map<String, Object> resposta = tmdbClient.buscarFilmesPopularesBrasileiros(pagina);
                totalPaginas = (int) resposta.getOrDefault("total_pages", 1);
                filmesImportados.addAll(processarPaginaDeFilmes(resposta, mapaGeneros));
                pagina++;
            } while (pagina <= totalPaginas);

            return filmesImportados;
        } catch (Exception e) {
            logger.error("Falha ao importar filmes brasileiros", e);
            return Collections.emptyList();
        }
    }

    private List<Filme> processarPaginaDeFilmes(Map<String, Object> resposta, Map<Integer, String> mapaGeneros) {
        List<Map<String, Object>> resultados = Optional.ofNullable((List<Map<String, Object>>) resposta.get("results"))
                .orElse(Collections.emptyList());

        List<Filme> filmesDaPagina = new ArrayList<>();
        for (Map<String, Object> resultado : resultados) {
            processarFilme(resultado, mapaGeneros).ifPresent(filme -> {
                filmeRepository.salvar(filme);
                filmesDaPagina.add(filme);
            });
        }

        return filmesDaPagina;
    }

    private Optional<Filme> processarFilme(Map<String, Object> resultado, Map<Integer, String> mapaGeneros) {
        try {
            String titulo = (String) resultado.get("title");
            if (titulo == null || titulo.isBlank() || filmeRepository.existePorTitulo(titulo)) {
                return Optional.empty();
            }

            int ano = extrairAno((String) resultado.get("release_date"));
            String bannerUrl = construirUrlBanner((String) resultado.get("poster_path"));

            List<String> generos = extrairGeneros(resultado, mapaGeneros);
            if (generos.isEmpty()) {
                return Optional.empty();
            }

            int tmdbId = (int) resultado.get("id");
            String diretor = tmdbClient.buscarDiretorDoFilme(tmdbId);

            Filme filme = new Filme(
                    titulo,
                    diretor,
                    ano,
                    generos,
                    PAIS_PADRAO,
                    bannerUrl
            );

            return Optional.of(filme);
        } catch (Exception e) {
            logger.warn("Falha ao processar filme: {}", resultado, e);
            return Optional.empty();
        }
    }

    private List<String> extrairGeneros(Map<String, Object> resultado, Map<Integer, String> mapaGeneros) {
        try {
            List<Integer> idsGeneros = (List<Integer>) resultado.get("genre_ids");
            return Optional.ofNullable(idsGeneros)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(mapaGeneros::get)
                    .filter(Objects::nonNull)
                    .toList();
        } catch (Exception e) {
            logger.warn("Falha ao extrair gêneros do filme", e);
            return Collections.emptyList();
        }
    }

    private int extrairAno(String data) {
        try {
            return Optional.ofNullable(data)
                    .filter(d -> d.length() >= 4)
                    .map(d -> Integer.parseInt(d.substring(0, 4)))
                    .orElse(Year.now().getValue());
        } catch (NumberFormatException e) {
            logger.warn("Data inválida: {}", data);
            return Year.now().getValue();
        }
    }

    private String construirUrlBanner(String path) {
        return Optional.ofNullable(path)
                .filter(p -> !p.isBlank())
                .map(p -> BASE_IMAGE_URL + p)
                .orElse("");
    }
}
