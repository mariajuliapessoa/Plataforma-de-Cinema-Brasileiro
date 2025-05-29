package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.entities.Comentario;

import java.time.Instant;
import java.util.UUID;

public record ComentarioResponseDTO(
        UUID id,
        String texto,
        UUID autorId,
        UUID filmeId,
        UUID debateId,
        Instant dataCriacao
) {
    public static ComentarioResponseDTO fromDomain(Comentario comentario) {
        return new ComentarioResponseDTO(
                comentario.getId().getValue(),
                comentario.getTexto(),
                comentario.getAutor().getValue(),
                comentario.getFilme().getValue(),
                comentario.getDebate().getValue(),
                comentario.getDataCriacao()
        );
    }
}
