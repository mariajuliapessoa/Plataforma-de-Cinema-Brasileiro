package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.DebateRepository;
import com.cesar.bracine.infrastructure.jpa.entities.DebateEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringDebateJpaRepository;

import com.cesar.bracine.infrastructure.jpa.repository.SpringDesafioJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.DebateMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class DebateRepositoryImpl
        extends RepositoryAbstratoImpl<Debate, DebateEntity, UUID, SpringDebateJpaRepository>
        implements DebateRepository {

    public DebateRepositoryImpl(SpringDebateJpaRepository jpa) {
        super(jpa);
    }


    @Override
    public void salvar(Debate debate, Usuario usuario) {

        DebateEntity entity = DebateMapper.toEntity(debate, usuario);
        jpaRepository.save(entity);
    }

    @Override
    protected DebateEntity mapToEntity(Debate debate) {
        return DebateMapper.toEntity(debate, null);
    }

    @Override
    protected Debate mapToDomain(DebateEntity entity) {
        return DebateMapper.toDomain(entity);
    }

}
