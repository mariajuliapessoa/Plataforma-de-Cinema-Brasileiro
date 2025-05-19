package com.cesar.bracine.application;

import com.cesar.bracine.domain.entities.Desafio;
import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.repositories.DesafioRepository;
import com.cesar.bracine.domain.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
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
    public Desafio criarDesafio(String titulo, String descricao, int pontos, UUID destinatarioId, LocalDate prazo) {
        Usuario destinatario = usuarioRepository.buscarPorId(destinatarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Desafio desafio = new Desafio(titulo, descricao, pontos, destinatario, prazo);
        desafioRepository.salvar(desafio);
        return desafio;
    }

    // Concluir desafio
    public void concluirDesafio(UUID desafioId) {
        Desafio desafio = desafioRepository.buscarPorId(desafioId)
                .orElseThrow(() -> new IllegalArgumentException("Desafio não encontrado"));

        desafio.concluir();
        desafioRepository.salvar(desafio); // persiste a alteração de status e pontos
    }

    // Listar desafios
    public List<Desafio> listarDesafios() {
        return desafioRepository.listarTodos();
    }

    // Remover desafio
    public void removerDesafio(UUID id) {
        desafioRepository.remover(id);
    }
}
