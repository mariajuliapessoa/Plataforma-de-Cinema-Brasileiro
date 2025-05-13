package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FilmeService {

    private final FilmeRepository filmeRepository;

    public FilmeService(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    // Criar ou Editar um filme
    public Filme salvarFilme(Filme filme) {
        filmeRepository.salvar(filme);
        return filme;
    }

    // Buscar um filme por ID
    public Optional<Filme> buscarFilmePorId(UUID id) {
        return filmeRepository.buscarPorId(id);
    }

    // Listar todos os filmes
    public List<Filme> listarTodosFilmes() {
        return filmeRepository.listarTodos();
    }

    // Remover um filme
    public void removerFilme(UUID id) {
        filmeRepository.remover(id);
    }
}
