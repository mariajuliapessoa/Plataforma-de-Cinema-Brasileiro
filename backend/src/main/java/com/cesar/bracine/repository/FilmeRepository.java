package com.cesar.bracine.repository;

import com.cesar.bracine.model.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Long> {
    @Query("SELECT f FROM Filme f WHERE LOWER(f.titulo) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(f.descricao) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(f.diretor) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(f.genero) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Filme> buscarPorTermo(@Param("termo") String termo);

    List<Filme> findByGeneroIgnoreCase(String genero);

    List<Filme> findByTituloContainingIgnoreCaseOrDescricaoContainingIgnoreCase(String titulo, String descricao);

    @Query("SELECT f FROM Filme f WHERE f.ano = :ano AND f.nota >= :notaMinima")
    List<Filme> buscarPorAnoENota(@Param("ano") Integer ano, @Param("notaMinima") Double notaMinima);

    Optional<Filme> findByTitulo(String titulo);
}
