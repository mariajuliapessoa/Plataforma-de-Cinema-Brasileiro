package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.entities.Usuario;

import java.util.UUID;

public record UsuarioResponseDTO(UUID id, String nome, String nomeUsuario, String email, String senha, int pontos) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNome(),
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getSenhaHash(),
                usuario.getPontos()
        );
    }
}
