package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.ComentarioApplicationService;
import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.presentation.dtos.ComentarioRequestDTO;
import com.cesar.bracine.presentation.dtos.ComentarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
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
                comentario.getId(),
                comentario.getTexto(),
                comentario.getAutor().getNome(),
                comentario.getFilme() != null ? comentario.getFilme().getTitulo() : null,
                comentario.getDebate() != null ? comentario.getDebate().getTitulo() : null,
                comentario.getDataCriacao()
        ));
    }

    @GetMapping
    public List<ComentarioResponseDTO> listar() {
        return comentarioService.listarComentarios().stream()
                .map(c -> new ComentarioResponseDTO(
                        c.getId(),
                        c.getTexto(),
                        c.getAutor().getNome(),
                        c.getFilme() != null ? c.getFilme().getTitulo() : null,
                        c.getDebate() != null ? c.getDebate().getTitulo() : null,
                        c.getDataCriacao()
                ))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comentario> buscarPorIdComentario(@PathVariable UUID id) {
        return comentarioApplicationService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Comentario>> buscarPorIdUsuario(@PathVariable UUID id) {
        List<Comentario> comentarios = comentarioApplicationService.buscarPorIdUsuario(id);
        if (comentarios.isEmpty()) {
            return ResponseEntity.noContent().build(); // ou notFound()
        }
        return ResponseEntity.ok(comentarios);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        comentarioService.removerComentario(id);
        return ResponseEntity.noContent().build();
    }
}
