package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Filme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmeRepository {

    void salvar(Filme filme);

    Optional<Filme> buscarPorId(UUID id);

    Page<Filme> buscarPorTitulo(String titulo, Pageable pageable);

    List<Filme> listarTodos();

    void remover(UUID id);

    boolean existePorTitulo(String titulo);

    boolean existePorTituloEAno(String titulo, int anoLancamento);

    Page<Filme> listarPaginado(Pageable pageable);
}
