package com.cesar.bracine.infrastructure.jpa.repository;

import com.cesar.bracine.infrastructure.jpa.entities.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringComentarioJpaRepository extends JpaRepository<ComentarioEntity, UUID> {
    List<ComentarioEntity> findAllByAutorId(UUID id);
}