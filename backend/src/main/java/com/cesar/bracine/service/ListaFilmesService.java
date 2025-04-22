package com.cesar.bracine.service;

import com.cesar.bracine.exception.ResourceNotFoundException;
import com.cesar.bracine.model.*;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ListaFilmesService {

    private final ListaFilmesRepository listaFilmesRepository;
    private final ItemListaRepository itemListaRepository;
    private final FilmeRepository filmeRepository;
    private final UserRepository userRepository;
    private final ComentarioListaRepository comentarioListaRepository;

    @Autowired
    public ListaFilmesService(
            ListaFilmesRepository listaFilmesRepository,
            ItemListaRepository itemListaRepository,
            FilmeRepository filmeRepository,
            UserRepository userRepository,
            ComentarioListaRepository comentarioListaRepository) {
        this.listaFilmesRepository = listaFilmesRepository;
        this.itemListaRepository = itemListaRepository;
        this.filmeRepository = filmeRepository;
        this.userRepository = userRepository;
        this.comentarioListaRepository = comentarioListaRepository;
    }

    /**
     * Retorna todas as listas públicas
     */
    public List<ListaFilmes> buscarListasPublicas() {
        return listaFilmesRepository.findByPublicaTrue();
    }

    /**
     * Retorna todas as listas que o usuário tem acesso
     */
    public List<ListaFilmes> buscarListasAcessiveis(String usuarioId) {
        return listaFilmesRepository.findListasAcessiveis(usuarioId);
    }

    /**
     * Busca uma lista específica por ID
     */
    public ListaFilmes buscarPorId(Long listaId, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.podeVisualizar(usuario)) {
            throw new IllegalArgumentException("Você não tem permissão para acessar esta lista");
        }

        return lista;
    }

    /**
     * Cria uma nova lista de filmes
     */
    @Transactional
    public ListaFilmes criarLista(String nome, String descricao, User criador, boolean publica, boolean colaborativa) {
        ListaFilmes novaLista = new ListaFilmes(nome, descricao, criador, publica, colaborativa);
        return listaFilmesRepository.save(novaLista);
    }

    /**
     * Adiciona um filme a uma lista
     */
    @Transactional
    public ItemLista adicionarFilmeALista(Long listaId, Long filmeId, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.podeEditar(usuario)) {
            throw new IllegalArgumentException("Você não tem permissão para editar esta lista");
        }

        Filme filme = filmeRepository.findById(filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado"));

        // Verifica se o filme já está na lista
        Optional<ItemLista> itemExistente = itemListaRepository.findByListaIdAndFilmeId(listaId, filmeId);
        if (itemExistente.isPresent()) {
            return itemExistente.get();
        }

        ItemLista novoItem = new ItemLista(lista, filme, usuario);
        return itemListaRepository.save(novoItem);
    }

    /**
     * Adiciona um colaborador à lista
     */
    @Transactional
    public ListaFilmes adicionarColaborador(Long listaId, String colaboradorId, User usuarioAtual) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.getCriador().equals(usuarioAtual)) {
            throw new IllegalArgumentException("Apenas o criador pode adicionar colaboradores");
        }

        if (!lista.isColaborativa()) {
            throw new IllegalArgumentException("Esta lista não é colaborativa");
        }

        User colaborador = userRepository.findById(colaboradorId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        lista.getColaboradores().add(colaborador);
        return listaFilmesRepository.save(lista);
    }

    /**
     * Registra voto em um filme da lista
     */
    @Transactional
    public ItemLista votarEmItemLista(Long itemId, User usuario) {
        ItemLista item = itemListaRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        ListaFilmes lista = item.getLista();
        if (!lista.isPermiteVotacao()) {
            throw new IllegalArgumentException("Esta lista não permite votação");
        }

        item.adicionarVoto(usuario);
        return itemListaRepository.save(item);
    }

    /**
     * Remove voto de um filme da lista
     */
    @Transactional
    public ItemLista removerVotoEmItemLista(Long itemId, User usuario) {
        ItemLista item = itemListaRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item não encontrado"));

        item.removerVoto(usuario);
        return itemListaRepository.save(item);
    }

    /**
     * Adiciona um comentário à lista
     */
    @Transactional
    public ComentarioLista adicionarComentario(Long listaId, String texto, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.podeVisualizar(usuario)) {
            throw new IllegalArgumentException("Você não tem permissão para comentar nesta lista");
        }

        ComentarioLista comentario = new ComentarioLista(lista, usuario, texto);
        return comentarioListaRepository.save(comentario);
    }

    /**
     * Busca os comentários de uma lista
     */
    public List<ComentarioLista> buscarComentarios(Long listaId, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.podeVisualizar(usuario)) {
            throw new IllegalArgumentException("Você não tem permissão para acessar esta lista");
        }

        return comentarioListaRepository.findByListaIdOrderByDataHoraDesc(listaId);
    }

    /**
     * Atualiza as configurações de uma lista
     */
    @Transactional
    public ListaFilmes atualizarLista(Long listaId, String nome, String descricao, 
                                  boolean publica, boolean colaborativa, 
                                  boolean permiteVotacao, User usuarioAtual) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.getCriador().equals(usuarioAtual)) {
            throw new IllegalArgumentException("Apenas o criador pode alterar as configurações da lista");
        }

        if (nome != null) lista.setNome(nome);
        if (descricao != null) lista.setDescricao(descricao);
        lista.setPublica(publica);
        lista.setColaborativa(colaborativa);
        lista.setPermiteVotacao(permiteVotacao);

        return listaFilmesRepository.save(lista);
    }

    /**
     * Remove um filme de uma lista
     */
    @Transactional
    public void removerFilmeDaLista(Long listaId, Long filmeId, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.podeEditar(usuario)) {
            throw new IllegalArgumentException("Você não tem permissão para editar esta lista");
        }

        ItemLista item = itemListaRepository.findByListaIdAndFilmeId(listaId, filmeId)
                .orElseThrow(() -> new ResourceNotFoundException("Filme não encontrado na lista"));

        itemListaRepository.delete(item);
    }

    /**
     * Exclui uma lista de filmes
     */
    @Transactional
    public void excluirLista(Long listaId, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.getCriador().equals(usuario)) {
            throw new IllegalArgumentException("Apenas o criador pode excluir a lista");
        }

        listaFilmesRepository.delete(lista);
    }
    
    /**
     * Busca os itens de uma lista ordenados por votos
     */
    public List<ItemLista> buscarItensDaLista(Long listaId, User usuario) {
        ListaFilmes lista = listaFilmesRepository.findById(listaId)
                .orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        if (!lista.podeVisualizar(usuario)) {
            throw new IllegalArgumentException("Você não tem permissão para acessar esta lista");
        }

        return itemListaRepository.findByListaIdOrderByVotosDesc(listaId);
    }
} 