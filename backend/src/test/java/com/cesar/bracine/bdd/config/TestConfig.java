package com.cesar.bracine.bdd.config;

import com.cesar.bracine.domain.entities.*;
import com.cesar.bracine.domain.repositories.*;
import com.cesar.bracine.domain.services.*;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfig {

    private final Map<UUID, Usuario> bancoUsuarios = new HashMap<>();

    @Bean
    public UsuarioRepository usuarioRepository() {
        UsuarioRepository mock = mock(UsuarioRepository.class);

        Mockito.when(mock.buscarPorId(Mockito.any())).thenAnswer(invocation -> {
            UUID id = invocation.getArgument(0);
            return Optional.ofNullable(bancoUsuarios.get(id));
        });

        Mockito.when(mock.buscarPorEmail(Mockito.any())).thenAnswer(invocation -> {
            String email = invocation.getArgument(0);
            return bancoUsuarios.values().stream()
                    .filter(u -> u.getEmail().equals(email))
                    .findFirst();
        });

        Mockito.doAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            bancoUsuarios.put(usuario.getId().getValue(), usuario);
            return null;
        }).when(mock).salvar(Mockito.any());

        return mock;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UsuarioService usuarioService(UsuarioRepository usuarioRepository, PasswordEncoder encoder) {
        return new UsuarioService(usuarioRepository, encoder);
    }

    @Bean
    public FilmeRepository filmeRepository() {
        FilmeRepository mock = mock(FilmeRepository.class);

        List<Filme> bancoFilmes = new java.util.ArrayList<>();

        Mockito.doAnswer(invocation -> {
            Filme filme = invocation.getArgument(0);
            bancoFilmes.add(filme);
            return null;
        }).when(mock).salvar(Mockito.any());

        Mockito.when(mock.listarTodos()).thenAnswer(invocation -> new java.util.ArrayList<>(bancoFilmes));

        return mock;
    }

    @Bean
    public FilmeService filmeService(FilmeRepository filmeRepository) {
        return new FilmeService(filmeRepository);
    }

    @Bean
    public DesafioRepository desafioRepository() {
        DesafioRepository mock = mock(DesafioRepository.class);

        List<Desafio> bancoDesafios = new java.util.ArrayList<>();

        Mockito.doAnswer(invocation -> {
            Desafio desafio = invocation.getArgument(0);
            bancoDesafios.add(desafio);
            return null;
        }).when(mock).salvar(Mockito.any(Desafio.class), Mockito.any());

        Mockito.when(mock.listarTodos()).thenReturn(bancoDesafios);

        return mock;
    }

    @Bean
    public DesafioService desafioService(DesafioRepository desafioRepository, UsuarioRepository usuarioRepository) {
        return new DesafioService(desafioRepository, usuarioRepository);
    }

    @Bean
    public NotificacaoRepository notificacaoRepository() {
        NotificacaoRepository mock = mock(NotificacaoRepository.class);

        List<Notificacao> bancoNotificacoes = new java.util.ArrayList<>();

        Mockito.doAnswer(invocation -> {
            Notificacao notificacao = invocation.getArgument(0);
            Usuario usuario = invocation.getArgument(1);
            bancoNotificacoes.add(notificacao);
            return null;
        }).when(mock).salvar(Mockito.any(Notificacao.class), Mockito.any());

        Mockito.when(mock.listarTodos()).thenAnswer(invocation -> new java.util.ArrayList<>(bancoNotificacoes));

        return mock;
    }

    @Bean
    public NotificacaoService notificacaoService(NotificacaoRepository notificacaoRepository,
                                                 UsuarioRepository usuarioRepository) {
        return new NotificacaoService(notificacaoRepository, usuarioRepository);
    }

    @Bean
    public ComentarioRepository comentarioRepository() {
        ComentarioRepository mock = mock(ComentarioRepository.class);

        List<Comentario> bancoComentarios = new java.util.ArrayList<>();

        Mockito.doAnswer(invocation -> {
            Comentario comentario = invocation.getArgument(0);
            bancoComentarios.add(comentario);
            return null;
        }).when(mock).salvar(Mockito.any());

        Mockito.when(mock.listarTodos()).thenAnswer(invocation -> new java.util.ArrayList<>(bancoComentarios));

        return mock;
    }

    @Bean
    public ComentarioService comentarioService(ComentarioRepository comentarioRepository) {
        return new ComentarioService(comentarioRepository);
    }

    @Bean
    public DebateRepository debateRepository() {
        DebateRepository mock = mock(DebateRepository.class);

        List<Debate> bancoDebates = new java.util.ArrayList<>();

        Mockito.doAnswer(invocation -> {
            Debate debate = invocation.getArgument(0);
            Usuario usuario = invocation.getArgument(1);
            bancoDebates.add(debate);
            return null;
        }).when(mock).salvar(Mockito.any(Debate.class), Mockito.any());

        Mockito.when(mock.listarTodos()).thenAnswer(invocation -> new java.util.ArrayList<>(bancoDebates));

        return mock;
    }

    @Bean
    public DebateService debateService(DebateRepository debateRepository) {
        return new DebateService(debateRepository);
    }

    @Bean
    public AvaliacaoRepository avaliacaoRepository() {
        AvaliacaoRepository mock = mock(AvaliacaoRepository.class);

        List<Avaliacao> bancoAvaliacoes = new java.util.ArrayList<>();

        Mockito.doAnswer(invocation -> {
            Avaliacao avaliacao = invocation.getArgument(0);
            bancoAvaliacoes.add(avaliacao);
            return null;
        }).when(mock).salvar(Mockito.any(Avaliacao.class));

        Mockito.when(mock.listarTodos()).thenAnswer(invocation -> new java.util.ArrayList<>(bancoAvaliacoes));

        return mock;
    }

    @Bean
    public AvaliacaoService avaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        return new AvaliacaoService(avaliacaoRepository);
    }
}
