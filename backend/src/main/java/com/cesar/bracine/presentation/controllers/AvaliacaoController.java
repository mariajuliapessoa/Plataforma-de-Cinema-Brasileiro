package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.AvaliacaoApplicationService;
import com.cesar.bracine.application.UsuarioApplicationService;
import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.domain.valueobjects.ComentarioId;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import com.cesar.bracine.presentation.dtos.AvaliacaoRequestDTO;
import com.cesar.bracine.presentation.dtos.AvaliacaoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoApplicationService avaliacaoService;
    private final UsuarioApplicationService usuarioService;

    public AvaliacaoController(AvaliacaoApplicationService avaliacaoService, UsuarioApplicationService usuarioService) {
        this.avaliacaoService = avaliacaoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponseDTO> criar(@RequestBody AvaliacaoRequestDTO dto) {
        Avaliacao avaliacao = avaliacaoService.criarAvaliacao(
                dto.texto(),
                dto.nota(),
                new UsuarioId(dto.usuarioId()).getValue(),
                new FilmeId(dto.filmeId()).getValue()
        );

        String nomeUsuario = usuarioService.buscarPorId(dto.usuarioId())
                .map(u -> u.getNome())
                .orElse("Desconhecido");

        return ResponseEntity.ok(new AvaliacaoResponseDTO(
                avaliacao.getId().getValue(),
                avaliacao.getTexto(),
                avaliacao.getNota(),
                avaliacao.getAutor().getValue(),
                nomeUsuario,
                avaliacao.getFilme().getValue(),
                avaliacao.getDataCriacao()
        ));
    }

    @GetMapping
    public List<AvaliacaoResponseDTO> listar() {
        return avaliacaoService.listarAvaliacoes().stream()
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId().getValue(),
                        a.getTexto(),
                        a.getNota(),
                        a.getAutor().getValue(),
                        usuarioService.buscarPorId(a.getAutor().getValue())
                                .map(u -> u.getNome()).orElse("Desconhecido"),
                        a.getFilme().getValue(),
                        a.getDataCriacao()
                ))
                .toList();
    }


    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(@PathVariable UUID id) {
        return avaliacaoService.buscarPorId(new ComentarioId(id).getValue())
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId().getValue(),
                        a.getTexto(),
                        a.getNota(),
                        a.getAutor().getValue(),
                        usuarioService.buscarPorId(a.getAutor().getValue())
                                .map(u -> u.getNome()).orElse("Desconhecido"),
                        a.getFilme().getValue(),
                        a.getDataCriacao()
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> buscarPorFilme(@PathVariable UUID filmeId) {
        List<Avaliacao> avaliacoes = avaliacaoService.listarPorFilme(filmeId);

        if (avaliacoes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<AvaliacaoResponseDTO> dtos = avaliacoes.stream()
                .map(a -> new AvaliacaoResponseDTO(
                        a.getId().getValue(),
                        a.getTexto(),
                        a.getNota(),
                        a.getAutor().getValue(),
                        usuarioService.buscarPorId(a.getAutor().getValue())
                                .map(u -> u.getNome()).orElse("Desconhecido"),
                        a.getFilme().getValue(),
                        a.getDataCriacao()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        avaliacaoService.remover(new ComentarioId(id).getValue());
        return ResponseEntity.noContent().build();
    }
}
