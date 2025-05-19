package com.cesar.bracine.presentation.dtos;

import com.cesar.bracine.domain.enums.TipoNotificacao;

import java.util.UUID;

public record NotificacaoRequestDTO(
        UUID destinatarioId,
        String mensagem,
        TipoNotificacao tipo
) {}