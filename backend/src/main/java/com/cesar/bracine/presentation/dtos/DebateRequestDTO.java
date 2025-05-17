package com.cesar.bracine.presentation.dtos;

import java.util.UUID;

public record DebateRequestDTO(String titulo, UUID usuarioId) {
}
