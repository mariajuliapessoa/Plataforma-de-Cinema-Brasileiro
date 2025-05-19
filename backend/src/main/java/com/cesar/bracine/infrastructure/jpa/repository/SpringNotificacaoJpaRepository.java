package com.cesar.bracine.infrastructure.jpa.repository;

import com.cesar.bracine.infrastructure.jpa.entities.NotificacaoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringNotificacaoJpaRepository extends JpaRepository<NotificacaoEntity, UUID> {
    List<NotificacaoEntity> findAllByDestinatarioId(UUID id);
}
