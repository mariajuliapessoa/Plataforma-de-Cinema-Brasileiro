package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.services.DebateService;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DebateApplicationService {

    private final DebateService debateService;
    private final UsuarioRepository usuarioRepository;

    public DebateApplicationService(DebateService debateService, UsuarioRepository usuarioRepository) {
        this.debateService = debateService;
        this.usuarioRepository = usuarioRepository;
    }

    public Debate criarDebate(String titulo, UUID usuarioId) {
        Usuario criador = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Debate debate = new Debate(criador.getId(), titulo, criador);
        return debateService.salvarDebate(debate);
    }

    public List<Debate> listarDebates() {
        return debateService.listarTodosDebates();
    }

    public void removerDebate(UUID id) {
        debateService.removerDebate(id);
    }

    public Optional<Debate> buscarPorId(UUID id) {
        return debateService.buscarDebatePorId(id);
    }
}
