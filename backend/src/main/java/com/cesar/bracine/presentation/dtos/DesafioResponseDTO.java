package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.entities.Desafio;

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
) {
    private DesafioResponseDTO toResponse(Desafio desafio) {
        return new DesafioResponseDTO(
                desafio.getId().getValue(),
                desafio.getTitulo(),
                desafio.getDescricao(),
                desafio.getPontos(),
                desafio.getDestinatario().getValue(),
                desafio.isConcluido(),
                desafio.getPrazo(),
                desafio.getDataCriacao()
        );
    }
}
