package com.cesar.bracine.repository;

import com.cesar.bracine.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    
    List<Notificacao> findByUsuarioIdOrderByDataHoraDesc(String usuarioId);
    
    List<Notificacao> findByUsuarioIdAndLidaFalseOrderByDataHoraDesc(String usuarioId);
    
    List<Notificacao> findByDesafioIdAndTipo(Long desafioId, String tipo);
} 