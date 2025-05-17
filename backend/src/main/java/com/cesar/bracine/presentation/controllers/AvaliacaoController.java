package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.AvaliacaoApplicationService;
import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.presentation.dtos.AvaliacaoRequestDTO;
import com.cesar.bracine.presentation.dtos.AvaliacaoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoApplicationService avaliacaoService;

    public AvaliacaoController(AvaliacaoApplicationService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criar(@RequestBody AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = avaliacaoService.criarAvaliacao(
                dto.texto(),
                dto.nota(),
                dto.usuarioId(),
                dto.filmeId()
        );

        return ResponseEntity.ok(new AvaliacaoResponseDTO(
                avaliacao.getId(),
                avaliacao.getTexto(),
                avaliacao.getNota(),
                avaliacao.getAutor().getNome(),
                avaliacao.getFilme().getTitulo(),
                avaliacao.getDataCriacao()
        ));
    }

    @GetMapping
    public List<AvaliacaoResponseDTO> listar() {
        return avaliacaoService.listarAvaliacoes().stream()
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId(),
                        a.getTexto(),
                        a.getNota(),
                        a.getAutor().getNome(),
                        a.getFilme().getTitulo(),
                        a.getDataCriacao()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return avaliacaoService.buscarPorId(id)
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId(),
                        a.getTexto(),
                        a.getNota(),
                        a.getAutor().getNome(),
                        a.getFilme().getTitulo(),
                        a.getDataCriacao()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        avaliacaoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}