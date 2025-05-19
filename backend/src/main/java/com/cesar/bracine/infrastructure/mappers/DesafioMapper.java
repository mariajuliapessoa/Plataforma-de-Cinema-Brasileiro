package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.infrastructure.jpa.entities.DesafioEntity;

public class DesafioMapper {

    public static DesafioEntity toEntity(Desafio desafio) {
        DesafioEntity entity = new DesafioEntity();
        entity.setId(desafio.getId());
        entity.setTitulo(desafio.getTitulo());
        entity.setDescricao(desafio.getDescricao());
        entity.setPontos(desafio.getPontos());
        entity.setDestinatario(UsuarioMapper.toEntity(desafio.getDestinatario())); // você também precisará disso
        entity.setDataCriacao(desafio.getDataCriacao());
        entity.setPrazo(desafio.getPrazo());
        entity.setConcluido(desafio.isConcluido());
        return entity;
    }

    public static Desafio toDomain(DesafioEntity entity) {
        return new Desafio(
                entity.getId(),
                entity.getTitulo(),
                entity.getDescricao(),
                entity.getPontos(),
                UsuarioMapper.toDomain(entity.getDestinatario()),
                entity.getDataCriacao(),
                entity.getPrazo(),
                entity.isConcluido()
        );
    }
}