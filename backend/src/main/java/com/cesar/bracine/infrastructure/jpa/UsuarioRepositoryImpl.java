package com.cesar.bracine.infrastructure.jpa;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
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
        return jpa.findById(id).map(UsuarioMapper::toDomain);
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
