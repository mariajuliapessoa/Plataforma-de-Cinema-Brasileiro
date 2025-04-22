package com.cesar.bracine.service;

import com.cesar.bracine.exception.ResourceNotFoundException;
import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.repository.AvaliacaoRepository;
import com.cesar.bracine.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final FilmeRepository filmeRepository;

    @Autowired
    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, FilmeRepository filmeRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.filmeRepository = filmeRepository;
    }

    /**
     * Cria ou atualiza uma avaliação
     */
    @Transactional
    public Avaliacao salvarAvaliacao(Long filmeId, String comentario, Integer estrelas, User usuario) {
        if (comentario.length() < 20) {
            throw new IllegalArgumentException("O comentário deve ter pelo menos 20 caracteres");
        }

        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));

        Optional<Avaliacao> avaliacaoExistente = avaliacaoRepository.findByFilmeIdAndUsuarioId(filmeId, usuario.getId());

        Avaliacao avaliacao;
        if (avaliacaoExistente.isPresent()) {
            avaliacao = avaliacaoExistente.get();
            avaliacao.setComentario(comentario);
            avaliacao.setEstrelas(estrelas);
            avaliacao.setDataHora(LocalDateTime.now());
        } else {
            avaliacao = new Avaliacao(filme, usuario, estrelas, comentario);
        }

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);
        
        // Atualiza a média das avaliações do filme
        atualizarMediaFilme(filmeId);
        
        return avaliacaoSalva;
    }

    /**
     * Remove uma avaliação
     */
    @Transactional
    public void removerAvaliacao(Long avaliacaoId, String usuarioId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        if (!avaliacao.getUsuario().getId().equals(usuarioId)) {
            throw new IllegalArgumentException("Usuário não autorizado a remover esta avaliação");
        }

        Long filmeId = avaliacao.getFilme().getId();
        avaliacaoRepository.delete(avaliacao);
        
        // Atualiza a média do filme após remover a avaliação
        atualizarMediaFilme(filmeId);
    }

    /**
     * Busca todas as avaliações de um filme
     */
    public List<Avaliacao> buscarAvaliacoesPorFilme(Long filmeId) {
        return avaliacaoRepository.findByFilmeIdOrderByDataHoraDesc(filmeId);
    }

    /**
     * Adiciona uma curtida a um comentário
     */
    @Transactional
    public Avaliacao curtirComentario(Long avaliacaoId) {
        Avaliacao avaliacao = avaliacaoRepository.findById(avaliacaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada"));

        avaliacao.setCurtidas(avaliacao.getCurtidas() + 1);
        return avaliacaoRepository.save(avaliacao);
    }

    /**
     * Atualiza a média de avaliações de um filme
     */
    @Transactional
    public void atualizarMediaFilme(Long filmeId) {
        Double media = avaliacaoRepository.calcularMediaAvaliacoes(filmeId);
        
        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));
                
        filme.setNota(media != null ? media : 0.0);
        filmeRepository.save(filme);
    }
    
    /**
     * Busca a média de avaliações de um filme
     */
    public Double buscarMediaAvaliacoes(Long filmeId) {
        Double media = avaliacaoRepository.calcularMediaAvaliacoes(filmeId);
        return media != null ? media : 0.0;
    }
} 