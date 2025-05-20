package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.valueobjects.DebateId;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import com.cesar.bracine.infrastructure.jpa.entities.DebateEntity;

public class DebateMapper {

    public static DebateEntity toEntity(Debate debate, Usuario usuario) {
        DebateEntity entity = new DebateEntity();

        entity.setId(debate.getId().getValue());
        entity.setTitulo(debate.getTitulo());
        entity.setCriador(UsuarioMapper.toEntity(usuario));

        return entity;
    }

    public static Debate toDomain(DebateEntity entity) {
        return new Debate(
                new DebateId(entity.getId()),
                entity.getTitulo(),
                new UsuarioId(entity.getCriador().getId())
        );
    }
}
