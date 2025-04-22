package com.cesar.bracine.controller;

import com.cesar.bracine.model.ComentarioLista;
import com.cesar.bracine.model.ItemLista;
import com.cesar.bracine.model.ListaFilmes;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.service.ListaFilmesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/listas")
public class ListaFilmesController {

    private final ListaFilmesService listaFilmesService;

    @Autowired
    public ListaFilmesController(ListaFilmesService listaFilmesService) {
        this.listaFilmesService = listaFilmesService;
    }

    @GetMapping("/publicas")
    public ResponseEntity<List<ListaFilmes>> listarListasPublicas() {
        List<ListaFilmes> listas = listaFilmesService.buscarListasPublicas();
        return ResponseEntity.ok(listas);
    }

    @GetMapping("/minhas")
    public ResponseEntity<List<ListaFilmes>> listarListasAcessiveis(@AuthenticationPrincipal User usuarioAtual) {
        List<ListaFilmes> listas = listaFilmesService.buscarListasAcessiveis(usuarioAtual.getId());
        return ResponseEntity.ok(listas);
    }

    @GetMapping("/{listaId}")
    public ResponseEntity<?> buscarListaPorId(
            @PathVariable Long listaId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            ListaFilmes lista = listaFilmesService.buscarPorId(listaId, usuarioAtual);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ListaFilmes> criarLista(
            @RequestBody Map<String, Object> listaRequest,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        String nome = (String) listaRequest.get("nome");
        String descricao = (String) listaRequest.get("descricao");
        boolean publica = listaRequest.containsKey("publica") ? (boolean) listaRequest.get("publica") : true;
        boolean colaborativa = listaRequest.containsKey("colaborativa") ? (boolean) listaRequest.get("colaborativa") : false;

        ListaFilmes novaLista = listaFilmesService.criarLista(nome, descricao, usuarioAtual, publica, colaborativa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaLista);
    }

    @PutMapping("/{listaId}")
    public ResponseEntity<?> atualizarLista(
            @PathVariable Long listaId,
            @RequestBody Map<String, Object> listaRequest,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            String nome = (String) listaRequest.get("nome");
            String descricao = (String) listaRequest.get("descricao");
            boolean publica = listaRequest.containsKey("publica") ? (boolean) listaRequest.get("publica") : true;
            boolean colaborativa = listaRequest.containsKey("colaborativa") ? (boolean) listaRequest.get("colaborativa") : false;
            boolean permiteVotacao = listaRequest.containsKey("permiteVotacao") ? (boolean) listaRequest.get("permiteVotacao") : false;

            ListaFilmes lista = listaFilmesService.atualizarLista(
                    listaId, nome, descricao, publica, colaborativa, permiteVotacao, usuarioAtual);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{listaId}")
    public ResponseEntity<?> excluirLista(
            @PathVariable Long listaId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            listaFilmesService.excluirLista(listaId, usuarioAtual);
            return ResponseEntity.ok(Map.of("mensagem", "Lista excluída com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{listaId}/colaboradores/{usuarioId}")
    public ResponseEntity<?> adicionarColaborador(
            @PathVariable Long listaId,
            @PathVariable String usuarioId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            ListaFilmes lista = listaFilmesService.adicionarColaborador(listaId, usuarioId, usuarioAtual);
            return ResponseEntity.ok(lista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{listaId}/filmes/{filmeId}")
    public ResponseEntity<?> adicionarFilmeALista(
            @PathVariable Long listaId,
            @PathVariable Long filmeId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            ItemLista item = listaFilmesService.adicionarFilmeALista(listaId, filmeId, usuarioAtual);
            return ResponseEntity.status(HttpStatus.CREATED).body(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/{listaId}/filmes/{filmeId}")
    public ResponseEntity<?> removerFilmeDaLista(
            @PathVariable Long listaId,
            @PathVariable Long filmeId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            listaFilmesService.removerFilmeDaLista(listaId, filmeId, usuarioAtual);
            return ResponseEntity.ok(Map.of("mensagem", "Filme removido da lista com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{listaId}/filmes")
    public ResponseEntity<?> listarFilmesDaLista(
            @PathVariable Long listaId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            List<ItemLista> itens = listaFilmesService.buscarItensDaLista(listaId, usuarioAtual);
            return ResponseEntity.ok(itens);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/itens/{itemId}/votar")
    public ResponseEntity<?> votarEmItemLista(
            @PathVariable Long itemId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            ItemLista item = listaFilmesService.votarEmItemLista(itemId, usuarioAtual);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @DeleteMapping("/itens/{itemId}/votar")
    public ResponseEntity<?> removerVotoEmItemLista(
            @PathVariable Long itemId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            ItemLista item = listaFilmesService.removerVotoEmItemLista(itemId, usuarioAtual);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping("/{listaId}/comentarios")
    public ResponseEntity<?> adicionarComentario(
            @PathVariable Long listaId,
            @RequestBody Map<String, String> comentarioRequest,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            String texto = comentarioRequest.get("texto");
            if (texto == null || texto.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("erro", "O comentário não pode estar vazio"));
            }
            
            ComentarioLista comentario = listaFilmesService.adicionarComentario(listaId, texto, usuarioAtual);
            return ResponseEntity.status(HttpStatus.CREATED).body(comentario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/{listaId}/comentarios")
    public ResponseEntity<?> listarComentarios(
            @PathVariable Long listaId,
            @AuthenticationPrincipal User usuarioAtual
    ) {
        try {
            List<ComentarioLista> comentarios = listaFilmesService.buscarComentarios(listaId, usuarioAtual);
            return ResponseEntity.ok(comentarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", e.getMessage()));
        }
    }
} 