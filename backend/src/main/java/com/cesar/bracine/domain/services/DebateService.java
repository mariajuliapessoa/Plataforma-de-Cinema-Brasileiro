package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.repositories.DebateRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DebateService {

    private final DebateRepository debateRepository;

    public DebateService(DebateRepository debateRepository) {
        this.debateRepository = debateRepository;
    }

    // Criar ou Editar um debate
    public Debate salvarDebate(Debate debate) {
        debateRepository.salvar(debate);
        return debate;
    }

    // Buscar debate por ID
    public Optional<Debate> buscarDebatePorId(UUID id) {
        return debateRepository.buscarPorId(id);
    }

    // Listar todos os debates
    public List<Debate> listarTodosDebates() {
        return debateRepository.listarTodos();
    }

    // Remover um debate
    public void removerDebate(UUID id) {
        debateRepository.remover(id);
    }
}