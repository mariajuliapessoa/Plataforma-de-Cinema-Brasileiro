package com.cesar.bracine.presentation.dtos;

import java.util.List;
import java.util.UUID;

public record FilmeResponseDTO(
        UUID id,
        String titulo,
        String diretor,
        String sinopse,
        int anoLancamento,
        double avaliacao,
        List<String> generos,
        String paisOrigem,
        String bannerUrl
) {}