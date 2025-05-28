package com.cesar.bracine.infrastructure.tmdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
public class CachingTMDBClientProxy implements TMDBOperations {
    private final TMDBClient realClient;
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CachingTMDBClientProxy.class);
    private static final long CACHE_EXPIRY_MS = TimeUnit.HOURS.toMillis(1); // 1 hora de cache

    public CachingTMDBClientProxy(TMDBClient realClient) {
        this.realClient = realClient;
    }

    @Override
    public Map<String, Object> buscarFilmesPopularesBrasileiros(int pagina) {
        String cacheKey = "populares_br_" + pagina;
        return getCachedOrFetch(cacheKey, () -> realClient.buscarFilmesPopularesBrasileiros(pagina));
    }

    @Override
    public String buscarDiretorDoFilme(int tmdbId) {
        String cacheKey = "diretor_" + tmdbId;
        return getCachedOrFetch(cacheKey, () -> realClient.buscarDiretorDoFilme(tmdbId));
    }

    @Override
    public Map<Integer, String> buscarMapaGeneros() {
        String cacheKey = "generos";
        return getCachedOrFetch(cacheKey, () -> realClient.buscarMapaGeneros());
    }

    @SuppressWarnings("unchecked")
    private <T> T getCachedOrFetch(String key, Supplier<T> fetcher) {
        CacheEntry entry = cache.get(key);
        long now = System.currentTimeMillis();

        if (entry != null && (now - entry.timestamp < CACHE_EXPIRY_MS)) {
            logger.debug("Cache hit para: {}", key);
            return (T) entry.value;
        }

        logger.debug("Cache miss para: {}", key);
        T value = fetcher.get();
        cache.put(key, new CacheEntry(value, now));
        return value;
    }

    private static class CacheEntry {
        final Object value;
        final long timestamp;

        CacheEntry(Object value, long timestamp) {
            this.value = value;
            this.timestamp = timestamp;
        }
    }

    private interface Supplier<T> {
        T get();
    }
}