package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.*;
import com.cesar.bracine.domain.services.ComentarioService;
import com.cesar.bracine.domain.repositories.DebateRepository;
import com.cesar.bracine.domain.repositories.FilmeRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.valueobjects.DebateId;
import com.cesar.bracine.domain.valueobjects.FilmeId;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
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

    public Comentario criarComentario(String texto, UUID usuarioId, UUID filmeId, UUID debateId, UUID comentarioPaiId) {
        if (usuarioRepository.buscarPorId(usuarioId).isEmpty()) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        if (filmeRepository.buscarPorId(filmeId).isEmpty()) {
            throw new IllegalArgumentException("Filme não encontrado");
        }

        if (debateRepository.buscarPorId(debateId).isEmpty()) {
            throw new IllegalArgumentException("Debate não encontrado");
        }

        UsuarioId autorIdValueObject = new UsuarioId(usuarioId);
        FilmeId filmeIdValueObject = new FilmeId(filmeId);
        DebateId debateIdValueObject = new DebateId(debateId);

        Comentario comentarioPai = null;
        if (comentarioPaiId != null) {
            Optional<Comentario> comentarioPaiOpt = comentarioService.buscarComentarioPorId(comentarioPaiId);
            if (comentarioPaiOpt.isEmpty()) {
                throw new IllegalArgumentException("Comentário pai não encontrado");
            }
            comentarioPai = comentarioPaiOpt.get();
        }

        Comentario comentario = new Comentario(texto, autorIdValueObject, filmeIdValueObject, debateIdValueObject, comentarioPai);
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