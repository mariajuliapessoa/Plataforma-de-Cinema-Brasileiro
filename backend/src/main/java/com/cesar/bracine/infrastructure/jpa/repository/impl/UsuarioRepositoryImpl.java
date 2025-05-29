package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringUsuarioJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryImpl
        extends RepositoryAbstratoImpl<Usuario, UsuarioEntity, UUID, SpringUsuarioJpaRepository>
        implements UsuarioRepository {

    public UsuarioRepositoryImpl(SpringUsuarioJpaRepository jpa) {
        super(jpa);
    }

    @Override
    protected void logEntityNotFound(UUID id) {
        System.out.println("Usuário com ID " + id + " não encontrado no banco.");
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpaRepository.findByEmail(email).map(this::mapToDomain);
    }

    @Override
    public Optional<Usuario> buscarPorNomeUsuario(String nomeUsuario) {
        return jpaRepository.findByNomeUsuario(nomeUsuario).map(this::mapToDomain);
    }

    @Override
    protected UsuarioEntity mapToEntity(Usuario usuario) {
        return UsuarioMapper.toEntity(usuario);
    }

    @Override
    protected Usuario mapToDomain(UsuarioEntity entity) {
        return UsuarioMapper.toDomain(entity);
    }
}
