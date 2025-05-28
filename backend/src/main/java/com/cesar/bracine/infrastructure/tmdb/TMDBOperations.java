package com.cesar.bracine.infrastructure.tmdb;

import java.util.Map;

public interface TMDBOperations {
    Map<String, Object> buscarFilmesPopularesBrasileiros(int pagina);
    String buscarDiretorDoFilme(int tmdbId);
    Map<Integer, String> buscarMapaGeneros();
}