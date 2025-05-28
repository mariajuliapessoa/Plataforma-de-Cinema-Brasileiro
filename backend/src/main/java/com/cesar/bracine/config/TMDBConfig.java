package com.cesar.bracine.config;

import com.cesar.bracine.infrastructure.tmdb.CachingTMDBClientProxy;
import com.cesar.bracine.infrastructure.tmdb.TMDBClient;
import com.cesar.bracine.infrastructure.tmdb.TMDBOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TMDBConfig {
    @Bean
    @Primary
    public TMDBOperations tmdbOperations(TMDBClient realClient) {
        return new CachingTMDBClientProxy(realClient);
    }
}