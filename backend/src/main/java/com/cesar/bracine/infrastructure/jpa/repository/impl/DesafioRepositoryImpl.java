package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.DesafioRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringDesafioJpaRepository;
import com.cesar.bracine.infrastructure.mappers.DesafioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class DesafioRepositoryImpl implements DesafioRepository {

    private final SpringDesafioJpaRepository jpa;

    public DesafioRepositoryImpl(SpringDesafioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Desafio desafio, Usuario usuario) {
        jpa.save(DesafioMapper.toEntity(desafio, usuario));
    }

    @Override
    public Optional<Desafio> buscarPorId(UUID id) {
        return jpa.findById(id).map(DesafioMapper::toDomain);
    }

    @Override
    public List<Desafio> listarTodos() {
        return jpa.findAll().stream()
                .map(DesafioMapper::toDomain)
                .toList();
    }

    @Override
    public List<Desafio> buscarPorUsuario(UUID id) {
        return jpa.findAllByDestinatarioId(id)
                .stream()
                .map(DesafioMapper::toDomain).toList();
    }

    @Override
    public void remover(UUID id) {
        jpa.deleteById(id);
    }
}
