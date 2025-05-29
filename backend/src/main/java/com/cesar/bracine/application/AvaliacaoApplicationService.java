package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.*;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.services.AvaliacaoService;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AvaliacaoApplicationService {

    private final AvaliacaoService avaliacaoService;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;

    public AvaliacaoApplicationService(AvaliacaoService avaliacaoService, UsuarioRepository usuarioRepository, FilmeRepository filmeRepository) {
        this.avaliacaoService = avaliacaoService;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
    }

    public Avaliacao criarAvaliacao(String texto, int nota, UUID usuarioId, UUID filmeId) {
       if(usuarioRepository.buscarPorId(usuarioId).isEmpty()) {
           throw new IllegalArgumentException("Usuário não encontrado");
       }

       if(filmeRepository.buscarPorId(filmeId).isEmpty()) {
           throw new IllegalArgumentException("Filme não encontrado");
       }

        UsuarioId autorIdValueObject = new UsuarioId(usuarioId);
        FilmeId filmeIdValueObject = new FilmeId(filmeId);

        Avaliacao avaliacao = new Avaliacao(texto, autorIdValueObject, filmeIdValueObject, nota);
        return avaliacaoService.salvarAvaliacao(avaliacao);
    }

    public Optional<Avaliacao> buscarPorId(UUID id) {
        return avaliacaoService.buscarAvaliacaoPorId(id);
    }

    public List<Avaliacao> listarPorFilme(UUID filmeId) {
        if (filmeRepository.buscarPorId(filmeId).isEmpty()) {
            throw new IllegalArgumentException("Filme não encontrado");
        }

        return avaliacaoService.listarPorFilmeId(filmeId);
    }

    public List<Avaliacao> listarAvaliacoes() {
        return avaliacaoService.listarTodasAvaliacoes();
    }

    public void remover(UUID id) {
        if ((usuarioRepository.buscarPorId(id).isEmpty())) {
            throw new IllegalArgumentException("Avaliacao não encontrada");
        }

        avaliacaoService.removerAvaliacao(id);
    }
}
