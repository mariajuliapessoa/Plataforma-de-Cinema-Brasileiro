package com.cesar.bracine.presentation.dtos;

import java.time.LocalDate;
import java.util.UUID;

public record DesafioResponseDTO(
        UUID id,
        String titulo,
        String descricao,
        int pontos,
        UUID destinatarioId,
        boolean concluido,
        LocalDate prazo,
        LocalDate dataCriacao
) {}
