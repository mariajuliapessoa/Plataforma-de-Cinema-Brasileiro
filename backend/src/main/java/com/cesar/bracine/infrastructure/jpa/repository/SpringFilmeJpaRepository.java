package com.cesar.bracine.infrastructure.jpa.repository;

import com.cesar.bracine.infrastructure.jpa.entities.FilmeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringFilmeJpaRepository extends JpaRepository<FilmeEntity, UUID> {
    Optional<FilmeEntity> findByTituloAndAnoLancamento(String titulo, int anoLancamento);
    Optional<FilmeEntity> findByTitulo(String titulo);
    Page<FilmeEntity> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);
    boolean existsByTituloIgnoreCaseAndAnoLancamento(String titulo, int anoLancamento);
}
