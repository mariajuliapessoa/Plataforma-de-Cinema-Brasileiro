package com.cesar.bracine.domain.repositories;

import com.cesar.bracine.domain.entities.Filme;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FilmeRepository {

    void salvar(Filme filme);

    Optional<Filme> buscarPorId(UUID id);

    List<Filme> listarTodos();

    void remover(UUID id);

    boolean existePorTitulo(String titulo);

    boolean existePorTituloEAno(String titulo, int anoLancamento);
}
