package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.domain.repositories.AvaliacaoRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringAvaliacaoJpaRepository;
import com.cesar.bracine.infrastructure.mappers.AvaliacaoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AvaliacaoRepositoryImpl implements AvaliacaoRepository {

    private final SpringAvaliacaoJpaRepository jpa;

    public AvaliacaoRepositoryImpl(SpringAvaliacaoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Avaliacao avaliacao) {
        jpa.save(AvaliacaoMapper.toEntity(avaliacao));
    }

    @Override
    public Optional<Avaliacao> buscarPorId(UUID id) {
        return jpa.findById(id).map(AvaliacaoMapper::toDomain);
    }

    @Override
    public List<Avaliacao> listarTodos() {
        return jpa.findAll().stream()
                .map(AvaliacaoMapper::toDomain)
                .toList();
    }

    @Override
    public void remover(UUID id) {
        jpa.deleteById(id);
    }
}
