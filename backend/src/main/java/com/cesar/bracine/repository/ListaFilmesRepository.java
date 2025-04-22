package com.cesar.bracine.repository;

import com.cesar.bracine.model.ListaFilmes;
import com.cesar.bracine.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ListaFilmesRepository extends JpaRepository<ListaFilmes, Long> {
    
    List<ListaFilmes> findByPublicaTrue();
    
    List<ListaFilmes> findByCriadorId(String criadorId);
    
    @Query("SELECT l FROM ListaFilmes l WHERE l.publica = true OR l.criador.id = :usuarioId OR :usuarioId IN (SELECT c.id FROM l.colaboradores c)")
    List<ListaFilmes> findListasAcessiveis(@Param("usuarioId") String usuarioId);
    
    Optional<ListaFilmes> findByNome(String nome);
} 