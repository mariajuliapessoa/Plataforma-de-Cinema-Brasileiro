package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.DesafioRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DesafioApplicationService {

    private final DesafioRepository desafioRepository;
    private final UsuarioRepository usuarioRepository;

    public DesafioApplicationService(DesafioRepository desafioRepository, UsuarioRepository usuarioRepository) {
        this.desafioRepository = desafioRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // Criar novo desafio
    public Desafio criarDesafio(String titulo, String descricao, int pontos, UUID usuarioId, LocalDate prazo) {
        Usuario destinatario = usuarioRepository.buscarPorId(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        UsuarioId usuarioIdValueObject = new UsuarioId(destinatario.getId().getValue());

        Desafio desafio = new Desafio(titulo, descricao, pontos, usuarioIdValueObject, prazo);
        desafioRepository.salvar(desafio, destinatario);
        return desafio;
    }

    // Concluir desafio
    public void concluirDesafio(UUID desafioId) {
        Desafio desafio = desafioRepository.buscarPorId(desafioId)
                .orElseThrow(() -> new IllegalArgumentException("Desafio não encontrado"));

        Usuario usuario = usuarioRepository.buscarPorId(desafio.getDestinatario().getValue())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        desafio.concluir();
        usuario.adicionarPontos(desafio.getPontos());
        usuarioRepository.salvar(usuario); 
        desafioRepository.salvar(desafio, usuario); // persiste a alteração de status e pontos
    }

    // Listar desafios
    public List<Desafio> listarDesafios() {
        return desafioRepository.listarTodos();
    }

    public Optional<Desafio> buscarPorId(UUID id) {
        return desafioRepository.buscarPorId(id);
    }

    public List<Desafio> buscarPorIdUsuario(UUID id) {
        return desafioRepository.buscarPorUsuario(id);
    }

    // Remover desafio
    public void removerDesafio(UUID id) {
        desafioRepository.remover(id);
    }
}
