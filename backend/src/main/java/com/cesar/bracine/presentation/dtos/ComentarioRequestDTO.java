package com.cesar.bracine.presentation.dtos;

import jakarta.annotation.Nullable;
import java.util.UUID;

public record ComentarioRequestDTO(String texto, UUID autorId, UUID filmeId, UUID debateId, @Nullable UUID comentarioPaiId ) {
}
