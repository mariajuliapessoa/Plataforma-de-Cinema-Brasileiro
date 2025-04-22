package com.cesar.bracine.controller;

import com.cesar.bracine.model.DenunciaComentario;
import com.cesar.bracine.service.DenunciaComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/denuncias/comentarios")
@CrossOrigin
public class DenunciaComentarioController {

    @Autowired
    private DenunciaComentarioService denunciaComentarioService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<List<DenunciaComentario>> listarTodasDenuncias() {
        List<DenunciaComentario> denuncias = denunciaComentarioService.listarTodasDenuncias();
        return ResponseEntity.ok(denuncias);
    }

    @GetMapping("/pendentes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<List<DenunciaComentario>> listarDenunciasNaoRevisadas() {
        List<DenunciaComentario> denuncias = denunciaComentarioService.listarDenunciasNaoRevisadas();
        return ResponseEntity.ok(denuncias);
    }

    @GetMapping("/pendentes/quantidade")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<Map<String, Long>> contarDenunciasNaoRevisadas() {
        long quantidade = denunciaComentarioService.contarDenunciasNaoRevisadas();
        return ResponseEntity.ok(Map.of("quantidade", quantidade));
    }

    @GetMapping("/revisadas")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<List<DenunciaComentario>> listarDenunciasRevisadas(
            @RequestParam(required = false) Boolean procedente) {
        List<DenunciaComentario> denuncias = denunciaComentarioService.listarDenunciasRevisadas(procedente);
        return ResponseEntity.ok(denuncias);
    }

    @GetMapping("/comentario/{comentarioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<List<DenunciaComentario>> listarDenunciasPorComentario(
            @PathVariable Long comentarioId) {
        List<DenunciaComentario> denuncias = denunciaComentarioService.listarDenunciasPorComentario(comentarioId);
        return ResponseEntity.ok(denuncias);
    }

    /**
     * Lista denúncias feitas por um usuário específico
     * @param usuarioId ID do usuário
     * @return Lista de denúncias feitas pelo usuário
     */
    @GetMapping("/usuario/{usuarioId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR') or #usuarioId == authentication.principal.id")
    public ResponseEntity<List<DenunciaComentario>> listarDenunciasPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(denunciaComentarioService.listarDenunciasPorUsuario(usuarioId));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<DenunciaComentario> buscarDenunciaPorId(@PathVariable Long id) {
        DenunciaComentario denuncia = denunciaComentarioService.buscarDenunciaPorId(id);
        return ResponseEntity.ok(denuncia);
    }

    @PostMapping
    public ResponseEntity<DenunciaComentario> registrarDenuncia(
            @RequestBody Map<String, Object> payload) {
        String usuarioId = (String) payload.get("usuarioId");
        Long comentarioId = Long.parseLong(payload.get("comentarioId").toString());
        String motivo = (String) payload.get("motivo");

        DenunciaComentario denuncia = denunciaComentarioService.registrarDenuncia(usuarioId, comentarioId, motivo);
        return ResponseEntity.status(HttpStatus.CREATED).body(denuncia);
    }

    @PutMapping("/{id}/revisar")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MODERADOR')")
    public ResponseEntity<DenunciaComentario> revisarDenuncia(
            @PathVariable Long id,
            @RequestBody Map<String, Object> payload) {
        String revisorId = (String) payload.get("revisorId");
        boolean procedente = (boolean) payload.get("procedente");

        DenunciaComentario denuncia = denunciaComentarioService.revisarDenuncia(id, revisorId, procedente);
        return ResponseEntity.ok(denuncia);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluirDenuncia(@PathVariable Long id) {
        denunciaComentarioService.excluirDenuncia(id);
        return ResponseEntity.noContent().build();
    }
} 