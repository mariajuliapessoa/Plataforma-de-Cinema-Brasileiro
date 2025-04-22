package com.cesar.bracine.service;

import com.cesar.bracine.model.Comentario;
import com.cesar.bracine.model.DenunciaComentario;
import com.cesar.bracine.model.Usuario;
import com.cesar.bracine.repository.ComentarioRepository;
import com.cesar.bracine.repository.DenunciaComentarioRepository;
import com.cesar.bracine.repository.UsuarioRepository;
import com.cesar.bracine.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DenunciaComentarioService {

    @Autowired
    private DenunciaComentarioRepository denunciaComentarioRepository;
    
    @Autowired
    private ComentarioRepository comentarioRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<DenunciaComentario> listarTodasDenuncias() {
        return denunciaComentarioRepository.findAll();
    }
    
    /**
     * Lista todas as denúncias não revisadas
     * @return Lista de denúncias não revisadas
     */
    public List<DenunciaComentario> listarDenunciasNaoRevisadas() {
        return denunciaComentarioRepository.findByRevisadaFalseOrderByDataDenunciaDesc();
    }
    
    /**
     * Lista todas as denúncias revisadas
     * @return Lista de denúncias revisadas
     */
    public List<DenunciaComentario> listarDenunciasRevisadas() {
        return denunciaComentarioRepository.findByRevisadaTrue();
    }
    
    /**
     * Lista denúncias revisadas filtrando por procedência
     * @param procedente Flag de procedência
     * @return Lista de denúncias revisadas e procedentes/improcedentes
     */
    public List<DenunciaComentario> listarDenunciasRevisadasPorProcedencia(Boolean procedente) {
        return denunciaComentarioRepository.findByRevisadaTrueAndProcedente(procedente);
    }
    
    /**
     * Lista denúncias para um comentário específico
     * @param comentarioId ID do comentário
     * @return Lista de denúncias para o comentário
     */
    public List<DenunciaComentario> listarDenunciasPorComentario(Long comentarioId) {
        return denunciaComentarioRepository.findByComentarioId(comentarioId);
    }
    
    /**
     * Lista denúncias feitas por um usuário específico, ordenadas por data
     * @param usuarioId ID do usuário
     * @return Lista de denúncias feitas pelo usuário, ordenadas por data
     */
    public List<DenunciaComentario> listarDenunciasPorUsuarioOrdenadas(Long usuarioId) {
        return denunciaComentarioRepository.findByUsuarioIdOrderByDataDenunciaDesc(usuarioId);
    }
    
    /**
     * Conta o número de denúncias não revisadas
     * @return Quantidade de denúncias não revisadas
     */
    public long contarDenunciasNaoRevisadas() {
        return denunciaComentarioRepository.countByRevisadaFalse();
    }
    
    /**
     * Busca uma denúncia de comentário pelo ID
     * @param id ID da denúncia
     * @return A denúncia encontrada
     * @throws ResourceNotFoundException se a denúncia não for encontrada
     */
    public DenunciaComentario buscarDenunciaPorId(Long id) {
        return denunciaComentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Denúncia de comentário não encontrada com o ID: " + id));
    }
    
    /**
     * Registra uma nova denúncia de comentário
     * @param denunciaComentario Dados da denúncia
     * @return Denúncia registrada
     */
    @Transactional
    public DenunciaComentario registrarDenuncia(DenunciaComentario denunciaComentario) {
        // Verificar se o comentário existe
        Comentario comentario = comentarioRepository.findById(denunciaComentario.getComentario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));
        
        // Verificar se o usuário existe
        Usuario usuario = usuarioRepository.findById(denunciaComentario.getUsuario().getId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        // Configurar referências
        denunciaComentario.setComentario(comentario);
        denunciaComentario.setUsuario(usuario);
        
        // Configurar valores padrão
        denunciaComentario.setDataDenuncia(LocalDateTime.now());
        denunciaComentario.setRevisada(false);
        
        return denunciaComentarioRepository.save(denunciaComentario);
    }
    
    /**
     * Revisa uma denúncia de comentário
     * @param id ID da denúncia
     * @param revisorId ID do revisor
     * @param procedente Flag de procedência
     * @param acaoTomada Descrição da ação tomada
     * @return Denúncia revisada
     */
    @Transactional
    public DenunciaComentario revisarDenuncia(Long id, Long revisorId, Boolean procedente, String acaoTomada) {
        // Buscar a denúncia
        DenunciaComentario denuncia = denunciaComentarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Denúncia não encontrada"));
        
        // Buscar o revisor
        Usuario revisor = usuarioRepository.findById(revisorId)
                .orElseThrow(() -> new EntityNotFoundException("Revisor não encontrado"));
        
        // Realizar a revisão
        denuncia.revisar(revisor, procedente, acaoTomada);
        
        return denunciaComentarioRepository.save(denuncia);
    }
    
    /**
     * Exclui uma denúncia
     * @param id ID da denúncia
     */
    @Transactional
    public void excluirDenuncia(Long id) {
        // Verificar se a denúncia existe
        if (!denunciaComentarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Denúncia não encontrada");
        }
        
        denunciaComentarioRepository.deleteById(id);
    }

    /**
     * Lista denúncias feitas por um usuário específico
     * @param usuarioId ID do usuário
     * @return Lista de denúncias feitas pelo usuário
     */
    public List<DenunciaComentario> listarDenunciasPorUsuario(String usuarioId) {
        return listarDenunciasPorUsuarioOrdenadas(Long.parseLong(usuarioId));
    }
} 