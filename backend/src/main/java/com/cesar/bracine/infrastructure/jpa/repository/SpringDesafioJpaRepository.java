package com.cesar.bracine.infrastructure.jpa.repository;

import com.cesar.bracine.infrastructure.jpa.entities.DesafioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDesafioJpaRepository extends JpaRepository<DesafioEntity, UUID> {
    List<DesafioEntity> findAllByDestinatarioId(UUID id);
}
