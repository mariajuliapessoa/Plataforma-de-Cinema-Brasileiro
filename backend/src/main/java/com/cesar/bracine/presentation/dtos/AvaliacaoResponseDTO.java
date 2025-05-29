package com.cesar.bracine.presentation.dtos;

import java.time.Instant;
import java.util.UUID;

public record AvaliacaoResponseDTO(
        UUID id,
        String texto,
        int nota,
        UUID autorId,
        UUID filmeId,
        Instant dataCriacao
) {
}
