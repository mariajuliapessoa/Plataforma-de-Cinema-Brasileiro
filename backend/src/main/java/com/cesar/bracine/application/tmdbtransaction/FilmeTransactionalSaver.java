package com.cesar.bracine.application.tmdbtransaction;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class FilmeTransactionalSaver {

    private final FilmeRepository filmeRepository;

    public FilmeTransactionalSaver(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void salvarFilme(Filme filme) {
        if (!filmeRepository.existePorTitulo(filme.getTitulo())) {
            filmeRepository.salvar(filme);
        }
    }
}
