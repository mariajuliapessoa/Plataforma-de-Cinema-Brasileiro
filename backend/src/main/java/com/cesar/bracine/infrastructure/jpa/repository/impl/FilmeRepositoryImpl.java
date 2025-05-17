package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringFilmeJpaRepository;
import com.cesar.bracine.infrastructure.mappers.FilmeMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FilmeRepositoryImpl implements FilmeRepository {
    private static final Logger logger = LoggerFactory.getLogger(FilmeRepositoryImpl.class);
    private final SpringFilmeJpaRepository jpa;

    public FilmeRepositoryImpl(SpringFilmeJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Filme filme) {
        boolean jaExiste = jpa.existsByTituloIgnoreCaseAndAnoLancamento(filme.getTitulo(), filme.getAnoLancamento());

        if (!jaExiste) {
            jpa.save(FilmeMapper.toEntity(filme));
            logger.info("Salvo filme: {} ({})", filme.getTitulo(), filme.getAnoLancamento());
        } else {
            logger.info("Ignorado (já existe): {} ({})", filme.getTitulo(), filme.getAnoLancamento());
        }
    }

    @Override
    public Optional<Filme> buscarPorId(UUID id) {
        return jpa.findById(id).map(FilmeMapper::toDomain);
    }

    @Override
    public List<Filme> listarTodos() {
        return jpa.findAll().stream()
                .map(FilmeMapper::toDomain)
                .toList();
    }

    @Override
    public void remover(UUID id) {
        jpa.deleteById(id);
    }

    @Override
    public boolean existePorTitulo(String titulo) {
        return jpa.findByTitulo(titulo).stream()
                .anyMatch(f -> f.getTitulo().equalsIgnoreCase(titulo));
    }

    @Override
    public boolean existePorTituloEAno(String titulo, int anoLancamento) {
        return jpa.existsByTituloIgnoreCaseAndAnoLancamento(titulo, anoLancamento);
    }
}
