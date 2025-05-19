package com.cesar.bracine.presentation.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record CriarDesafioRequestDTO(
        String titulo,
        String descricao,
        int pontos,
        UUID destinatarioId,
        LocalDate prazo
) {}