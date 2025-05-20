package com.cesar.bracine.presentation.dtos;

import java.time.Instant;
import java.util.UUID;

public record ComentarioResponseDTO(
        UUID id,
        String texto,
        UUID autor,
        UUID filme,
        UUID debate,
        Instant dataCriacao
) {
}
