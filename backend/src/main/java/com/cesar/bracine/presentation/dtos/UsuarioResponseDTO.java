package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.enums.TipoUsuario;

import java.util.UUID;

public record UsuarioResponseDTO(UUID id, String nome, String nomeUsuario, String email, TipoUsuario cargo, int pontos) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(
                usuario.getId().getValue(),
                usuario.getNome(),
                usuario.getNomeUsuario(),
                usuario.getEmail(),
                usuario.getCargo(),
                usuario.getPontos()
        );
    }
}
