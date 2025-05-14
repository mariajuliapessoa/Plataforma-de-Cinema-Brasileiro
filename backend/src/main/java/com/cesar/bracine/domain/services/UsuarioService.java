package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Criar um novo usuário
    public Usuario criarUsuario(String nome, String nomeUsuario, String email, String senha) {
        if (usuarioRepository.buscarPorEmail(email).get() != null) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.criar(nome, nomeUsuario, email, senha, passwordEncoder);
        usuarioRepository.salvar(usuario);
        return usuario;
    }

    // Alterar senha do usuário
    public void alterarSenha(UUID id, String novaSenha) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.alterarSenha(novaSenha, passwordEncoder);
        usuarioRepository.salvar(usuario);
    }

    // Adicionar pontos ao usuário
    public void adicionarPontos(UUID id, int pontos) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuario.adicionarPontos(pontos);
        usuarioRepository.salvar(usuario);
    }

    // Buscar Usuario por Email
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.buscarPorEmail(email);
    }
}
