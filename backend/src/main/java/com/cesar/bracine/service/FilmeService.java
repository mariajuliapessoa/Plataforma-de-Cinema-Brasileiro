package com.cesar.bracine.service;

import com.cesar.bracine.model.Filme;
import com.cesar.bracine.repository.FilmeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    public List<Filme> listarFilmes(String busca) {
        if (busca == null || busca.isBlank()) {
            return filmeRepository.findAll();
        }
        return filmeRepository.buscarPorTermo(busca);
    }

    public Optional<Filme> buscarPorId(Long id) {
        return filmeRepository.findById(id);
    }
}
