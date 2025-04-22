package com.cesar.bracine.repository;

import com.cesar.bracine.model.Desafio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesafioRepository extends JpaRepository<Desafio, Long> {
    
    List<Desafio> findByAtivoTrue();
    
    @Query("SELECT d FROM Desafio d WHERE d.ativo = true AND (d.dataFim IS NULL OR d.dataFim >= CURRENT_DATE)")
    List<Desafio> findDesafiosAtivos();
    
    Optional<Desafio> findByTitulo(String titulo);
} 