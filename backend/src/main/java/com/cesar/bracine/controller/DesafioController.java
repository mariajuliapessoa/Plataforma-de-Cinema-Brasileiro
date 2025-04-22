package com.cesar.bracine.controller;

import com.cesar.bracine.model.Desafio;
import com.cesar.bracine.model.DesafioParticipante;
import com.cesar.bracine.model.Notificacao;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.model.user.UserRole;
import com.cesar.bracine.service.DesafioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/desafios")
public class DesafioController {

    private final DesafioService desafioService;

    @Autowired
    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @GetMapping
    public ResponseEntity<List<Desafio>> listarDesafiosAtivos() {
        List<Desafio> desafios = desafioService.buscarDesafiosAtivos();
        return ResponseEntity.ok(desafios);
    }

    @GetMapping("/{desafioId}")
    public ResponseEntity<Desafio> buscarDesafioPorId(@PathVariable Long desafioId) {
        Desafio desafio = desafioService.buscarPorId(desafioId);
        return ResponseEntity.ok(desafio);
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Desafio> buscarDesafioPorTitulo(@PathVariable String titulo) {
        Desafio desafio = desafioService.buscarPorTitulo(titulo);
        return ResponseEntity.ok(desafio);
    }

    @PostMapping
    public ResponseEntity<?> criarDesafio(
            @RequestBody Map<String, Object> desafioRequest,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        if (!usuarioAtual.getRole().equals(UserRole.ADMIN)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas administradores podem criar desafios"));
        }

        try {
            String titulo = (String) desafioRequest.get("titulo");
            String descricao = (String) desafioRequest.get("descricao");
            LocalDate dataFim = desafioRequest.containsKey("dataFim") ? 
                    LocalDate.parse((String) desafioRequest.get("dataFim")) : null;
            @SuppressWarnings("unchecked")
            List<Long> filmesIds = (List<Long>) desafioRequest.get("filmes");
            int pontosConquista = desafioRequest.containsKey("pontosConquista") ? 
                    (Integer) desafioRequest.get("pontosConquista") : 100;
            String premioDesafio = (String) desafioRequest.get("premioDesafio");

            Desafio novoDesafio = desafioService.criarDesafio(
                    titulo, descricao, usuarioAtual, dataFim, filmesIds, pontosConquista, premioDesafio);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoDesafio);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{desafioId}/participar")
    public ResponseEntity<?> participarDesafio(
            @PathVariable Long desafioId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            DesafioParticipante participante = desafioService.participarDesafio(desafioId, usuarioAtual);
            return ResponseEntity.status(HttpStatus.CREATED).body(participante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{desafioId}/desistir")
    public ResponseEntity<?> desistirDesafio(
            @PathVariable Long desafioId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            desafioService.desistirDesafio(desafioId, usuarioAtual);
            return ResponseEntity.ok(Map.of("mensagem", "Você saiu do desafio com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{desafioId}/filmes/{filmeId}/assistido")
    public ResponseEntity<?> marcarFilmeComoAssistido(
            @PathVariable Long desafioId,
            @PathVariable Long filmeId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            DesafioParticipante participante = desafioService.marcarFilmeComoVisto(desafioId, filmeId, usuarioAtual);
            return ResponseEntity.ok(participante);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/meus-desafios")
    public ResponseEntity<List<DesafioParticipante>> listarMeusDesafios(
            @AuthenticationPrincipal User usuarioAtual
    ) {
        List<DesafioParticipante> desafios = desafioService.buscarDesafiosDoUsuario(usuarioAtual.getId());
        return ResponseEntity.ok(desafios);
    }

    @GetMapping("/meus-desafios/ativos")
    public ResponseEntity<List<DesafioParticipante>> listarMeusDesafiosAtivos(
            @AuthenticationPrincipal User usuarioAtual
    ) {
        List<DesafioParticipante> desafios = desafioService.buscarDesafiosAtivosDoUsuario(usuarioAtual.getId());
        return ResponseEntity.ok(desafios);
    }

    @GetMapping("/{desafioId}/ranking")
    public ResponseEntity<List<Map<String, Object>>> buscarRankingDesafio(
            @PathVariable Long desafioId
    ) {
        List<Map<String, Object>> ranking = desafioService.buscarRankingDesafio(desafioId);
        return ResponseEntity.ok(ranking);
    }

    @GetMapping("/notificacoes")
    public ResponseEntity<List<Notificacao>> buscarNotificacoesNaoLidas(
            @AuthenticationPrincipal User usuarioAtual
    ) {
        List<Notificacao> notificacoes = desafioService.buscarNotificacoesNaoLidas(usuarioAtual.getId());
        return ResponseEntity.ok(notificacoes);
    }

    @PostMapping("/notificacoes/{notificacaoId}/marcar-lida")
    public ResponseEntity<Notificacao> marcarNotificacaoComoLida(
            @PathVariable Long notificacaoId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        Notificacao notificacao = desafioService.marcarNotificacaoComoLida(notificacaoId, usuarioAtual);
        return ResponseEntity.ok(notificacao);
    }
} 