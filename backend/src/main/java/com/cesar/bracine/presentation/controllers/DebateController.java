package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.DebateApplicationService;
import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.presentation.dtos.DebateRequestDTO;
import com.cesar.bracine.presentation.dtos.DebateResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/debates")
public class DebateController {

    private final DebateApplicationService debateService;
    private final DebateApplicationService debateApplicationService;

    public DebateController(DebateApplicationService debateService, DebateApplicationService debateApplicationService) {
        this.debateService = debateService;
        this.debateApplicationService = debateApplicationService;
    }

    @PostMapping
    public ResponseEntity<DebateResponseDTO> criar(@RequestBody DebateRequestDTO dto) {
        Debate debate = debateService.criarDebate(dto.titulo(), dto.usuarioId());
        return ResponseEntity.ok(new DebateResponseDTO(
                debate.getId().getValue(),
                debate.getTitulo(),
                debate.getCriador().getValue()
        ));
    }

    @GetMapping
    public List<DebateResponseDTO> listar() {
        return debateService.listarDebates().stream()
                .map(debate -> new DebateResponseDTO(
                        debate.getId().getValue(),
                        debate.getTitulo(),
                        debate.getCriador().getValue()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Debate> buscarDebatePorId(@PathVariable UUID id) {
        return debateApplicationService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        debateService.removerDebate(id);
        return ResponseEntity.noContent().build();
    }
}
