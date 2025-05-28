package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.DesafioRepository;
import com.cesar.bracine.infrastructure.jpa.entities.DesafioEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringDesafioJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.DesafioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DesafioRepositoryImpl
        extends RepositoryAbstratoImpl<Desafio, DesafioEntity, UUID, SpringDesafioJpaRepository>
        implements DesafioRepository {

    public DesafioRepositoryImpl(SpringDesafioJpaRepository jpa) {
        super(jpa);
    }

    @Override
    protected void logEntityNotFound(UUID id) {
        System.out.println("Desafio com ID " + id + " não encontrado no banco.");
    }

    @Override
    public void salvar(Desafio desafio, Usuario usuario) {
        jpaRepository.save(DesafioMapper.toEntity(desafio, usuario));
    }

    @Override
    public List<Desafio> buscarPorUsuario(UUID id) {
        return jpaRepository.findAllByDestinatarioId(id)
                .stream()
                .map(this::mapToDomain).toList();
    }

    @Override
    protected DesafioEntity mapToEntity(Desafio desafio) {
        return DesafioMapper.toEntity(desafio, null);
    }

    @Override
    protected Desafio mapToDomain(DesafioEntity entity) {
        return DesafioMapper.toDomain(entity);
    }
}
