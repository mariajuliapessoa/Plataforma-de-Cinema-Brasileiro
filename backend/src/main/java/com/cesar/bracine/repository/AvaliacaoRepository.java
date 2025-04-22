package com.cesar.bracine.repository;

import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByFilmeId(Long filmeId);
    
    Optional<Avaliacao> findByFilmeIdAndUsuarioId(Long filmeId, String usuarioId);
    
    @Query("SELECT AVG(a.estrelas) FROM Avaliacao a WHERE a.filme.id = :filmeId")
    Double calcularMediaAvaliacoes(@Param("filmeId") Long filmeId);
    
    List<Avaliacao> findByFilmeIdOrderByDataHoraDesc(Long filmeId);
}
