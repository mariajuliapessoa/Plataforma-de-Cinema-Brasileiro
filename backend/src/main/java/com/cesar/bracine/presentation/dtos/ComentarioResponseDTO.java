package com.cesar.bracine.presentation.dtos;

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
}
