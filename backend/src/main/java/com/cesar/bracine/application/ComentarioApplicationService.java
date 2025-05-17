package com.cesar.bracine.application;

import aj.org.objectweb.asm.commons.Remapper;
import com.cesar.bracine.domain.entities.Comentario;
import com.cesar.bracine.domain.entities.Debate;
import com.cesar.bracine.domain.entities.Filme;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.services.ComentarioService;
import com.cesar.bracine.domain.repositories.DebateRepository;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ComentarioApplicationService {

    private final ComentarioService comentarioService;
    private final UsuarioRepository usuarioRepository;
    private final FilmeRepository filmeRepository;
    private final DebateRepository debateRepository;

    public ComentarioApplicationService(ComentarioService comentarioService,
                                        UsuarioRepository usuarioRepository,
                                        FilmeRepository filmeRepository,
                                        DebateRepository debateRepository) {
        this.comentarioService = comentarioService;
        this.usuarioRepository = usuarioRepository;
        this.filmeRepository = filmeRepository;
        this.debateRepository = debateRepository;
    }

    public Comentario criarComentario(String texto, UUID autorId, UUID filmeId, UUID debateId) {
        Usuario autor = usuarioRepository.buscarPorId(autorId)
                .orElseThrow(() -> new EntityNotFoundException("Autor não encontrado"));

        Filme filme = filmeId != null ? filmeRepository.buscarPorId(filmeId).orElse(null) : null;
        Debate debate = debateId != null ? debateRepository.buscarPorId(debateId)
                .orElseThrow(() -> new EntityNotFoundException("Debate não encontrado")) : null;

        Comentario comentario = new Comentario(texto, autor, filme, debate);
        return comentarioService.salvarComentario(comentario);
    }

    public List<Comentario> listarComentarios() {
        return comentarioService.listarTodosComentarios();
    }

    public void removerComentario(UUID id) {
        comentarioService.removerComentario(id);
    }

    public Optional<Comentario> buscarPorId(UUID id) {
        return comentarioService.buscarComentarioPorId(id);
    }

    public List<Comentario> buscarPorIdUsuario(UUID id) {
        return comentarioService.buscarComentarioPorIdUsuario(id);
    }
}