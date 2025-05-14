package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.services.UsuarioService;
import com.cesar.bracine.infrastructure.mapper.UsuarioMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioApplicationService implements UserDetailsService {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioApplicationService(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario criarUsuario(String nome, String nomeUsuario, String email, String senha) {
        return usuarioService.criarUsuario(nome, nomeUsuario, email, senha);
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioService.buscarUsuarioPorEmail(email);
    }

    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioService.buscarPorId(id);
    }

    public void alterarSenha(UUID id, String novaSenha) {
        usuarioService.alterarSenha(id, novaSenha);
    }

    public void editarDados(UUID id, String nome, String nomeUsuario, String email) {
        usuarioService.editarUsuario(id, nome, nomeUsuario, email);
    }

    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    public void deletarUsuario(UUID id) {
        usuarioService.deletarUsuario(id);
    }

    public void adicionarPontos(UUID id, int pontos) {
        usuarioService.adicionarPontos(id, pontos);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.buscarPorEmail(email)
                .map(UsuarioMapper::toEntity)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }
}