package com.cesar.bracine.infrastructure.jpa.repository;

import com.cesar.bracine.infrastructure.entities.DebateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDebateJpaRepository extends JpaRepository<DebateEntity, UUID> {
}
