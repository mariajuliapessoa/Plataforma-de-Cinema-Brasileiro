package com.cesar.bracine.presentation.dtos;

import java.util.UUID;

public record AvaliacaoRequestDTO(String texto, int nota, UUID usuarioId, UUID filmeId) {
}
