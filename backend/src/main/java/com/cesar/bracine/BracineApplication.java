package com.cesar.bracine;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BracineApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                              .directory(".")
                              .load();

        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );

        // Agora inicia o Spring
        SpringApplication.run(BracineApplication.class, args);
    }
}
