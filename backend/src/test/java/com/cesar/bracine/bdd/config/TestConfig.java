package com.cesar.bracine.bdd.config;

import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.services.UsuarioService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import com.cesar.bracine.domain.entities.Usuario;

@Configuration
public class TestConfig {

    private final Map<UUID, Usuario> bancoUsuarios = new HashMap<>();

    @Bean
    public UsuarioRepository usuarioRepository() {
        UsuarioRepository mock = Mockito.mock(UsuarioRepository.class);

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
}
