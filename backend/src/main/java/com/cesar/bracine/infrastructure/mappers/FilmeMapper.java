package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.infrastructure.jpa.entities.FilmeEntity;

public class FilmeMapper {

    public static FilmeEntity toEntity(Filme filme) {
        FilmeEntity entity = new FilmeEntity();
        entity.setTitulo(filme.getTitulo());
        entity.setDiretor(filme.getDiretor());
        entity.setSinopse(filme.getSinopse());
        entity.setAnoLancamento(filme.getAnoLancamento());
        entity.setAvaliacao(filme.getAvaliacao());
        entity.setGeneros(filme.getGeneros());
        entity.setPaisOrigem(filme.getPaisOrigem());
        entity.setBannerUrl(filme.getBannerUrl());
        return entity;
    }

    public static Filme toDomain(FilmeEntity entity) {
        if (entity.getId() == null) {
            throw new IllegalArgumentException("FilmeEntity sem ID não pode ser convertido para domínio.");
        }

        return new Filme(
                new FilmeId(entity.getId()),
                entity.getTitulo(),
                entity.getDiretor(),
                entity.getSinopse(),
                entity.getAnoLancamento(),
                entity.getAvaliacao(),
                entity.getGeneros(),
                entity.getPaisOrigem(),
                entity.getBannerUrl()
        );
    }
}
