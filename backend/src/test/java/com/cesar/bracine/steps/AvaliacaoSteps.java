package com.cesar.bracine.steps;

import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.user.User;
import com.cesar.bracine.model.user.UserRole;
import com.cesar.bracine.repository.AvaliacaoRepository;
import com.cesar.bracine.repository.FilmeRepository;
import com.cesar.bracine.repository.UserRepository;
import com.cesar.bracine.service.AvaliacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
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
public class AvaliacaoSteps {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private User usuarioTeste;
    private Filme filme;
    private ResultActions resultActions;
    private MockHttpServletResponse response;
    private Avaliacao avaliacaoExistente;
    private String comentarioAvaliacao;
    private Integer estrelasAvaliacao;

    @Before
    public void setUp() {
        avaliacaoRepository.deleteAll();
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

    @Dado("que existe um filme {string} cadastrado no sistema")
    public void queExisteUmFilmeCadastradoNoSistema(String titulo) {
        filme = new Filme();
        filme.setTitulo(titulo);
        filme.setDescricao("Descrição do filme " + titulo);
        filme.setGenero("Drama");
        filme.setDiretor("Anna Muylaert");
        filme.setAno(2015);
        filme.setNota(0.0);
        filmeRepository.save(filme);
    }

    @Dado("que o usuário está autenticado")
    public void queOUsuarioEstaAutenticado() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                usuarioTeste, null, usuarioTeste.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Dado("que o usuário está na página do filme {string}")
    public void queOUsuarioEstaNaPaginaDoFilme(String titulo) {
        // Simula a navegação para a página do filme
        filme = filmeRepository.findByTitulo(titulo).orElseThrow();
    }

    @Quando("ele preencher {int} estrelas e escrever um comentário de {int} caracteres")
    public void elePreencher4EstrelasEEscreverUmComentarioDe30Caracteres(Integer estrelas, Integer tamanhoComentario) throws Exception {
        estrelasAvaliacao = estrelas;
        comentarioAvaliacao = "A".repeat(tamanhoComentario); // Cria um comentário com o tamanho especificado
        
        Map<String, Object> avaliacaoRequest = new HashMap<>();
        avaliacaoRequest.put("comentario", comentarioAvaliacao);
        avaliacaoRequest.put("estrelas", estrelasAvaliacao);
        
        resultActions = mockMvc.perform(post("/api/avaliacoes/filme/" + filme.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoRequest)));
        
        response = resultActions.andReturn().getResponse();
    }

    @Então("a avaliação deve ser salva com sucesso")
    public void aAvaliacaoDeveSerSalvaComSucesso() throws Exception {
        resultActions.andExpect(status().isCreated());
        
        // Verifica se a avaliação foi realmente salva no banco de dados
        Optional<Avaliacao> avaliacaoSalva = avaliacaoRepository.findByFilmeIdAndUsuarioId(
                filme.getId(), usuarioTeste.getId());
        
        Assertions.assertTrue(avaliacaoSalva.isPresent());
        Assertions.assertEquals(estrelasAvaliacao, avaliacaoSalva.get().getEstrelas());
        Assertions.assertEquals(comentarioAvaliacao, avaliacaoSalva.get().getComentario());
    }

    @Dado("que o usuário tenta avaliar um filme")
    public void queOUsuarioTentaAvaliarUmFilme() {
        // Já preparado no contexto
    }

    @Quando("ele não escreve nenhum comentário")
    public void eleNaoEscreveNenhumComentario() throws Exception {
        Map<String, Object> avaliacaoRequest = new HashMap<>();
        avaliacaoRequest.put("comentario", "");
        avaliacaoRequest.put("estrelas", 3);
        
        resultActions = mockMvc.perform(post("/api/avaliacoes/filme/" + filme.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoRequest)));
        
