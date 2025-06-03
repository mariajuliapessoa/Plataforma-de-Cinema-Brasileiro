package com.cesar.bracine.infrastructure.jpa.repository;

import com.cesar.bracine.infrastructure.jpa.entities.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringComentarioJpaRepository extends JpaRepository<ComentarioEntity, UUID> {

    List<ComentarioEntity> findAllByAutorId(UUID id);

    @Query("""
        SELECT DISTINCT c FROM ComentarioEntity c
        LEFT JOIN FETCH c.respostas
        WHERE c.debate.id = :debateId AND c.comentarioPai IS NULL
        ORDER BY c.dataCriacao ASC
    """)
    List<ComentarioEntity> findAllRaizesByDebateIdWithRespostas(@Param("debateId") UUID debateId);
}
