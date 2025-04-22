package com.cesar.bracine.steps;

import com.cesar.bracine.model.*;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.model.user.UserRole;
import com.cesar.bracine.repository.*;
import com.cesar.bracine.service.ListaFilmesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ListaFilmesSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListaFilmesRepository listaFilmesRepository;

    @Autowired
    private ItemListaRepository itemListaRepository;

    @Autowired
    private ComentarioListaRepository comentarioListaRepository;

    @Autowired
    private ListaFilmesService listaFilmesService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private User usuarioTeste;
    private User outroUsuario;
    private Filme filme;
    private ListaFilmes lista;
    private ResultActions resultActions;
    private MockHttpServletResponse response;
    private ItemLista itemLista;

    @Before
    public void setUp() {
        comentarioListaRepository.deleteAll();
        itemListaRepository.deleteAll();
        listaFilmesRepository.deleteAll();
        filmeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Dado("que existe um usuário cadastrado com o username {string}")
    public void queExisteUmUsuarioCadastradoComOUsername(String username) {
        usuarioTeste = new User();
        usuarioTeste.setUsername(username);
        usuarioTeste.setEmail(username + "@email.com");
        usuarioTeste.setPassword(passwordEncoder.encode("senha123"));
        usuarioTeste.setRole(UserRole.USER);
        userRepository.save(usuarioTeste);
    }

    @E("que existe outro usuário cadastrado")
    public void queExisteOutroUsuarioCadastrado() {
        outroUsuario = new User();
        outroUsuario.setUsername("outro_usuario");
        outroUsuario.setEmail("outro_usuario@email.com");
        outroUsuario.setPassword(passwordEncoder.encode("senha123"));
        outroUsuario.setRole(UserRole.USER);
        userRepository.save(outroUsuario);
    }

    @Dado("que existe um filme {string} cadastrado no sistema")
    public void queExisteUmFilmeCadastradoNoSistema(String titulo) {
        filme = new Filme();
        filme.setTitulo(titulo);
        filme.setDescricao("Descrição do filme " + titulo);
        filme.setGenero("Drama");
        filme.setDiretor("Kleber Mendonça Filho");
        filme.setAno(2019);
        filme.setNota(4.5);
        filmeRepository.save(filme);
    }

    @Dado("que o usuário está autenticado")
    public void queOUsuarioEstaAutenticado() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                usuarioTeste, null, usuarioTeste.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Dado("que existe uma lista pública chamada {string}")
    public void queExisteUmaListaPublicaChamada(String nomeLista) {
        lista = new ListaFilmes();
        lista.setNome(nomeLista);
        lista.setDescricao("Lista de filmes do sertão brasileiro");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        lista.setColaborativa(false);
        listaFilmesRepository.save(lista);
        
        // Adicionar filmes à lista
        ItemLista item = new ItemLista(lista, filme, usuarioTeste);
        itemListaRepository.save(item);
    }

    @Quando("o usuário acessa a aba {string}")
    public void oUsuarioAcessaAAba(String aba) throws Exception {
        if ("Listas".equals(aba)) {
            resultActions = mockMvc.perform(get("/api/listas/publicas"));
            response = resultActions.andReturn().getResponse();
        }
    }

    @E("ele clicar em uma lista pública chamada {string}")
    public void eleClicarEmUmaListaPublicaChamada(String nomeLista) throws Exception {
        ListaFilmes lista = listaFilmesRepository.findByNome(nomeLista).orElseThrow();
        resultActions = mockMvc.perform(get("/api/listas/" + lista.getId()));
        response = resultActions.andReturn().getResponse();
    }

    @Então("deve visualizar todos os filmes dessa lista")
    public void deveVisualizarTodosOsFilmesDessaLista() throws Exception {
        resultActions.andExpect(status().isOk());
        
        resultActions = mockMvc.perform(get("/api/listas/" + lista.getId() + "/filmes"));
        resultActions.andExpect(status().isOk());
        
        List<ItemLista> itens = itemListaRepository.findByListaIdOrderByVotosDesc(lista.getId());
        Assertions.assertFalse(itens.isEmpty());
    }

    @Dado("que o usuário está logado")
    public void queOUsuarioEstaLogado() {
        // Já configurado no contexto
    }

    @Quando("ele clicar em {string} e definir como colaborativa")
    public void eleClicarEmEDefinirComoColaborativa(String botao) throws Exception {
        if ("Criar nova lista".equals(botao)) {
            Map<String, Object> listaRequest = new HashMap<>();
            listaRequest.put("nome", "Lista Colaborativa");
            listaRequest.put("descricao", "Uma lista para colaboração");
            listaRequest.put("colaborativa", true);
            
            resultActions = mockMvc.perform(post("/api/listas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(listaRequest)));
            
            response = resultActions.andReturn().getResponse();
            
            // Obter a lista criada
            lista = objectMapper.readValue(response.getContentAsString(), ListaFilmes.class);
        }
    }

    @Então("o sistema deve criar a lista com permissões de edição para convidados")
    public void oSistemaDeveCriarAListaComPermissoesDeEdicaoParaConvidados() throws Exception {
        resultActions.andExpect(status().isCreated());
        
        // Verificar se a lista foi criada como colaborativa
        ListaFilmes listaCriada = listaFilmesRepository.findById(lista.getId()).orElseThrow();
        Assertions.assertTrue(listaCriada.isColaborativa());
    }

    @Dado("que o usuário criou uma lista")
    public void queOUsuarioCriouUmaLista() {
        lista = new ListaFilmes();
        lista.setNome("Minha Lista");
        lista.setDescricao("Lista de filmes favoritos");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        lista.setColaborativa(false);
        listaFilmesRepository.save(lista);
    }

    @Quando("ele acessar um filme e clicar em {string}")
    public void eleAcessarUmFilmeEClicarEm(String botao) throws Exception {
        if ("Adicionar à lista".equals(botao)) {
            resultActions = mockMvc.perform(post("/api/listas/" + lista.getId() + "/filmes/" + filme.getId()));
            response = resultActions.andReturn().getResponse();
        }
    }

    @Então("o filme deve ser incluído na lista selecionada")
    public void oFilmeDeveSerIncluidoNaListaSelecionada() throws Exception {
        resultActions.andExpect(status().isCreated());
        
        // Verificar se o filme foi adicionado à lista
        Optional<ItemLista> itemAdicionado = itemListaRepository.findByListaIdAndFilmeId(lista.getId(), filme.getId());
        Assertions.assertTrue(itemAdicionado.isPresent());
    }

    @Dado("que existe uma lista configurada para votação")
    public void queExisteUmaListaConfiguradaParaVotacao() {
        lista = new ListaFilmes();
        lista.setNome("Lista com Votação");
        lista.setDescricao("Lista onde você pode votar nos filmes");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        lista.setPermiteVotacao(true);
        listaFilmesRepository.save(lista);
        
        // Adicionar filme à lista
        itemLista = new ItemLista(lista, filme, usuarioTeste);
        itemListaRepository.save(itemLista);
    }

    @Quando("o usuário votar em um filme da lista")
    public void oUsuarioVotarEmUmFilmeDaLista() throws Exception {
        resultActions = mockMvc.perform(post("/api/listas/itens/" + itemLista.getId() + "/votar"));
        response = resultActions.andReturn().getResponse();
    }

    @Então("o sistema deve registrar o voto")
    public void oSistemaDeveRegistrarOVoto() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verificar se o voto foi registrado
        ItemLista itemAtualizado = itemListaRepository.findById(itemLista.getId()).orElseThrow();
        Assertions.assertEquals(1, itemAtualizado.getVotos());
    }

    @Dado("que o usuário visualiza uma lista")
    public void queOUsuarioVisualizaUmaLista() {
        lista = new ListaFilmes();
        lista.setNome("Lista para Comentar");
        lista.setDescricao("Lista onde você pode comentar");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        listaFilmesRepository.save(lista);
    }

    @Quando("ele escrever um comentário")
    public void eleEscreverUmComentario() throws Exception {
        Map<String, String> comentarioRequest = new HashMap<>();
        comentarioRequest.put("texto", "Este é um comentário na lista");
        
        resultActions = mockMvc.perform(post("/api/listas/" + lista.getId() + "/comentarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(comentarioRequest)));
        
        response = resultActions.andReturn().getResponse();
    }

    @Então("o comentário deve aparecer para todos que acessarem a lista")
    public void oComentarioDeveAparecerParaTodosQueAcessaremALista() throws Exception {
        resultActions.andExpect(status().isCreated());
        
        // Verificar se o comentário foi adicionado
        List<ComentarioLista> comentarios = comentarioListaRepository.findByListaIdOrderByDataHoraDesc(lista.getId());
        Assertions.assertFalse(comentarios.isEmpty());
        Assertions.assertEquals("Este é um comentário na lista", comentarios.get(0).getTexto());
    }

    @Dado("que o usuário é o criador da lista")
    public void queOUsuarioEOCriadorDaLista() {
        lista = new ListaFilmes();
        lista.setNome("Lista para Excluir");
        lista.setDescricao("Lista que será excluída");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        listaFilmesRepository.save(lista);
    }

    @Quando("ele clicar em {string}")
    public void eleClicarEm(String botao) throws Exception {
        if ("Excluir lista".equals(botao)) {
            resultActions = mockMvc.perform(delete("/api/listas/" + lista.getId()));
            response = resultActions.andReturn().getResponse();
        }
    }

    @Então("o sistema deve apagar permanentemente a lista")
    public void oSistemaDeveApagarPermanentementeALista() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verificar se a lista foi removida
        Optional<ListaFilmes> listaRemovida = listaFilmesRepository.findById(lista.getId());
        Assertions.assertTrue(listaRemovida.isEmpty());
    }

    @Dado("que o usuário está editando uma lista")
    public void queOUsuarioEstaEditandoUmaLista() {
        lista = new ListaFilmes();
        lista.setNome("Lista para Tornar Privada");
        lista.setDescricao("Lista que será privada");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        listaFilmesRepository.save(lista);
    }

    @Quando("ele marcar a opção {string}")
    public void eleMarcarAOpcao(String opcao) throws Exception {
        if ("Privada".equals(opcao)) {
            Map<String, Object> listaRequest = new HashMap<>();
            listaRequest.put("publica", false);
            
            resultActions = mockMvc.perform(put("/api/listas/" + lista.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(listaRequest)));
            
            response = resultActions.andReturn().getResponse();
        }
    }

    @Então("apenas convidados poderão visualizá-la")
    public void apenasConvidadosPoderaVisualizaLa() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verificar se a lista foi atualizada como privada
        ListaFilmes listaAtualizada = listaFilmesRepository.findById(lista.getId()).orElseThrow();
        Assertions.assertFalse(listaAtualizada.isPublica());
    }

    @Dado("que o usuário criou uma lista colaborativa")
    public void queOUsuarioCriouUmaListaColaborativa() {
        lista = new ListaFilmes();
        lista.setNome("Lista com Colaboradores");
        lista.setDescricao("Lista para adicionar colaboradores");
        lista.setCriador(usuarioTeste);
        lista.setPublica(true);
        lista.setColaborativa(true);
        listaFilmesRepository.save(lista);
    }

    @Quando("ele adicionar um amigo como colaborador")
    public void eleAdicionarUmAmigoComoColaborador() throws Exception {
        resultActions = mockMvc.perform(post("/api/listas/" + lista.getId() + "/colaboradores/" + outroUsuario.getId()));
        response = resultActions.andReturn().getResponse();
    }

    @Então("o sistema deve permitir que o amigo também edite a lista")
    public void oSistemaDevePermitirQueOAmigoTambemEditeALista() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verificar se o amigo foi adicionado como colaborador
        ListaFilmes listaAtualizada = listaFilmesRepository.findById(lista.getId()).orElseThrow();
        Assertions.assertTrue(listaAtualizada.getColaboradores().contains(outroUsuario));
    }
} 