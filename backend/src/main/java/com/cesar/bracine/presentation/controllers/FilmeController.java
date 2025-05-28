package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.FilmeApplicationService;
import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.presentation.dtos.FilmeRequestDTO;
import com.cesar.bracine.presentation.dtos.FilmeResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

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
    public ResponseEntity<FilmeResponseDTO> criarFilme(@RequestBody FilmeRequestDTO request) {
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
    public Page<FilmeResponseDTO> listarFilmes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Filme> filmesPage = filmeApplicationService.listarFilmesPaginados(pageable);

        return filmesPage.map(this::toResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilmePorId(@PathVariable UUID id) {
        return filmeApplicationService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFilme(@PathVariable UUID id) {
        filmeApplicationService.removerFilme(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/importar")
    public List<FilmeResponseDTO> importarFilmesBrasileiros() {
        List<Filme> importados = filmeApplicationService.importarFilmesBrasileiros();
        return importados.stream()
                .map(this::toResponse)
                .toList();
    }

    private FilmeResponseDTO toResponse(Filme filme) {
        return new FilmeResponseDTO(
                filme.getId().getValue(),
                filme.getTitulo(),
                filme.getDiretor(),
                filme.getAnoLancamento(),
                filme.getGeneros(),
                filme.getPaisOrigem(),
                filme.getBannerUrl()
        );
    }
}
