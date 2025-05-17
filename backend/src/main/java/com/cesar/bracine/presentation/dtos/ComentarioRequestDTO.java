package com.cesar.bracine.presentation.dtos;

import java.util.UUID;

public record ComentarioRequestDTO(String texto, UUID autorId, UUID filmeId, UUID debateId) {
}
