package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.domain.repositories.AvaliacaoRepository;
import com.cesar.bracine.infrastructure.jpa.entities.AvaliacaoEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringAvaliacaoJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.AvaliacaoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AvaliacaoRepositoryImpl
        extends RepositoryAbstratoImpl<Avaliacao, AvaliacaoEntity, UUID, SpringAvaliacaoJpaRepository>
        implements AvaliacaoRepository {

    public AvaliacaoRepositoryImpl(SpringAvaliacaoJpaRepository jpa) {
        super(jpa);
    }

    @Override
    protected AvaliacaoEntity mapToEntity(Avaliacao avaliacao) {
        return AvaliacaoMapper.toEntity(avaliacao);
    }

    @Override
    protected Avaliacao mapToDomain(AvaliacaoEntity entity) {
        return AvaliacaoMapper.toDomain(entity);
    }

    @Override
    protected void logEntityNotFound(UUID id) {
        System.out.println("Avaliação com ID " + id + " não encontrada no banco.");
    }

    @Override
    public List<Avaliacao> buscarPorIdFilme(UUID id) {
        return jpaRepository.findByFilmeId(id)
                .stream()
                .map(AvaliacaoMapper::toDomain)
                .toList();
    }

    @Override
    public List<Avaliacao> buscarPorIdUsuario(UUID id) {
        return jpaRepository.findByAutorId(id)
                .stream()
                .map(AvaliacaoMapper::toDomain)
                .toList();
    }
}
