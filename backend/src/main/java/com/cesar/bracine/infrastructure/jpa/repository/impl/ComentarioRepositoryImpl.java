package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.domain.repositories.ComentarioRepository;
import com.cesar.bracine.infrastructure.jpa.entities.ComentarioEntity;
import com.cesar.bracine.infrastructure.jpa.entities.DebateEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringComentarioJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringDebateJpaRepository;
import com.cesar.bracine.infrastructure.mappers.ComentarioMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ComentarioRepositoryImpl implements ComentarioRepository {

    private final SpringComentarioJpaRepository jpaComentario;
    private final SpringDebateJpaRepository jpaDebate;

    public ComentarioRepositoryImpl(SpringComentarioJpaRepository jpa, SpringDebateJpaRepository jpaDebate) {
        this.jpaComentario = jpa;
        this.jpaDebate = jpaDebate;
    }

    @Override
    public void salvar(Comentario comentario) {
        ComentarioEntity entity = ComentarioMapper.toEntity(comentario);

        if (comentario.getDebate() != null) {
            UUID debateId = comentario.getDebate().getId();
            DebateEntity debateEntity = jpaDebate.findById(debateId)
                    .orElseThrow(() -> new EntityNotFoundException("Debate não encontrado"));

            entity.setDebate(debateEntity);
        }

        jpaComentario.save(entity);
    }

    @Override
    public Optional<Comentario> buscarPorId(UUID id) {
        return jpaComentario.findById(id).map(ComentarioMapper::toDomain);
    }


    @Override
    public List<Comentario> buscarPorIdUsuario(UUID id) {
        return jpaComentario.findAllByAutorId(id).stream()
                .map(ComentarioMapper::toDomain)
                .toList();
    }

    @Override
    public List<Comentario> listarTodos() {
        return jpaComentario.findAll()
                .stream()
                .map(ComentarioMapper::toDomain)
                .toList();
    }

    @Override
    public void remover(UUID id) {
        jpaComentario.deleteById(id);
    }

}
