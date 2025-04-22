package com.cesar.bracine.repository;

import com.cesar.bracine.model.DenunciaComentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DenunciaComentarioRepository extends JpaRepository<DenunciaComentario, Long> {
    
    /**
     * Busca denúncias por ID do comentário
     * @param comentarioId ID do comentário
     * @return Lista de denúncias para o comentário
     */
    List<DenunciaComentario> findByComentarioId(Long comentarioId);
    
    /**
     * Busca denúncias por ID do usuário denunciante
     * @param usuarioId ID do usuário denunciante
     * @return Lista de denúncias feitas pelo usuário
     */
    List<DenunciaComentario> findByUsuarioId(Long usuarioId);
    
    /**
     * Busca denúncias feitas por um usuário específico, ordenadas por data de denúncia (mais recentes primeiro)
     * @param usuarioId ID do usuário que fez as denúncias
     * @return Lista de denúncias feitas pelo usuário ordenadas por data
     */
    List<DenunciaComentario> findByUsuarioIdOrderByDataDenunciaDesc(Long usuarioId);
    
    /**
     * Busca denúncias não revisadas, ordenadas por data (mais recentes primeiro)
     * @return Lista de denúncias não revisadas
     */
    List<DenunciaComentario> findByRevisadaFalseOrderByDataDenunciaDesc();
    
    /**
     * Busca denúncias revisadas
     * @return Lista de denúncias revisadas
     */
    List<DenunciaComentario> findByRevisadaTrue();
    
    /**
     * Busca denúncias revisadas por procedência
     * @param procedente Flag de procedência
     * @return Lista de denúncias revisadas e procedentes/improcedentes
     */
    List<DenunciaComentario> findByRevisadaTrueAndProcedente(Boolean procedente);
    
    /**
     * Conta denúncias não revisadas
     * @return Número de denúncias não revisadas
     */
    long countByRevisadaFalse();

    /**
     * Busca denúncias feitas por um usuário específico, ordenadas pela data mais recente
     * @param usuarioId ID do usuário
     * @return Lista de denúncias
     */
    List<DenunciaComentario> findByUsuarioIdOrderByDataDenunciaDesc(Long usuarioId);

    /**
     * Busca denúncias relacionadas a um comentário específico
     * @param comentarioId ID do comentário
     * @return Lista de denúncias
     */
    List<DenunciaComentario> findByComentarioId(Long comentarioId);

    /**
     * Busca denúncias não revisadas, ordenadas pela data mais antiga primeiro
     * @return Lista de denúncias não revisadas
     */
    List<DenunciaComentario> findByRevisadaOrderByDataDenunciaAsc(boolean revisada);

    /**
     * Método conveniente para buscar denúncias não revisadas
     * @return Lista de denúncias não revisadas
     */
    default List<DenunciaComentario> findAllNaoRevisadas() {
        return findByRevisadaOrderByDataDenunciaAsc(false);
    }

    /**
     * Busca denúncias por motivo
     * @param motivo Motivo da denúncia
     * @return Lista de denúncias com o motivo especificado
     */
    List<DenunciaComentario> findByMotivo(String motivo);

    /**
     * Busca denúncias por status de revisão
     * @param revisada Status de revisão
     * @return Lista de denúncias com o status especificado
     */
    List<DenunciaComentario> findByRevisada(boolean revisada);

    /**
     * Conta denúncias por procedência
     * @param procedente Status de procedência
     * @return Número de denúncias
     */
    long countByProcedente(boolean procedente);

    /**
     * Busca motivos distintos de denúncias
     * @return Lista de motivos distintos
     */
    @Query("SELECT DISTINCT d.motivo FROM DenunciaComentario d")
    List<String> findDistinctMotivos();

    /**
     * Conta denúncias não revisadas
     * @return Número de denúncias não revisadas
     */
    long countByRevisada(boolean revisada);
} 