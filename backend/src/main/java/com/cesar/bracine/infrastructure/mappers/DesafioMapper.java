package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.valueobjects.DesafioId;
import com.cesar.bracine.infrastructure.jpa.entities.DesafioEntity;

public class DesafioMapper {

    public static DesafioEntity toEntity(Desafio desafio, Usuario usuario) {
        DesafioEntity entity = new DesafioEntity();
        entity.setId(desafio.getId().getValue());
        entity.setTitulo(desafio.getTitulo());
        entity.setDescricao(desafio.getDescricao());
        entity.setPontos(desafio.getPontos());
        entity.setDestinatario(UsuarioMapper.toEntity(usuario));
        entity.setDataCriacao(desafio.getDataCriacao());
        entity.setPrazo(desafio.getPrazo());
        entity.setConcluido(desafio.isConcluido());
        return entity;
    }

    public static Desafio toDomain(DesafioEntity entity) {
        return new Desafio(
                new DesafioId(entity.getId()),
                entity.getTitulo(),
                entity.getDescricao(),
                entity.getPontos(),
                UsuarioMapper.toDomain(entity.getDestinatario()).getId(),
                entity.getDataCriacao(),
                entity.getPrazo(),
                entity.isConcluido()
        );
    }
}