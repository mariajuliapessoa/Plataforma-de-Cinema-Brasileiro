package com.cesar.bracine.repository;

import com.cesar.bracine.model.DesafioParticipante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesafioParticipanteRepository extends JpaRepository<DesafioParticipante, Long> {
    
    List<DesafioParticipante> findByUsuarioIdOrderByDataInscricaoDesc(String usuarioId);
    
    List<DesafioParticipante> findByDesafioIdOrderByFilmesVistosSize(Long desafioId);
    
    Optional<DesafioParticipante> findByDesafioIdAndUsuarioId(Long desafioId, String usuarioId);
    
    @Query("SELECT dp FROM DesafioParticipante dp JOIN dp.desafio d WHERE d.ativo = true AND dp.usuario.id = :usuarioId")
    List<DesafioParticipante> findDesafiosAtivosDoUsuario(@Param("usuarioId") String usuarioId);
    
    @Query("SELECT COUNT(dp) FROM DesafioParticipante dp WHERE dp.desafio.id = :desafioId AND dp.filmesVistos.size > (SELECT dp2.filmesVistos.size FROM DesafioParticipante dp2 WHERE dp2.desafio.id = :desafioId AND dp2.usuario.id = :usuarioId)")
    Integer getPosicaoNoRanking(@Param("desafioId") Long desafioId, @Param("usuarioId") String usuarioId);
} 