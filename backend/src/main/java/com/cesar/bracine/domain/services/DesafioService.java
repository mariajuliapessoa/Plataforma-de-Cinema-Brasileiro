package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.repositories.DesafioRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DesafioService {

    private final DesafioRepository desafioRepository;

    public DesafioService(DesafioRepository desafioRepository) {
        this.desafioRepository = desafioRepository;
    }

    // Criar ou Editar um desafio
    public Desafio salvarDesafio(Desafio desafio) {
        desafioRepository.salvar(desafio);
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