package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.ComentarioApplicationService;
import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.presentation.dtos.ComentarioRequestDTO;
import com.cesar.bracine.presentation.dtos.ComentarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/comentarios")
public class ComentarioController {

    private final ComentarioApplicationService comentarioService;
    private final ComentarioApplicationService comentarioApplicationService;

    public ComentarioController(ComentarioApplicationService comentarioService, ComentarioApplicationService comentarioApplicationService) {
        this.comentarioService = comentarioService;
        this.comentarioApplicationService = comentarioApplicationService;
    }

    @PostMapping
    public ResponseEntity<ComentarioResponseDTO> criar(@RequestBody ComentarioRequestDTO dto) {
        Comentario comentario = comentarioService.criarComentario(
                dto.texto(),
                dto.autorId(),
                dto.filmeId(),
                dto.debateId()
        );

        return ResponseEntity.ok(new ComentarioResponseDTO(
                comentario.getId().getValue(),
                comentario.getTexto(),
                comentario.getAutor().getValue(),
                comentario.getFilme() != null ? comentario.getFilme().getValue() : null,
                comentario.getDebate() != null ? comentario.getDebate().getValue() : null,
                comentario.getDataCriacao()
        ));
    }

    @GetMapping
    public List<ComentarioResponseDTO> listar() {
        return comentarioService.listarComentarios().stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getId().getValue(),
                        c.getTexto(),
                        c.getAutor().getValue(),
                        c.getFilme() != null ? c.getFilme().getValue() : null,
                        c.getDebate() != null ? c.getDebate().getValue() : null,
                        c.getDataCriacao()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponseDTO> buscarPorIdComentario(@PathVariable UUID id) {
        return comentarioApplicationService.buscarPorId(id)
                .map(ComentarioResponseDTO::fromDomain)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ComentarioResponseDTO>> buscarPorIdUsuario(@PathVariable UUID id) {
        List<Comentario> comentarios = comentarioApplicationService.buscarPorIdUsuario(id);

        if (comentarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ComentarioResponseDTO> dtos = comentarios.stream()
                .map(ComentarioResponseDTO::fromDomain)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        comentarioService.removerComentario(id);
        return ResponseEntity.noContent().build();
    }
}
