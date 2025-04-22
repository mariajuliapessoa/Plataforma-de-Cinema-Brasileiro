package com.cesar.bracine.repository;

import com.cesar.bracine.model.ItemLista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemListaRepository extends JpaRepository<ItemLista, Long> {
    
    List<ItemLista> findByListaIdOrderByVotosDesc(Long listaId);
    
    Optional<ItemLista> findByListaIdAndFilmeId(Long listaId, Long filmeId);
} 