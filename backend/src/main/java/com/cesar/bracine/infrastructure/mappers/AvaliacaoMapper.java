package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.infrastructure.jpa.entities.AvaliacaoEntity;
import com.cesar.bracine.infrastructure.jpa.entities.FilmeEntity;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;

public class AvaliacaoMapper {

    public static AvaliacaoEntity toEntity(Avaliacao avaliacao) {
        var entity = new AvaliacaoEntity();
        entity.setId(avaliacao.getId().getValue());
        entity.setTexto(avaliacao.getTexto());
        entity.setNota(avaliacao.getNota());
        entity.setDataCriacao(avaliacao.getDataCriacao());

        var autorEntity = new UsuarioEntity();
        autorEntity.setId(avaliacao.getAutor().getValue());
        entity.setAutor(autorEntity);

        var filmeEntity = new FilmeEntity();
        filmeEntity.setId(avaliacao.getFilme().getValue());
        entity.setFilme(filmeEntity);

        return entity;
    }

    public static Avaliacao toDomain(AvaliacaoEntity entity) {
        return new Avaliacao(
                entity.getTexto(),
                UsuarioMapper.toDomain(entity.getAutor()).getId(),
                FilmeMapper.toDomain(entity.getFilme()).getId(),
                entity.getNota()
        );
    }
}
