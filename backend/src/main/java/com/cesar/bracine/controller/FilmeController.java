package com.cesar.bracine.controller;

import com.cesar.bracine.model.Filme;
import com.cesar.bracine.service.FilmeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    private final FilmeService filmeService;

    public FilmeController(FilmeService filmeService) {
        this.filmeService = filmeService;
    }
    
    @GetMapping
    public ResponseEntity<?> listarTodos(@RequestParam(required = false) String busca) {
        List<Filme> filmes = filmeService.listarFilmes(busca);

        if (filmes.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensagem", "Nenhum resultado encontrado"));
        }

        return ResponseEntity.ok(filmes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarPorId(@PathVariable Long id) {
        return filmeService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
