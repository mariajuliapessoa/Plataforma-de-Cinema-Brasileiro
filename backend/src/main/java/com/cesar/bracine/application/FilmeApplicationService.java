package com.cesar.bracine.application;

import com.cesar.bracine.application.tmdbtransaction.FilmeTransactionalSaver;
import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.infrastructure.tmdb.TMDBOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;

import java.time.Year;
import java.util.*;

@Service
public class FilmeApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(FilmeApplicationService.class);
    private static final String BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500";
    private static final String PAIS_PADRAO = "Brasil";

    private final FilmeTransactionalSaver transactionalSaver;
    private final TMDBOperations tmdbClient;
    private final FilmeRepository filmeRepository;

    public FilmeApplicationService(FilmeTransactionalSaver transactionalSaver, TMDBOperations tmdbClient, FilmeRepository filmeRepository) {
        this.transactionalSaver = transactionalSaver;
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

    public Page<Filme> listarFilmesPaginados(Pageable pageable) {
        return filmeRepository.listarPaginado(pageable);
    }

    public Optional<Filme> buscarPorId(UUID id) {
        return filmeRepository.buscarPorId(id);
    }

    public Page<Filme> buscarPorTitulo(String titulo, Pageable pageable) {
        return filmeRepository.buscarPorTitulo(titulo, pageable);
    }

    public void removerFilme(UUID id) {
        filmeRepository.remover(id);
    }

    // Importação do TMDB
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<Filme> importarFilmesBrasileiros() {
        List<Filme> filmesSalvos = new ArrayList<>();

        Map<Integer, String> mapaGeneros = tmdbClient.buscarMapaGeneros();

        for (int pagina = 1; pagina <= 10; pagina++) {
            Map<String, Object> resultados = tmdbClient.buscarFilmesPopularesBrasileiros(pagina);

            List<Map<String, Object>> filmes = (List<Map<String, Object>>) resultados.get("results");

            for (Map<String, Object> json : filmes) {
                Optional<Filme> optionalFilme = processarFilme(json, mapaGeneros);

                optionalFilme.ifPresent(filme -> {
                    try {
                        transactionalSaver.salvarFilme(filme); // SALVA UM POR UM COM TRANSAÇÃO CURTA
                        filmesSalvos.add(filme);
                    } catch (Exception e) {
                        logger.warn("Erro ao salvar filme '{}': {}", filme.getTitulo(), e.getMessage());
                    }
                });
            }
        }

        return filmesSalvos;
    }
    
    @SuppressWarnings("unchecked")
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

            // Novos campos
            String sinopse = Optional.ofNullable((String) resultado.get("overview"))
                    .filter(s -> !s.isBlank())
                    .orElse("Sinopse não disponível.");
            double avaliacao = Optional.ofNullable(resultado.get("vote_average"))
                    .map(val -> {
                        if (val instanceof Number)
                            return ((Number) val).doubleValue();
                        try {
                            return Double.parseDouble(val.toString());
                        } catch (NumberFormatException e) {
                            return 0.0;
                        }
                    })
                    .orElse(0.0);

            Filme filme = new Filme(
                    titulo,
                    diretor,
                    sinopse,
                    ano,
                    avaliacao,
                    generos,
                    PAIS_PADRAO,
                    bannerUrl);

            return Optional.of(filme);
        } catch (Exception e) {
            logger.warn("Falha ao processar filme: {}", resultado, e);
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
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
