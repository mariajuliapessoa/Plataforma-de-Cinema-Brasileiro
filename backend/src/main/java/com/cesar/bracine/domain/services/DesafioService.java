package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.DesafioRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final UsuarioRepository usuarioRepository;

    public DesafioService(DesafioRepository desafioRepository, UsuarioRepository usuarioRepository) {
        this.desafioRepository = desafioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Criar ou Editar um desafio
    public Desafio salvarDesafio(Desafio desafio) {
        Usuario usuario = usuarioRepository.buscarPorId(desafio.getDestinatario().getValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        desafioRepository.salvar(desafio, usuario);
        return desafio;
    }

    // Buscar desafio por ID
    public Optional<Desafio> buscarDesafioPorId(UUID id) {
        return desafioRepository.buscarPorId(id);
    }

    // Listar todos os desafios
    public List<Desafio> listarTodosDesafios() {
        return desafioRepository.listarTodos();
    }

    // Remover um desafio
    public void removerDesafio(UUID id) {
        desafioRepository.remover(id);
    }
}