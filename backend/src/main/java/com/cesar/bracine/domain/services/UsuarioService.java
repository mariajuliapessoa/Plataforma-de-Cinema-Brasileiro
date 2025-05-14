package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
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
        if (usuarioRepository.buscarPorEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        Usuario usuario = Usuario.criar(nome, nomeUsuario, email, senha, passwordEncoder);
        usuarioRepository.salvar(usuario);
        return usuario;
    }

    public void editarUsuario(UUID id, String novoNome, String novoNomeUsuario, String novoEmail) {
        Usuario usuario = usuarioRepository.buscarPorId(id).orElseThrow(() -> new IllegalArgumentException(("Usuário não encontrado")));

        usuario.editarDados(novoNome, novoNomeUsuario, novoEmail);
        usuarioRepository.salvar(usuario);
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

    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioRepository.buscarPorId(id);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.listarTodos();
    }

    public void deletarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        usuarioRepository.deletar(id);
    }
}
