package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.DesafioApplicationService;
import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.presentation.dtos.CriarDesafioRequestDTO;
import com.cesar.bracine.presentation.dtos.DesafioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/desafios")
public class DesafioController {

    private final DesafioApplicationService desafioService;

    public DesafioController(DesafioApplicationService desafioService) {
        this.desafioService = desafioService;
    }

    @PostMapping
    public ResponseEntity<DesafioResponseDTO> criar(@RequestBody CriarDesafioRequestDTO request) {
        Desafio desafio = desafioService.criarDesafio(
                request.titulo(),
                request.descricao(),
                request.pontos(),
                request.destinatarioId(),
                request.prazo()
        );

        DesafioResponseDTO response = new DesafioResponseDTO(
                desafio.getId(),
                desafio.getTitulo(),
                desafio.getDescricao(),
                desafio.getPontos(),
                desafio.getDestinatario().getId(),
                desafio.isConcluido(),
                desafio.getPrazo(),
                desafio.getDataCriacao()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<DesafioResponseDTO> listarTodos() {
        return desafioService.listarDesafios()
                .stream()
                .map(desafio -> new DesafioResponseDTO(
                        desafio.getId(),
                        desafio.getTitulo(),
                        desafio.getDescricao(),
                        desafio.getPontos(),
                        desafio.getDestinatario().getId(),
                        desafio.isConcluido(),
                        desafio.getPrazo(),
                        desafio.getDataCriacao()
                ))
                .toList();
    }

    @PostMapping("/{id}/concluir")
    public ResponseEntity<Void> concluir(@PathVariable UUID id) {
        desafioService.concluirDesafio(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        desafioService.removerDesafio(id);
        return ResponseEntity.noContent().build();
    }
}
