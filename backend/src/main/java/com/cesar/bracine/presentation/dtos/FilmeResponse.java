package com.cesar.bracine.presentation.dtos;

import java.util.List;
import java.util.UUID;

public record FilmeResponse(
        UUID id,
        String titulo,
        String diretor,
        int anoLancamento,
        List<String> generos,
        String paisOrigem,
        String bannerUrl
) {}