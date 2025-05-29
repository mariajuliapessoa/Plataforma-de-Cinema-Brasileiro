package com.cesar.bracine.presentation.dtos;

import java.util.List;

public record FilmeRequestDTO(
        String titulo,
        String diretor,
        String sinopse,
        int anoLancamento,
        double avaliacao,
        List<String> generos,
        String paisOrigem,
        String bannerUrl
) {}