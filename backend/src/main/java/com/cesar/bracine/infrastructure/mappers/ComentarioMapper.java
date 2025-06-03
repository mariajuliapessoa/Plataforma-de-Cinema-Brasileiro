package com.cesar.bracine.infrastructure.mappers;

import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.domain.valueobjects.ComentarioId;
import com.cesar.bracine.infrastructure.jpa.entities.ComentarioEntity;
import com.cesar.bracine.infrastructure.jpa.entities.DebateEntity;
import com.cesar.bracine.infrastructure.jpa.entities.FilmeEntity;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;

public class ComentarioMapper {

    public static ComentarioEntity toEntity(Comentario comentario) {
        var entity = new ComentarioEntity();
        entity.setId(comentario.getId().getValue());
        entity.setTexto(comentario.getTexto());
        entity.setDataCriacao(comentario.getDataCriacao());

        var autorEntity = new UsuarioEntity();
        autorEntity.setId(comentario.getAutor().getValue());
        entity.setAutor(autorEntity);

        if (comentario.getFilme() != null) {
            var filmeEntity = new FilmeEntity();
            filmeEntity.setId(comentario.getFilme().getValue());
            entity.setFilme(filmeEntity);
        }

        if (comentario.getDebate() != null) {
            var debateEntity = new DebateEntity();
            debateEntity.setId(comentario.getDebate().getValue());
            entity.setDebate(debateEntity);
        }

        if (comentario.getComentarioPai() != null) {
            var comentarioPaiEntity = new ComentarioEntity();
            comentarioPaiEntity.setId(comentario.getComentarioPai().getId().getValue());
            entity.setComentarioPai(comentarioPaiEntity);
        }
        return entity;
    }

    public static Comentario toDomain(ComentarioEntity entity) {
        Comentario comentarioPaiDomain = null;
        if (entity.getComentarioPai() != null) {
            comentarioPaiDomain = toDomain(entity.getComentarioPai());
        }

        Comentario comentario = new Comentario(
                new ComentarioId(entity.getId()),
                entity.getTexto(),
                UsuarioMapper.toDomain(entity.getAutor()).getId(),
                entity.getFilme() != null ? FilmeMapper.toDomain(entity.getFilme()).getId() : null,
                entity.getDebate() != null ? DebateMapper.toDomain(entity.getDebate()).getId() : null,
                entity.getDataCriacao()
        );

        if (comentarioPaiDomain != null) {
            comentario = new Comentario(
                    comentario.getTexto(),
                    comentario.getAutor(),
                    comentario.getFilme(),
                    comentario.getDebate(),
                    comentarioPaiDomain
            );
        }

        if (entity.getRespostas() != null && !entity.getRespostas().isEmpty()) {
            for (ComentarioEntity respostaEntity : entity.getRespostas()) {
                Comentario respostaDomain = toDomain(respostaEntity);
                // Aqui usa método para adicionar resposta na lista (assumindo que exista)
                comentario.adicionarResposta(respostaDomain);
            }
        }

        return comentario;
    }
}
