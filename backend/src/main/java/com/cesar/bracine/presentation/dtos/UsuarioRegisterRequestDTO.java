package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.enums.TipoUsuario;

public record UsuarioRegisterRequestDTO(String nome, String nomeUsuario, String email, String senha, TipoUsuario cargo) {
}
