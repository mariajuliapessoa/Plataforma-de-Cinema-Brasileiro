package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity toEntity(Usuario usuario) {
        return UsuarioEntity.builder()
                .id(usuario.getId().getValue())
                .nome(usuario.getNome())
                .nomeUsuario(usuario.getNomeUsuario())
                .email(usuario.getEmail())
                .cargo(usuario.getCargo())
                .senhaHash(usuario.getSenhaHash())
                .pontos(usuario.getPontos())
                .build();
    }

    public static Usuario toDomain(UsuarioEntity entity) {
        return Usuario.reconstruir(
                new UsuarioId(entity.getId()),
                entity.getNome(),
                entity.getNomeUsuario(),
                entity.getEmail(),
                entity.getCargo(),
                entity.getSenhaHash(),
                entity.getPontos()
        );
    }
}
