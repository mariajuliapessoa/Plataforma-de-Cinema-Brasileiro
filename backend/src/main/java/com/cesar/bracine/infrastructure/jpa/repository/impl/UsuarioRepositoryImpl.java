package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.infrastructure.jpa.entities.UsuarioEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringUsuarioJpaRepository;
import com.cesar.bracine.infrastructure.mappers.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final SpringUsuarioJpaRepository jpa;

    public UsuarioRepositoryImpl(SpringUsuarioJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Usuario usuario) {
        jpa.save(UsuarioMapper.toEntity(usuario));
    }

    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        Optional<UsuarioEntity> entityOptional = jpa.findById(id);

        if (entityOptional.isEmpty()) {
            System.out.println("Usuário com ID " + id + " não encontrado no banco."); // ou logger
            return Optional.empty();
        }

        Usuario usuario = UsuarioMapper.toDomain(entityOptional.get());
        return Optional.of(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return jpa.findByEmail(email).map(UsuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorNomeUsuario(String nomeUsuario) {
        return jpa.findByNomeUsuario(nomeUsuario).map(UsuarioMapper::toDomain);
    }

    @Override
    public List<Usuario> listarTodos() {
        return jpa.findAll().stream().map(UsuarioMapper::toDomain).toList();
    }

    @Override
    public void deletar(UUID id) {
        jpa.deleteById(id);
    }
}
