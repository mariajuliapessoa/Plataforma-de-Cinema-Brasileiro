package com.cesar.bracine.config;

import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringUsuarioJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.impl.UsuarioRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryConfig {
    @Bean
    public UsuarioRepository usuarioRepository(SpringUsuarioJpaRepository jpaRepository) {
        return new UsuarioRepositoryImpl(jpaRepository);
    }
}
