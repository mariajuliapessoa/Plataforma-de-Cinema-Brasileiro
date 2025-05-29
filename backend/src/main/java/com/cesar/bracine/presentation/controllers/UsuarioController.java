package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.UsuarioApplicationService;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;
import com.cesar.bracine.presentation.dtos.UsuarioRegisterRequestDTO;
import com.cesar.bracine.presentation.dtos.UsuarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioApplicationService usuarioService;

    public UsuarioController(UsuarioApplicationService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/me")
    public ResponseEntity<UsuarioResponseDTO> getUsuarioAtual() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UsuarioEntity usuarioAutenticado) {
            UUID id = usuarioAutenticado.getId();

            return usuarioService.buscarPorId(id)
                    .map(usuario -> ResponseEntity.ok(new UsuarioResponseDTO(usuario)))
                    .orElse(ResponseEntity.notFound().build());
        }

        return ResponseEntity.status(401).build();
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> criar(@RequestBody UsuarioRegisterRequestDTO dto) {
        Usuario usuario = usuarioService.criarUsuario(dto.nome(), dto.nomeUsuario(), dto.email(), dto.cargo(), dto.senha());
        return ResponseEntity.ok(
                new UsuarioResponseDTO(usuario));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable UUID id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(new UsuarioResponseDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = usuarioService.listarTodos().stream()
                .map(UsuarioResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(
            @PathVariable UUID id,
            @RequestBody UsuarioRegisterRequestDTO dto
    ) {
        usuarioService.editarDados(id, dto.nome(), dto.nomeUsuario(), dto.email());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
