package com.cesar.bracine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class BracineApplication {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure()
                              .directory(".")
                              .load();

        dotenv.entries().forEach(entry ->
            System.setProperty(entry.getKey(), entry.getValue())
        );
        SpringApplication.run(BracineApplication.class, args);
    }
}