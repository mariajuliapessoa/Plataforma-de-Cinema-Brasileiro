package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Avaliacao;
import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.services.AvaliacaoService;
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
        Usuario autor = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Filme filme = filmeRepository.buscarPorId(filmeId)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado"));

        Avaliacao avaliacao = new Avaliacao(texto, autor, filme, nota);
        return avaliacaoService.salvarAvaliacao(avaliacao);
    }

    public Optional<Avaliacao> buscarPorId(UUID id) {
        return avaliacaoService.buscarAvaliacaoPorId(id);
    }

    public List<Avaliacao> listarAvaliacoes() {
        return avaliacaoService.listarTodasAvaliacoes();
    }

    public void remover(UUID id) {
        avaliacaoService.removerAvaliacao(id);
    }
}
