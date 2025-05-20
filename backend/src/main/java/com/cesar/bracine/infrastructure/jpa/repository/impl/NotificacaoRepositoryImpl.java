package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.NotificacaoRepository;
import com.cesar.bracine.infrastructure.jpa.repository.SpringNotificacaoJpaRepository;
import com.cesar.bracine.infrastructure.mappers.NotificacaoMapper;
import com.cesar.bracine.infrastructure.mappers.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class NotificacaoRepositoryImpl implements NotificacaoRepository {

    private final SpringNotificacaoJpaRepository jpa;

    public NotificacaoRepositoryImpl(SpringNotificacaoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public void salvar(Notificacao notificacao, Usuario usuario) {
        jpa.save(NotificacaoMapper.toEntity(notificacao, usuario));
    }

    @Override
    public Optional<Notificacao> buscarPorId(UUID id) {
        return jpa.findById(id).map(NotificacaoMapper::toDomain);
    }

    @Override
    public List<Notificacao> buscarPorDestinatario(UUID usuarioId) {
        return jpa.findAllByDestinatarioId(usuarioId)
                .stream()
                .map(NotificacaoMapper::toDomain).toList();
    }

    @Override
    public List<Notificacao> listarTodos() {
        return jpa.findAll()
                .stream()
                .map(NotificacaoMapper::toDomain).toList();
    }

    @Override
    public void remover(UUID id) {
        jpa.deleteById(id);
    }
}
