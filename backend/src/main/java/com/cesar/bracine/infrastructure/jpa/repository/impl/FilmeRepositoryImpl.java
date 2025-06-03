package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.infrastructure.jpa.entities.FilmeEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringFilmeJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.FilmeMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class FilmeRepositoryImpl
        extends RepositoryAbstratoImpl<Filme, FilmeEntity, UUID, SpringFilmeJpaRepository>
        implements FilmeRepository {

    private static final Logger logger = LoggerFactory.getLogger(FilmeRepositoryImpl.class);

    public FilmeRepositoryImpl(SpringFilmeJpaRepository jpa) {
        super(jpa);
    }

    @Override
    protected void logEntityNotFound(UUID id) {
        logger.warn("Filme com ID {} não encontrado no banco.", id);
    }

    @Override
    public void salvar(Filme filme) {
        boolean jaExiste = jpaRepository.existsByTituloIgnoreCaseAndAnoLancamento(filme.getTitulo(), filme.getAnoLancamento());

        if (!jaExiste) {
            jpaRepository.save(mapToEntity(filme));
            logger.info("Salvo filme: {} ({})", filme.getTitulo(), filme.getAnoLancamento());
        } else {
            logger.info("Ignorado (já existe): {} ({})", filme.getTitulo(), filme.getAnoLancamento());
        }
    }

    @Override
    public Page<Filme> buscarPorTitulo(String titulo, Pageable pageable) {
        Page<FilmeEntity> entidadesPage = jpaRepository.findByTituloContainingIgnoreCase(titulo, pageable);

        List<Filme> filmes = entidadesPage.getContent().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(filmes, pageable, entidadesPage.getTotalElements());
    }

    @Override
    public Page<Filme> listarPaginado(Pageable pageable) {
        Page<FilmeEntity> entidadesPage = jpaRepository.findAll(pageable);

        List<Filme> filmes = entidadesPage.getContent().stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());

        return new PageImpl<>(filmes, pageable, entidadesPage.getTotalElements());
    }

    @Override
    public List<Filme> listarTodos() {
        // Limitar a 100 resultados temporariamente
        return jpaRepository.findAll(PageRequest.of(0, 100))
                .getContent()
                .stream()
                .map(this::mapToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorTitulo(String titulo) {
        return jpaRepository.findByTitulo(titulo).stream()
                .anyMatch(f -> f.getTitulo().equalsIgnoreCase(titulo));
    }

    @Override
    public boolean existePorTituloEAno(String titulo, int anoLancamento) {
        return jpaRepository.existsByTituloIgnoreCaseAndAnoLancamento(titulo, anoLancamento);
    }

    @Override
    protected FilmeEntity mapToEntity(Filme filme) {
        return FilmeMapper.toEntity(filme);
    }

    @Override
    protected Filme mapToDomain(FilmeEntity entity) {
        return FilmeMapper.toDomain(entity);
    }
}
