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
                notificacao.getId().getValue(),
                notificacao.getDestinatario().getValue(),
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
                        n.getId().getValue(),
                        n.getDestinatario().getValue(),
                        n.getMensagem(),
                        n.getTipo(),
                        n.getDataCriacao(),
                        n.estaLida()
                ))
                .toList();
    }

    @GetMapping("/{notificacaoId}")
    public ResponseEntity<Notificacao> exibirNotificacaoPorId(@PathVariable UUID notificacaoId) {
        return notificacaoService.buscarPorId(notificacaoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> listarPorUsuario(@PathVariable UUID usuarioId) {
        List<Notificacao> notificacaosUsuario = notificacaoService.listarPorDestinatario(usuarioId);
        if (notificacaosUsuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(notificacaosUsuario);
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