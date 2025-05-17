package com.cesar.bracine.config;

import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.services.FilmeService;
import com.cesar.bracine.domain.services.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DomainServiceConfig {

    @Bean
    public UsuarioService usuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return new UsuarioService(usuarioRepository, passwordEncoder);
    }

    @Bean
    public FilmeService filmeService(FilmeRepository filmeRepository) {
        return new FilmeService(filmeRepository);
    }
}
