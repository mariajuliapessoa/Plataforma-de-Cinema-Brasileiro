package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.repositories.DebateRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringDebateJpaRepository;
import com.cesar.bracine.infrastructure.entities.DebateEntity;
import com.cesar.bracine.infrastructure.mappers.DebateMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DebateRepositoryImpl implements DebateRepository {

    private final SpringDebateJpaRepository jpa;

    public DebateRepositoryImpl(SpringDebateJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Debate debate) {
        DebateEntity entity = DebateMapper.toEntity(debate);
        jpa.save(entity);
    }

    @Override
    public Optional<Debate> buscarPorId(UUID id) {
        return jpa.findById(id)
                .map(DebateMapper::toDomain);
    }

    @Override
    public List<Debate> listarTodos() {
        return jpa.findAll().stream()
                .map(DebateMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void remover(UUID id) {
        jpa.deleteById(id);
    }
}
