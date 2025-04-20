package com.cesar.bracine.service;

import com.cesar.bracine.repository.FilmeRepository;
import org.springframework.stereotype.Service;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }
}
