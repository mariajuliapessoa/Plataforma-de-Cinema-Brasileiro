package com.cesar.bracine.service;

import com.cesar.bracine.exception.ResourceNotFoundException;
import com.cesar.bracine.model.*;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.model.user.UserRole;
import com.cesar.bracine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DesafioService {

    private final DesafioRepository desafioRepository;
    private final DesafioParticipanteRepository desafioParticipanteRepository;
    private final FilmeRepository filmeRepository;
    private final UserRepository userRepository;
    private final NotificacaoRepository notificacaoRepository;

    @Autowired
    public DesafioService(
            DesafioRepository desafioRepository,
            DesafioParticipanteRepository desafioParticipanteRepository,
            FilmeRepository filmeRepository,
            UserRepository userRepository,
            NotificacaoRepository notificacaoRepository) {
        this.desafioRepository = desafioRepository;
        this.desafioParticipanteRepository = desafioParticipanteRepository;
        this.filmeRepository = filmeRepository;
        this.userRepository = userRepository;
        this.notificacaoRepository = notificacaoRepository;
    }

    /**
     * Retorna todos os desafios ativos
     */
    public List<Desafio> buscarDesafiosAtivos() {
        return desafioRepository.findDesafiosAtivos();
    }

    /**
     * Retorna um desafio específico por ID
     */
    public Desafio buscarPorId(Long desafioId) {
        return desafioRepository.findById(desafioId)
                .orElseThrow(() -> new ResourceNotFoundException("Desafio não encontrado"));
    }

    /**
     * Busca um desafio pelo título
     */
    public Desafio buscarPorTitulo(String titulo) {
        return desafioRepository.findByTitulo(titulo)
                .orElseThrow(() -> new ResourceNotFoundException("Desafio não encontrado"));
    }

    /**
     * Cria um novo desafio (apenas para administradores)
     */
    @Transactional
    public Desafio criarDesafio(String titulo, String descricao, User criador, 
                            LocalDate dataFim, List<Long> filmesIds, 
                            int pontosConquista, String premioDesafio) {
        
        if (!criador.getRole().equals(UserRole.ADMIN)) {
            throw new IllegalArgumentException("Apenas administradores podem criar desafios");
        }
        
        Desafio novoDesafio = new Desafio(titulo, descricao, criador, dataFim, pontosConquista, premioDesafio);
        
        // Adicionar filmes ao desafio
        if (filmesIds != null && !filmesIds.isEmpty()) {
            Set<Filme> filmes = filmesIds.stream()
                    .map(id -> filmeRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado: " + id)))
                    .collect(Collectors.toSet());
            novoDesafio.setFilmes(filmes);
        }
        
        Desafio desafioSalvo = desafioRepository.save(novoDesafio);
        
        // Enviar notificação para todos os usuários sobre o novo desafio
        List<User> usuarios = userRepository.findAll();
        for (User usuario : usuarios) {
            Notificacao notificacao = new Notificacao(
                    usuario,
                    "Novo desafio disponível: " + titulo,
                    "NOVO_DESAFIO",
                    desafioSalvo
            );
            notificacaoRepository.save(notificacao);
        }
        
        return desafioSalvo;
    }

    /**
     * Participar de um desafio
     */
    @Transactional
    public DesafioParticipante participarDesafio(Long desafioId, User usuario) {
        Desafio desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new ResourceNotFoundException("Desafio não encontrado"));
        
        if (!desafio.isDesafioAtivo()) {
            throw new IllegalArgumentException("Este desafio não está mais ativo");
        }
        
        Optional<DesafioParticipante> participanteExistente = 
                desafioParticipanteRepository.findByDesafioIdAndUsuarioId(desafioId, usuario.getId());
                
        if (participanteExistente.isPresent()) {
            return participanteExistente.get();
        }
        
        DesafioParticipante novoParticipante = new DesafioParticipante(desafio, usuario);
        return desafioParticipanteRepository.save(novoParticipante);
    }

    /**
     * Desistir de um desafio
     */
    @Transactional
    public void desistirDesafio(Long desafioId, User usuario) {
        DesafioParticipante participante = desafioParticipanteRepository
                .findByDesafioIdAndUsuarioId(desafioId, usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Você não está participando deste desafio"));
        
        desafioParticipanteRepository.delete(participante);
    }

    /**
     * Marcar um filme como visto em um desafio
     */
    @Transactional
    public DesafioParticipante marcarFilmeComoVisto(Long desafioId, Long filmeId, User usuario) {
        Desafio desafio = desafioRepository.findById(desafioId)
                .orElseThrow(() -> new ResourceNotFoundException("Desafio não encontrado"));
        
        if (!desafio.isDesafioAtivo()) {
            throw new IllegalArgumentException("Este desafio não está mais ativo");
        }
        
        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));
        
        if (!desafio.getFilmes().contains(filme)) {
            throw new IllegalArgumentException("Este filme não faz parte do desafio");
        }
        
        DesafioParticipante participante = desafioParticipanteRepository
                .findByDesafioIdAndUsuarioId(desafioId, usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Você não está participando deste desafio"));
        
        participante.marcarFilmeComoVisto(filme);
        
        DesafioParticipante participanteAtualizado = desafioParticipanteRepository.save(participante);
        
        // Verificar se o participante concluiu o desafio e criar notificação
        if (participanteAtualizado.isConcluido() && participanteAtualizado.isConquistaDesbloqueada()) {
            Notificacao notificacao = new Notificacao(
                    usuario,
                    "Parabéns! Você concluiu o desafio '" + desafio.getTitulo() + "' e ganhou uma conquista!",
                    "CONQUISTA_DESBLOQUEADA",
                    desafio
            );
            notificacaoRepository.save(notificacao);
        }
        
        return participanteAtualizado;
    }

    /**
     * Buscar os desafios que o usuário está participando
     */
    public List<DesafioParticipante> buscarDesafiosDoUsuario(String usuarioId) {
        return desafioParticipanteRepository.findByUsuarioIdOrderByDataInscricaoDesc(usuarioId);
    }

    /**
     * Buscar os desafios ativos que o usuário está participando
     */
    public List<DesafioParticipante> buscarDesafiosAtivosDoUsuario(String usuarioId) {
        return desafioParticipanteRepository.findDesafiosAtivosDoUsuario(usuarioId);
    }

    /**
     * Buscar o ranking de um desafio
     */
    public List<Map<String, Object>> buscarRankingDesafio(Long desafioId) {
        List<DesafioParticipante> participantes = 
                desafioParticipanteRepository.findByDesafioIdOrderByFilmesVistosSize(desafioId);
        
        // Para cada participante, calcular a posição e retornar as informações
        return participantes.stream()
                .map(p -> {
                    Integer posicao = desafioParticipanteRepository
                            .getPosicaoNoRanking(desafioId, p.getUsuario().getId());
                    
                    return Map.of(
                            "posicao", posicao + 1,
                            "usuario", p.getUsuario().getUsername(),
                            "progresso", p.getProgresso(),
                            "filmesVistos", p.getFilmesVistos().size(),
                            "concluido", p.isConcluido(),
                            "dataConclusao", p.getDataConclusao()
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * Buscar notificações não lidas do usuário
     */
    public List<Notificacao> buscarNotificacoesNaoLidas(String usuarioId) {
        return notificacaoRepository.findByUsuarioIdAndLidaFalseOrderByDataHoraDesc(usuarioId);
    }

    /**
     * Marcar notificação como lida
     */
    @Transactional
    public Notificacao marcarNotificacaoComoLida(Long notificacaoId, User usuario) {
        Notificacao notificacao = notificacaoRepository.findById(notificacaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Notificação não encontrada"));
        
        if (!notificacao.getUsuario().getId().equals(usuario.getId())) {
            throw new IllegalArgumentException("Esta notificação não pertence a você");
        }
        
        notificacao.setLida(true);
        return notificacaoRepository.save(notificacao);
    }
} 