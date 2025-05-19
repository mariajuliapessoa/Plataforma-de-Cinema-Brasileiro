package com.cesar.bracine.config;

import com.cesar.bracine.domain.repositories.*;
import com.cesar.bracine.domain.services.*;
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

    @Bean
    public ComentarioService comentarioService(ComentarioRepository comentarioRepository) {
        return new ComentarioService(comentarioRepository);
    }

    @Bean
    public DebateService debateService(DebateRepository debateRepository) {
        return new DebateService(debateRepository);
    }

    @Bean
    public AvaliacaoService avaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        return new AvaliacaoService(avaliacaoRepository);
    }

    @Bean
    public DesafioService desafioService(DesafioRepository desafioRepository) {
        return new DesafioService(desafioRepository);
    }

    @Bean
    public NotificacaoService notificacaoService(NotificacaoRepository notificacaoRepository) {
        return new NotificacaoService(notificacaoRepository);
    }
}
