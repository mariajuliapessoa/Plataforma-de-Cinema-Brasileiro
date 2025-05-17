package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.infrastructure.entities.DebateEntity;
import com.cesar.bracine.infrastructure.jpa.entities.ComentarioEntity;
import com.cesar.bracine.infrastructure.jpa.entities.FilmeEntity;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;

public class ComentarioMapper {

    public static ComentarioEntity toEntity(Comentario comentario) {
        var entity = new ComentarioEntity();
        entity.setId(comentario.getId());
        entity.setTexto(comentario.getTexto());
        entity.setDataCriacao(comentario.getDataCriacao());

        var autorEntity = new UsuarioEntity();
        autorEntity.setId(comentario.getAutor().getId());
        entity.setAutor(autorEntity);

        if (comentario.getFilme() != null) {
            var filmeEntity = new FilmeEntity();
            filmeEntity.setId(comentario.getFilme().getId());
            entity.setFilme(filmeEntity);
        }

        if (comentario.getDebate() != null) {
            var debateEntity = new DebateEntity();
            debateEntity.setId(comentario.getDebate().getId());
            entity.setDebate(debateEntity);
        }

        return entity;
    }

    public static Comentario toDomain(ComentarioEntity entity) {
        return new Comentario(
                entity.getId(),
                entity.getTexto(),
                UsuarioMapper.toDomain(entity.getAutor()),
                FilmeMapper.toDomain(entity.getFilme()),
                DebateMapper.toDomain(entity.getDebate()),
                entity.getDataCriacao()
        );
    }
}