        response = resultActions.andReturn().getResponse();
    }

    @Então("o sistema deve exibir uma mensagem de erro solicitando pelo menos {int} caracteres")
    public void oSistemaDeveExibirUmaMensagemDeErroSolicitandoPeloMenos20Caracteres(Integer minCaracteres) throws Exception {
        resultActions.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.erro").value("O comentário deve ter pelo menos " + minCaracteres + " caracteres"));
    }

    @Dado("que o usuário insere um comentário com menos de {int} caracteres")
    public void queOUsuarioInsereUmComentarioComMenosDe20Caracteres(Integer minCaracteres) {
        comentarioAvaliacao = "Comentário curto";  // Menos de 20 caracteres
    }

    @Quando("ele tenta enviar a avaliação")
    public void eleTentaEnviarAAvaliacao() throws Exception {
        Map<String, Object> avaliacaoRequest = new HashMap<>();
        avaliacaoRequest.put("comentario", comentarioAvaliacao);
        avaliacaoRequest.put("estrelas", 3);
        
        resultActions = mockMvc.perform(post("/api/avaliacoes/filme/" + filme.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoRequest)));
        
        response = resultActions.andReturn().getResponse();
    }

    @Então("o sistema deve impedir o envio e exibir uma mensagem de erro")
    public void oSistemaDeveImpedirOEnvioEExibirUmaMensagemDeErro() throws Exception {
        resultActions.andExpect(status().isBadRequest());
    }

    @Dado("que o usuário já avaliou um filme")
    public void queOUsuarioJaAvaliouUmFilme() {
        // Criar avaliação inicial
        avaliacaoExistente = new Avaliacao(filme, usuarioTeste, 3, "Este é um comentário inicial com mais de 20 caracteres");
        avaliacaoRepository.save(avaliacaoExistente);
    }

    @Quando("ele alterar a nota ou o comentário")
    public void eleAlterarANotaOuOComentario() throws Exception {
        Map<String, Object> avaliacaoRequest = new HashMap<>();
        avaliacaoRequest.put("comentario", "Este é um comentário atualizado com mais de 20 caracteres");
        avaliacaoRequest.put("estrelas", 5);  // Nota atualizada
        
        resultActions = mockMvc.perform(post("/api/avaliacoes/filme/" + filme.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(avaliacaoRequest)));
        
        response = resultActions.andReturn().getResponse();
    }

    @Então("o sistema deve substituir a avaliação anterior pela nova")
    public void oSistemaDeveSubstituirAAvaliacaoAnteriorPelaNova() throws Exception {
        resultActions.andExpect(status().isCreated());
        
        // Verifica se a avaliação foi atualizada
        Optional<Avaliacao> avaliacaoAtualizada = avaliacaoRepository.findByFilmeIdAndUsuarioId(
                filme.getId(), usuarioTeste.getId());
        
        Assertions.assertTrue(avaliacaoAtualizada.isPresent());
        Assertions.assertEquals(5, avaliacaoAtualizada.get().getEstrelas()); // Nota atualizada
        Assertions.assertEquals("Este é um comentário atualizado com mais de 20 caracteres", 
                avaliacaoAtualizada.get().getComentario());
    }

    @Dado("que o usuário deseja apagar sua avaliação")
    public void queOUsuarioDesejaApagarSuaAvaliacao() {
        // Criar avaliação para ser removida
        avaliacaoExistente = new Avaliacao(filme, usuarioTeste, 3, "Este é um comentário para ser removido");
        avaliacaoRepository.save(avaliacaoExistente);
    }

    @Quando("ele clicar em {string}")
    public void eleClicarEm(String botao) throws Exception {
        if ("Remover avaliação".equals(botao)) {
            resultActions = mockMvc.perform(delete("/api/avaliacoes/" + avaliacaoExistente.getId()));
            response = resultActions.andReturn().getResponse();
        } else if ("Curtir".equals(botao)) {
            resultActions = mockMvc.perform(post("/api/avaliacoes/" + avaliacaoExistente.getId() + "/curtir"));
            response = resultActions.andReturn().getResponse();
        }
    }

    @Então("o sistema deve excluir a avaliação permanentemente")
    public void oSistemaDeveExcluirAAvaliacaoPermanentemente() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verifica se a avaliação foi realmente removida
        Optional<Avaliacao> avaliacaoRemovida = avaliacaoRepository.findById(avaliacaoExistente.getId());
        Assertions.assertTrue(avaliacaoRemovida.isEmpty());
    }

    @Dado("que existem avaliações de outros usuários para o filme")
    public void queExistemAvaliacoesDeOutrosUsuariosParaOFilme() {
        // Criar outro usuário
        User outroUsuario = new User();
        outroUsuario.setUsername("outro_usuario");
        outroUsuario.setEmail("outro_usuario@email.com");
        outroUsuario.setPassword(passwordEncoder.encode("senha123"));
        outroUsuario.setRole(UserRole.USER);
        userRepository.save(outroUsuario);
        
        // Criar avaliação desse outro usuário
        Avaliacao avaliacaoOutroUsuario = new Avaliacao(
                filme, outroUsuario, 4, "Esta é uma avaliação de outro usuário com mais de 20 caracteres");
        avaliacaoRepository.save(avaliacaoOutroUsuario);
    }

    @Quando("o usuário acessa a página de um filme")
    public void oUsuarioAcessaAPaginaDeUmFilme() throws Exception {
        resultActions = mockMvc.perform(get("/api/avaliacoes/filme/" + filme.getId()));
        response = resultActions.andReturn().getResponse();
    }

    @Então("ele deve visualizar as avaliações públicas dos outros usuários")
    public void eleDeveVisualizarAsAvaliacoesPublicasDosOutrosUsuarios() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verifica se há pelo menos uma avaliação na resposta
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByFilmeId(filme.getId());
        Assertions.assertFalse(avaliacoes.isEmpty());
    }

    @Dado("que o usuário leu um comentário interessante")
    public void queOUsuarioLeuUmComentarioInteressante() {
        // Criar avaliação para ser curtida
        User outroUsuario = new User();
        outroUsuario.setUsername("usuario_avaliacao");
        outroUsuario.setEmail("usuario_avaliacao@email.com");
        outroUsuario.setPassword(passwordEncoder.encode("senha123"));
        outroUsuario.setRole(UserRole.USER);
        userRepository.save(outroUsuario);
        
        avaliacaoExistente = new Avaliacao(
                filme, outroUsuario, 5, "Este é um comentário interessante para ser curtido");
        avaliacaoExistente.setCurtidas(0);
        avaliacaoRepository.save(avaliacaoExistente);
    }

    @Então("o número de curtidas daquele comentário deve aumentar")
    public void oNumeroDeCurtidasDaqueleComentarioDeveAumentar() throws Exception {
        resultActions.andExpect(status().isOk());
        
        // Verifica se as curtidas aumentaram
        Avaliacao avaliacaoAtualizada = avaliacaoRepository.findById(avaliacaoExistente.getId()).orElseThrow();
        Assertions.assertEquals(1, avaliacaoAtualizada.getCurtidas());
    }

    @Dado("que existem avaliações para o filme")
    public void queExistemAvaliacoesParaOFilme() {
        // Criar múltiplas avaliações
        User usuario1 = new User();
        usuario1.setUsername("usuario1");
        usuario1.setEmail("usuario1@email.com");
        usuario1.setPassword(passwordEncoder.encode("senha123"));
        usuario1.setRole(UserRole.USER);
        userRepository.save(usuario1);
        
        User usuario2 = new User();
        usuario2.setUsername("usuario2");
        usuario2.setEmail("usuario2@email.com");
        usuario2.setPassword(passwordEncoder.encode("senha123"));
        usuario2.setRole(UserRole.USER);
        userRepository.save(usuario2);
        
        avaliacaoRepository.save(new Avaliacao(filme, usuario1, 4, "Comentário do usuário 1 com mais de 20 caracteres"));
        avaliacaoRepository.save(new Avaliacao(filme, usuario2, 5, "Comentário do usuário 2 com mais de 20 caracteres"));
    }

    @Quando("a página do filme for carregada")
    public void aPaginaDoFilmeForCarregada() throws Exception {
        resultActions = mockMvc.perform(get("/api/avaliacoes/filme/" + filme.getId() + "/media"));
        response = resultActions.andReturn().getResponse();
    }

    @Então("o sistema deve exibir a média das notas recebidas")
    public void oSistemaDeveExibirAMediaDasNotasRecebidas() throws Exception {
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.media").exists());
        
        Double media = avaliacaoService.buscarMediaAvaliacoes(filme.getId());
        Assertions.assertNotNull(media);
    }
} 