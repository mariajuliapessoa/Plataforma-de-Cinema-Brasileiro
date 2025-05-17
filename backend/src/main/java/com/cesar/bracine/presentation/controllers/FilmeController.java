package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.FilmeApplicationService;
import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.presentation.dtos.FilmeRequest;
import com.cesar.bracine.presentation.dtos.FilmeResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/filmes")
public class FilmeController {

    private final FilmeApplicationService filmeApplicationService;

    public FilmeController(FilmeApplicationService filmeApplicationService) {
        this.filmeApplicationService = filmeApplicationService;
    }

    @PostMapping
    public ResponseEntity<FilmeResponse> criarFilme(@RequestBody FilmeRequest request) {
        Filme filme = new Filme(
                request.titulo(),
                request.diretor(),
                request.anoLancamento(),
                request.generos(),
                request.paisOrigem(),
                request.bannerUrl()
        );

        Filme salvo = filmeApplicationService.salvarFilme(filme);

        return ResponseEntity.ok(toResponse(salvo));
    }

    @GetMapping
    public List<FilmeResponse> listarFilmes() {
        return filmeApplicationService.listarTodosFilmes().stream()
                .map(this::toResponse)
                .toList();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFilme(@PathVariable UUID id) {
        filmeApplicationService.removerFilme(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/importar")
    public List<FilmeResponse> importarFilmesBrasileiros() {
        List<Filme> importados = filmeApplicationService.importarFilmesBrasileiros();
        return importados.stream()
                .map(this::toResponse)
                .toList();
    }

    private FilmeResponse toResponse(Filme filme) {
        return new FilmeResponse(
                filme.getId(),
                filme.getTitulo(),
                filme.getDiretor(),
                filme.getAnoLancamento(),
                filme.getGeneros(),
                filme.getPaisOrigem(),
                filme.getBannerUrl()
        );
    }
}
