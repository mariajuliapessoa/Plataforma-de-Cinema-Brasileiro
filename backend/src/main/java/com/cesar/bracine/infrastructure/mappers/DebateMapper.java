package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.infrastructure.entities.DebateEntity;

public class DebateMapper {

    public static DebateEntity toEntity(Debate debate) {
        DebateEntity entity = new DebateEntity();
        entity.setId(debate.getId());
        entity.setTitulo(debate.getTitulo());
        entity.setCriador(UsuarioMapper.toEntity(debate.getCriador()));
        return entity;
    }

    public static Debate toDomain(DebateEntity entity) {
        return new Debate(
                entity.getId(),
                entity.getTitulo(),
                UsuarioMapper.toDomain(entity.getCriador())
        );
    }
}
