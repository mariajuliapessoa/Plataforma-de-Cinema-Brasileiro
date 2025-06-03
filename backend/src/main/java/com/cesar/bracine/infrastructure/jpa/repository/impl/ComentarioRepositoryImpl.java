package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.domain.repositories.ComentarioRepository;
import com.cesar.bracine.infrastructure.jpa.entities.ComentarioEntity;
import com.cesar.bracine.infrastructure.jpa.entities.DebateEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringComentarioJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringDebateJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.ComentarioMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ComentarioRepositoryImpl
        extends RepositoryAbstratoImpl<Comentario, ComentarioEntity, UUID, SpringComentarioJpaRepository>
        implements ComentarioRepository {

    private final SpringDebateJpaRepository jpaDebate;

    public ComentarioRepositoryImpl(SpringComentarioJpaRepository jpa, SpringDebateJpaRepository jpaDebate) {
        super(jpa);
        this.jpaDebate = jpaDebate;
    }

    @Override
    public void salvar(Comentario comentario) {
        ComentarioEntity entity = mapToEntity(comentario);

        if (comentario.getDebate() != null) {
            UUID debateId = comentario.getDebate().getValue();
            DebateEntity debateEntity = jpaDebate.findById(debateId)
                    .orElseThrow(() -> new EntityNotFoundException("Debate não encontrado"));

            entity.setDebate(debateEntity);
        }

        jpaRepository.save(entity);
    }

    @Override
    public List<Comentario> buscarPorDebate(UUID debateId) {
        List<ComentarioEntity> entities = jpaRepository.findAllRaizesByDebateIdWithRespostas(debateId);
        return entities.stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    public List<Comentario> buscarPorIdUsuario(UUID id) {
        return jpaRepository.findAllByAutorId(id).stream()
                .map(this::mapToDomain)
                .toList();
    }

    @Override
    protected ComentarioEntity mapToEntity(Comentario comentario) {
        return ComentarioMapper.toEntity(comentario);
    }

    @Override
    protected Comentario mapToDomain(ComentarioEntity entity) {
        Set<UUID> comentariosProcessados = new HashSet<>();
        return ComentarioMapper.toDomain(entity, comentariosProcessados);
    }

    @Override
    protected void logEntityNotFound(UUID id) {
        System.out.println("Comentário com ID " + id + " não encontrado no banco.");
    }
}
