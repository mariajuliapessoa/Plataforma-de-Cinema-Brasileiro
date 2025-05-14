package com.cesar.bracine.infrastructure.mapper;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;
import com.cesar.bracine.presentation.dtos.UsuarioRegisterRequestDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UsuarioMapper {

    public static UsuarioEntity toEntity(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .nomeUsuario(usuario.getNomeUsuario())
                .email(usuario.getEmail())
                .senhaHash(usuario.getSenhaHash())
                .pontos(usuario.getPontos())
                .build();
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        return Usuario.reconstruir(
                entity.getId(),
                entity.getNome(),
                entity.getNomeUsuario(),
                entity.getEmail(),
                entity.getSenhaHash(),
                entity.getPontos()
        );
    }
}
