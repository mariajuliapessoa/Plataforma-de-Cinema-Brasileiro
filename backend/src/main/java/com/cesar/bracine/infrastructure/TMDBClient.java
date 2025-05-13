package com.cesar.bracine.infrastructure;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
public class TMDBClient {

    @Value("${TMDB.API.KEY}")
    private String apiKey;



}
