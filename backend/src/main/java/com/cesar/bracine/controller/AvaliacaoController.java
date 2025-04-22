package com.cesar.bracine.controller;

import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public AvaliacaoController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @PostMapping("/filme/{filmeId}")
    public ResponseEntity<?> criarAvaliacao(
            @PathVariable Long filmeId,
            @RequestBody Map<String, Object> avaliacaoRequest,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            String comentario = (String) avaliacaoRequest.get("comentario");
            Integer estrelas = (Integer) avaliacaoRequest.get("estrelas");

            if (comentario == null || comentario.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("erro", "O comentário é obrigatório"));
            }

            if (comentario.length() < 20) {
                return ResponseEntity
                        .badRequest()
                        .body(Map.of("erro", "O comentário deve ter pelo menos 20 caracteres"));
            }

            Avaliacao avaliacao = avaliacaoService.salvarAvaliacao(
                    filmeId, comentario, estrelas, usuarioAtual);

            return ResponseEntity.status(HttpStatus.CREATED).body(avaliacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao processar a avaliação: " + e.getMessage()));
        }
    }

    @GetMapping("/filme/{filmeId}")
    public ResponseEntity<List<Avaliacao>> listarAvaliacoesPorFilme(@PathVariable Long filmeId) {
        List<Avaliacao> avaliacoes = avaliacaoService.buscarAvaliacoesPorFilme(filmeId);
        return ResponseEntity.ok(avaliacoes);
    }

    @DeleteMapping("/{avaliacaoId}")
    public ResponseEntity<?> removerAvaliacao(
            @PathVariable Long avaliacaoId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            avaliacaoService.removerAvaliacao(avaliacaoId, usuarioAtual.getId());
            return ResponseEntity.ok(Map.of("mensagem", "Avaliação removida com sucesso"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao remover a avaliação: " + e.getMessage()));
        }
    }

    @PostMapping("/{avaliacaoId}/curtir")
    public ResponseEntity<Avaliacao> curtirComentario(@PathVariable Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoService.curtirComentario(avaliacaoId);
        return ResponseEntity.ok(avaliacao);
    }

    @GetMapping("/filme/{filmeId}/media")
    public ResponseEntity<Map<String, Double>> obterMediaAvaliacoes(@PathVariable Long filmeId) {
        Double media = avaliacaoService.buscarMediaAvaliacoes(filmeId);
        return ResponseEntity.ok(Map.of("media", media));
    }
} 