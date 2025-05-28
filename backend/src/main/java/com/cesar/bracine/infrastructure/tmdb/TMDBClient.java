package com.cesar.bracine.infrastructure.tmdb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

@Repository
public class TMDBClient implements TMDBOperations {

    @Value("${tmdb.api.key}")  // ⚠️ troquei para minúsculo e padronizado
    private String apiKey;

    private static final Logger logger = LoggerFactory.getLogger(TMDBClient.class);
    private final RestTemplate restTemplate;

    public TMDBClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> buscarFilmesPopularesBrasileiros(int pagina) {
        String url = UriComponentsBuilder
                .newInstance()
                .scheme("https")
                .host("api.themoviedb.org")
                .path("/3/discover/movie")
                .queryParam("api_key", apiKey)
                .queryParam("language", "pt-BR")
                .queryParam("sort_by", "popularity.desc")
                .queryParam("with_original_language", "pt")
                .queryParam("region", "BR")
                .queryParam("page", pagina)
                .build()
                .toUriString();

        try {
            return restTemplate.getForObject(url, Map.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao buscar filmes populares brasileiros do TMDB", e);
        }
    }

    @SuppressWarnings("unchecked")
    public String buscarDiretorDoFilme(int tmdbId) {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.themoviedb.org/3/movie/" + tmdbId + "/credits")
                .queryParam("api_key", apiKey)
                .toUriString();

        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            List<Map<String, Object>> crew = (List<Map<String, Object>>) response.get("crew");

            return crew.stream()
                    .filter(pessoa -> "Director".equalsIgnoreCase((String) pessoa.get("job")))
                    .map(pessoa -> (String) pessoa.get("name"))
                    .findFirst()
                    .orElse("Desconhecido");

        } catch (Exception e) {
            logger.warn("Erro ao buscar diretor do filme ID {}: {}", tmdbId, e.getMessage());
            return "Desconhecido";
        }
    }

    @SuppressWarnings("unchecked")
    public Map<Integer, String> buscarMapaGeneros() {
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.themoviedb.org/3/genre/movie/list")
                .queryParam("api_key", apiKey)
                .queryParam("language", "pt-BR")
                .toUriString();

        try {
            Map<String, Object> resposta = restTemplate.getForObject(url, Map.class);
            if (resposta == null || !resposta.containsKey("genres")) {
                throw new RuntimeException("Resposta inválida ao buscar gêneros do TMDB");
            }

            List<Map<String, Object>> generos = (List<Map<String, Object>>) resposta.get("genres");
            Map<Integer, String> mapa = new HashMap<>();

            for (Map<String, Object> genero : generos) {
                Object idObj = genero.get("id");
                Object nomeObj = genero.get("name");
                if (idObj instanceof Number && nomeObj instanceof String) {
                    mapa.put(((Number) idObj).intValue(), (String) nomeObj);
                }
            }
            return mapa;

        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao buscar gêneros do TMDB", e);
        }
    }
}
