package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.NotificacaoApplicationService;
import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.presentation.dtos.NotificacaoRequestDTO;
import com.cesar.bracine.presentation.dtos.NotificacaoResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    private final NotificacaoApplicationService notificacaoService;

    public NotificacaoController(NotificacaoApplicationService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    @PostMapping
    public ResponseEntity<NotificacaoResponseDTO> criar(@RequestBody NotificacaoRequestDTO request) {
        Notificacao notificacao = notificacaoService.criarNotificacao(
                request.destinatarioId(),
                request.mensagem(),
                request.tipo()
        );

        NotificacaoResponseDTO response = new NotificacaoResponseDTO(
                notificacao.getId(),
                notificacao.getDestinatario().getId(),
                notificacao.getMensagem(),
                notificacao.getTipo(),
                notificacao.getDataCriacao(),
                notificacao.estaLida()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public List<NotificacaoResponseDTO> listarTodas() {
        return notificacaoService.listarTodas().stream()
                .map(n -> new NotificacaoResponseDTO(
                        n.getId(),
                        n.getDestinatario().getId(),
                        n.getMensagem(),
                        n.getTipo(),
                        n.getDataCriacao(),
                        n.estaLida()
                ))
                .toList();
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<NotificacaoResponseDTO> listarPorUsuario(@PathVariable UUID usuarioId) {
        return notificacaoService.listarPorDestinatario(usuarioId).stream()
                .map(n -> new NotificacaoResponseDTO(
                        n.getId(),
                        n.getDestinatario().getId(),
                        n.getMensagem(),
                        n.getTipo(),
                        n.getDataCriacao(),
                        n.estaLida()
                ))
                .toList();
    }

    @PostMapping("/{id}/ler")
    public ResponseEntity<Void> marcarComoLida(@PathVariable UUID id) {
        notificacaoService.marcarComoLida(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable UUID id) {
        notificacaoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}