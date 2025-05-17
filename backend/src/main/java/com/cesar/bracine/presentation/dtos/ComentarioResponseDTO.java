package com.cesar.bracine.presentation.dtos;

import java.time.Instant;
import java.util.UUID;

public record ComentarioResponseDTO(
        UUID id,
        String texto,
        String autorNome,
        String filmeTitulo,
        String debateTitulo,
        Instant dataCriacao
) {
}
