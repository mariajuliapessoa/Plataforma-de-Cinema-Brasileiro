package com.cesar.bracine.domain.services;

import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.domain.repositories.AvaliacaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    // Criar ou Editar uma avaliação
    public Avaliacao salvarAvaliacao(Avaliacao avaliacao) {
        avaliacaoRepository.salvar(avaliacao);
        return avaliacao;
    }

    // Buscar avaliação por ID
    public Optional<Avaliacao> buscarAvaliacaoPorId(UUID id) {
        return avaliacaoRepository.buscarPorId(id);
    }

    // Listar todas as avaliações
    public List<Avaliacao> listarTodasAvaliacoes() {
        return avaliacaoRepository.listarTodos();
    }

    public List<Avaliacao> listarPorFilmeId(UUID id) {
        return avaliacaoRepository.buscarPorIdFilme(id);
    }

    public List<Avaliacao> listarPorUsuarioId(UUID id) {
        return avaliacaoRepository.buscarPorIdUsuario(id);
    }

    // Remover uma avaliação
    public void removerAvaliacao(UUID id) {
        avaliacaoRepository.remover(id);
    }
}