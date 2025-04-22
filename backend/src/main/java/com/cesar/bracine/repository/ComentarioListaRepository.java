package com.cesar.bracine.repository;

import com.cesar.bracine.model.ComentarioLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioListaRepository extends JpaRepository<ComentarioLista, Long> {
    
    List<ComentarioLista> findByListaIdOrderByDataHoraDesc(Long listaId);
} 