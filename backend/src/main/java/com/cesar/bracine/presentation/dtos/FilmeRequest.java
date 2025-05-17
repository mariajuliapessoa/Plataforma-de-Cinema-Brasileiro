package com.cesar.bracine.presentation.dtos;

import java.util.List;

public record FilmeRequest(
        String titulo,
        String diretor,
        int anoLancamento,
        List<String> generos,
        String paisOrigem,
        String bannerUrl
) {}