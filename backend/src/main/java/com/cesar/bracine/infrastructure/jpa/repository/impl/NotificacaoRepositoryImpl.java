package com.cesar.bracine.infrastructure.jpa.repository.impl;

import com.cesar.bracine.domain.entities.Notificacao;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.NotificacaoRepository;
import com.cesar.bracine.infrastructure.jpa.entities.NotificacaoEntity;
import com.cesar.bracine.infrastructure.jpa.repository.SpringNotificacaoJpaRepository;
import com.cesar.bracine.infrastructure.jpa.repository.template.RepositoryAbstratoImpl;
import com.cesar.bracine.infrastructure.mappers.NotificacaoMapper;
import com.cesar.bracine.infrastructure.mappers.UsuarioMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class NotificacaoRepositoryImpl
        extends RepositoryAbstratoImpl<Notificacao, NotificacaoEntity, UUID, SpringNotificacaoJpaRepository>
        implements NotificacaoRepository {

    public NotificacaoRepositoryImpl(SpringNotificacaoJpaRepository jpa) {
        super(jpa);
    }

    @Override
    public void salvar(Notificacao notificacao, Usuario usuario) {
        jpaRepository.save(NotificacaoMapper.toEntity(notificacao, usuario));
    }

    @Override
    public Optional<Notificacao> buscarPorId(UUID id) {
        return jpaRepository.findById(id).map(NotificacaoMapper::toDomain);
    }

    @Override
    public List<Notificacao> buscarPorDestinatario(UUID usuarioId) {
        return jpaRepository.findAllByDestinatarioId(usuarioId)
                .stream()
                .map(NotificacaoMapper::toDomain).toList();
    }

    @Override
    public List<Notificacao> listarTodos() {
        return jpaRepository.findAll()
                .stream()
                .map(NotificacaoMapper::toDomain).toList();
    }

    @Override
    public void remover(UUID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    protected NotificacaoEntity mapToEntity(Notificacao notificacao) {
        return NotificacaoMapper.toEntity(notificacao, null);
    }

    @Override
    protected Notificacao mapToDomain(NotificacaoEntity entity) {
        return NotificacaoMapper.toDomain(entity);
    }
}
