package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.enums.TipoNotificacao;

import java.time.Instant;
import java.util.UUID;

public record NotificacaoResponseDTO(
        UUID id,
        UUID destinatarioId,
        String mensagem,
        TipoNotificacao tipo,
        Instant dataCriacao,
        boolean lida
) {}