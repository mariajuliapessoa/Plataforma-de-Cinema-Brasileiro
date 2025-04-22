package com.cesar.bracine.steps;

import com.cesar.bracine.model.Desafio;
import com.cesar.bracine.model.DesafioUsuario;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.User;
import com.cesar.bracine.repository.DesafioRepository;
import com.cesar.bracine.repository.DesafioUsuarioRepository;
import com.cesar.bracine.repository.FilmeRepository;
import com.cesar.bracine.repository.UserRepository;
import com.cesar.bracine.service.DesafioService;
import com.cesar.bracine.service.NotificacaoService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

public class DesafiosSteps {

    @Autowired
    private DesafioRepository desafioRepository;

    @Autowired
    private DesafioUsuarioRepository desafioUsuarioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private DesafioService desafioService;

    @MockBean
    private NotificacaoService notificacaoService;

    private User usuarioLogado;
    private User usuarioAdmin;
    private Desafio desafioAtivo;
    private DesafioUsuario participacaoDesafio;
    private Filme filme;
    private List<Filme> filmesDesafio = new ArrayList<>();
    private boolean operacaoSucesso;

    @Before
    public void setUp() {
        desafioUsuarioRepository.deleteAll();
        desafioRepository.deleteAll();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Dado("que existe um usuário cadastrado com o username {string}")
    public void queExisteUmUsuarioCadastradoComOUsername(String username) {
        usuarioLogado = new User();
        usuarioLogado.setUsername(username);
        usuarioLogado.setEmail(username + "@teste.com");
        usuarioLogado.setPassword("senha123");
        usuarioLogado.setRole("ROLE_USER");
        userRepository.save(usuarioLogado);
    }

    @E("que existe um filme {string} cadastrado no sistema")
    public void queExisteUmFilmeCadastradoNoSistema(String titulo) {
        filme = new Filme();
        filme.setTitulo(titulo);
        filme.setAno(2019);
        filme.setDiretor("Kleber Mendonça Filho");
        filme.setSinopse("Filme de teste para os cenários de desafio");
        filmeRepository.save(filme);
        filmesDesafio.add(filme);
    }

    @E("que o usuário está autenticado")
    public void queOUsuarioEstaAutenticado() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                usuarioLogado, null, usuarioLogado.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Dado("que existe um desafio ativo chamado {string}")
    public void queExisteUmDesafioAtivoChamado(String nomeDesafio) {
        desafioAtivo = new Desafio();
        desafioAtivo.setTitulo(nomeDesafio);
        desafioAtivo.setDescricao("Assista 5 filmes clássicos do Cinema Novo");
        desafioAtivo.setDataInicio(LocalDate.now().minusDays(5));
        desafioAtivo.setDataFim(LocalDate.now().plusDays(30));
        desafioAtivo.setFilmes(filmesDesafio);
        desafioAtivo.setAtivo(true);
        desafioRepository.save(desafioAtivo);
    }

    @Quando("o usuário acessar o desafio {string}")
    public void oUsuarioAcessarODesafio(String nomeDesafio) {
        desafioAtivo = desafioRepository.findByTitulo(nomeDesafio)
                .orElseThrow(() -> new RuntimeException("Desafio não encontrado"));
    }

    @Então("deve ser adicionado ao desafio")
    public void deveSerAdicionadoAoDesafio() {
        participacaoDesafio = desafioService.participarDesafio(usuarioLogado.getId(), desafioAtivo.getId());
        Assert.assertNotNull("Participação não foi criada", participacaoDesafio);
        Assert.assertEquals("Usuário incorreto", usuarioLogado.getId(), participacaoDesafio.getUsuario().getId());
        Assert.assertEquals("Desafio incorreto", desafioAtivo.getId(), participacaoDesafio.getDesafio().getId());
        Assert.assertEquals("Progresso inicial deve ser 0", 0, participacaoDesafio.getProgresso());
    }

    @Dado("que o usuário participa de um desafio")
    public void queOUsuarioParticipaDeUmDesafio() {
        if (desafioAtivo == null) {
            queExisteUmDesafioAtivoChamado("Assista 5 filmes do Cinema Novo");
        }
        participacaoDesafio = new DesafioUsuario();
        participacaoDesafio.setUsuario(usuarioLogado);
        participacaoDesafio.setDesafio(desafioAtivo);
        participacaoDesafio.setDataInicio(LocalDateTime.now().minusDays(2));
        participacaoDesafio.setProgresso(2);
        participacaoDesafio.setConcluido(false);
        desafioUsuarioRepository.save(participacaoDesafio);
    }

    @Quando("ele acessar a aba {string}")
    public void eleAcessarAAba(String nomeAba) {
        // Simulação do acesso a uma aba da interface
    }

    @Então("o sistema deve exibir seu progresso")
    public void oSistemaDeveExibirSeuProgresso() {
        DesafioUsuario desafioUsuario = desafioUsuarioRepository.findByUsuarioIdAndDesafioId(
                usuarioLogado.getId(), desafioAtivo.getId())
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
        
        Assert.assertNotNull("Progresso não encontrado", desafioUsuario);
        Assert.assertEquals("Progresso incorreto", 2, desafioUsuario.getProgresso());
    }

    @Dado("que o filme faz parte de um desafio")
    public void queOFilmeFazParteDeUmDesafio() {
        queOUsuarioParticipaDeUmDesafio();
        Assert.assertTrue("Filme não está no desafio", 
                desafioAtivo.getFilmes().stream().anyMatch(f -> f.getId().equals(filme.getId())));
    }

    @Quando("o usuário clicar em {string}")
    public void oUsuarioClicarEm(String botao) {
        if ("Marcar como assistido".equals(botao)) {
            operacaoSucesso = desafioService.marcarFilmeAssistido(usuarioLogado.getId(), desafioAtivo.getId(), filme.getId());
        } else if ("Sair do desafio".equals(botao)) {
            operacaoSucesso = desafioService.sairDoDesafio(usuarioLogado.getId(), desafioAtivo.getId());
        }
    }

    @Então("o sistema atualiza seu progresso no desafio")
    public void oSistemaAtualizaSeuProgressoNoDesafio() {
        Assert.assertTrue("Operação falhou", operacaoSucesso);
        
        DesafioUsuario desafioAtualizado = desafioUsuarioRepository.findByUsuarioIdAndDesafioId(
                usuarioLogado.getId(), desafioAtivo.getId())
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
        
        // O progresso deve ter aumentado
        Assert.assertEquals("Progresso não foi atualizado", 3, desafioAtualizado.getProgresso());
    }

    @Dado("que o usuário completou todos os filmes do desafio")
    public void queOUsuarioCompletouTodosOsFilmesDoDesafio() {
        queOUsuarioParticipaDeUmDesafio();
        
        participacaoDesafio.setProgresso(filmesDesafio.size());
        participacaoDesafio.setConcluido(true);
        participacaoDesafio.setDataConclusao(LocalDateTime.now());
        desafioUsuarioRepository.save(participacaoDesafio);
    }

    @Então("o sistema deve exibir uma conquista desbloqueada")
    public void oSistemaDeveExibirUmaConquistaDesbloqueada() {
        DesafioUsuario desafioConcluido = desafioUsuarioRepository.findByUsuarioIdAndDesafioId(
                usuarioLogado.getId(), desafioAtivo.getId())
                .orElseThrow(() -> new RuntimeException("Participação não encontrada"));
        
        Assert.assertTrue("Desafio não foi marcado como concluído", desafioConcluido.getConcluido());
        Assert.assertNotNull("Data de conclusão não foi registrada", desafioConcluido.getDataConclusao());
        
        // Verificar se a notificação de conquista foi enviada (mockado)
        verify(notificacaoService).enviarNotificacao(any(User.class), any(String.class));
    }

    @Então("ele deve ver a posição dele e de outros usuários")
    public void eleDeveVerAPosicaoDelEDeOutrosUsuarios() {
        List<DesafioUsuario> ranking = desafioService.obterRankingDesafio(desafioAtivo.getId());
        Assert.assertFalse("Ranking está vazio", ranking.isEmpty());
        
        boolean usuarioEstaNoRanking = ranking.stream()
                .anyMatch(du -> du.getUsuario().getId().equals(usuarioLogado.getId()));
        Assert.assertTrue("Usuário não está no ranking", usuarioEstaNoRanking);
    }

    @Dado("que o administrador está logado")
    public void queOAdministradorEstaLogado() {
        usuarioAdmin = new User();
        usuarioAdmin.setUsername("admin");
        usuarioAdmin.setEmail("admin@teste.com");
        usuarioAdmin.setPassword("senha123");
        usuarioAdmin.setRole("ROLE_ADMIN");
        userRepository.save(usuarioAdmin);
        
        Authentication auth = new UsernamePasswordAuthenticationToken(
                usuarioAdmin, null, usuarioAdmin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Quando("ele clicar em {string}")
    public void eleClicarEm(String botao) {
        // Simulação do clique em botão da interface
    }

    @Então("o desafio será salvo e publicado")
    public void oDesafioSeraSalvoEPublicado() {
        Desafio novoDesafio = new Desafio();
        novoDesafio.setTitulo("Novo Desafio de Teste");
        novoDesafio.setDescricao("Descrição do novo desafio de teste");
        novoDesafio.setDataInicio(LocalDate.now());
        novoDesafio.setDataFim(LocalDate.now().plusDays(30));
        novoDesafio.setFilmes(filmesDesafio);
        novoDesafio.setAtivo(true);
        
        novoDesafio = desafioService.criarDesafio(novoDesafio);
        Assert.assertNotNull("Desafio não foi criado", novoDesafio.getId());
        Assert.assertTrue("Desafio não está ativo", novoDesafio.getAtivo());
    }

    @Dado("que o usuário não deseja mais participar")
    public void queOUsuarioNaoDesejaMaisParticipar() {
        queOUsuarioParticipaDeUmDesafio();
    }

    @Então("o sistema deve removê-lo do desafio")
    public void oSistemaDeveRemoveLoDoDesafio() {
        Assert.assertTrue("Operação falhou", operacaoSucesso);
        
        Optional<DesafioUsuario> participacao = desafioUsuarioRepository.findByUsuarioIdAndDesafioId(
                usuarioLogado.getId(), desafioAtivo.getId());
        
        Assert.assertFalse("Usuário ainda está no desafio", participacao.isPresent());
    }

    @Dado("que um novo desafio foi publicado")
    public void queUmNovoDesafioFoiPublicado() {
        Desafio novoDesafio = new Desafio();
        novoDesafio.setTitulo("Desafio Recém-Publicado");
        novoDesafio.setDescricao("Um novo desafio para testar notificações");
        novoDesafio.setDataInicio(LocalDate.now());
        novoDesafio.setDataFim(LocalDate.now().plusDays(30));
        novoDesafio.setFilmes(filmesDesafio);
        novoDesafio.setAtivo(true);
        novoDesafio.setNovo(true);
        desafioRepository.save(novoDesafio);
    }

    @Quando("o usuário abrir a plataforma")
    public void oUsuarioAbrirAPlataforma() {
        // Simulação do acesso à plataforma
    }

    @Então("ele deve receber uma notificação sobre o desafio")
    public void eleDeveReceberUmaNotificacaoSobreODesafio() {
        List<Desafio> desafiosNovos = desafioRepository.findByAtivoTrueAndNovoTrue();
        Assert.assertFalse("Não há desafios novos", desafiosNovos.isEmpty());
        
        // Verificar se a notificação foi enviada (mockado)
        verify(notificacaoService).enviarNotificacao(any(User.class), any(String.class));
    }
} 