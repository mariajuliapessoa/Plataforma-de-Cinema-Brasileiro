package com.cesar.bracine.steps;

import com.cesar.bracine.model.ComentarioDebate;
import com.cesar.bracine.model.DebateFilme;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.User;
import com.cesar.bracine.repository.ComentarioDebateRepository;
import com.cesar.bracine.repository.DebateFilmeRepository;
import com.cesar.bracine.repository.FilmeRepository;
import com.cesar.bracine.repository.UserRepository;
import com.cesar.bracine.service.DebateService;
import com.cesar.bracine.service.NotificacaoService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class DebateFilmesSteps {

    @Autowired
    private DebateFilmeRepository debateFilmeRepository;

    @Autowired
    private ComentarioDebateRepository comentarioDebateRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private DebateService debateService;

    @MockBean
    private NotificacaoService notificacaoService;

    private User usuario;
    private Filme filme;
    private DebateFilme debate;
    private ComentarioDebate comentario;
    private ComentarioDebate comentarioExistente;
    private String textoComentario;
    private boolean operacaoSucesso;
    private String mensagemErro;

    @Before
    public void setUp() {
        comentarioDebateRepository.deleteAll();
        debateFilmeRepository.deleteAll();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Dado("que existe um usuário cadastrado com o username {string}")
    public void queExisteUmUsuarioCadastradoComOUsername(String username) {
        usuario = new User();
        usuario.setUsername(username);
        usuario.setEmail(username + "@teste.com");
        usuario.setPassword("senha123");
        usuario.setRole("ROLE_USER");
        userRepository.save(usuario);
    }

    @E("que existe um filme {string} cadastrado no sistema")
    public void queExisteUmFilmeCadastradoNoSistema(String titulo) {
        filme = new Filme();
        filme.setTitulo(titulo);
        filme.setAno(2019);
        filme.setDiretor("Kleber Mendonça Filho");
        filme.setSinopse("Filme de teste para os cenários de debate");
        filmeRepository.save(filme);
    }

    @E("que o usuário está autenticado")
    public void queOUsuarioEstaAutenticado() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                usuario, null, usuario.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Dado("que o usuário está na página do filme")
    public void queOUsuarioEstaNaPaginaDoFilme() {
        if (debate == null) {
            // Criar um debate caso não exista
            debate = new DebateFilme();
            debate.setFilme(filme);
            debate.setTitulo("Debate sobre " + filme.getTitulo());
            debate.setDescricao("Espaço para discutir sobre o filme " + filme.getTitulo());
            debate.setDataCriacao(LocalDateTime.now());
            debate.setAtivo(true);
            debateFilmeRepository.save(debate);
        }
    }

    @Quando("ele clicar em {string}")
    public void eleClicarEm(String botao) {
        // Simulação do clique em botão da interface
    }

    @Então("será direcionado à sala de debate daquele filme")
    public void seraDirecionadoASalaDeDebateDaqueleFilme() {
        Optional<DebateFilme> debateOpt = debateFilmeRepository.findByFilmeId(filme.getId());
        Assert.assertTrue("Debate do filme não foi encontrado", debateOpt.isPresent());
        debate = debateOpt.get();
    }

    @Dado("que o usuário está em uma sala de discussão")
    public void queOUsuarioEstaEmUmaSalaDeDiscussao() {
        queOUsuarioEstaNaPaginaDoFilme();
        seraDirecionadoASalaDeDebateDaqueleFilme();
    }

    @Quando("ele escrever e enviar um comentário")
    public void eleEscreverEEnviarUmComentario() {
        textoComentario = "Este é um comentário de teste para o debate sobre o filme " + filme.getTitulo();
        comentario = debateService.adicionarComentario(usuario.getId(), debate.getId(), textoComentario, null);
    }

    @Então("o comentário deve aparecer na discussão")
    public void oComentarioDeveAparecerNaDiscussao() {
        Assert.assertNotNull("Comentário não foi criado", comentario);
        Assert.assertEquals("Texto do comentário incorreto", textoComentario, comentario.getTexto());
        Assert.assertEquals("Autor do comentário incorreto", usuario.getId(), comentario.getAutor().getId());
        Assert.assertEquals("Debate do comentário incorreto", debate.getId(), comentario.getDebate().getId());
        
        List<ComentarioDebate> comentarios = comentarioDebateRepository.findByDebateIdOrderByDataCriacaoDesc(debate.getId());
        Assert.assertFalse("Não há comentários no debate", comentarios.isEmpty());
        
        boolean comentarioEncontrado = comentarios.stream()
                .anyMatch(c -> c.getId().equals(comentario.getId()));
        Assert.assertTrue("Comentário não aparece na lista de comentários do debate", comentarioEncontrado);
    }

    @Dado("que há um comentário existente")
    public void queHaUmComentarioExistente() {
        queOUsuarioEstaEmUmaSalaDeDiscussao();
        
        // Criar um comentário para responder
        comentarioExistente = new ComentarioDebate();
        comentarioExistente.setDebate(debate);
        comentarioExistente.setAutor(usuario);
        comentarioExistente.setTexto("Comentário inicial para testes de resposta");
        comentarioExistente.setDataCriacao(LocalDateTime.now().minusHours(1));
        comentarioDebateRepository.save(comentarioExistente);
    }

    @Quando("o usuário clicar em {string}")
    public void oUsuarioClicarEm(String botao) {
        if ("Responder".equals(botao)) {
            // Simulação do clique em "Responder"
        } else if ("Curtir".equals(botao)) {
            operacaoSucesso = debateService.curtirComentario(usuario.getId(), comentarioExistente.getId());
        } else if ("Denunciar".equals(botao)) {
            operacaoSucesso = debateService.denunciarComentario(usuario.getId(), comentarioExistente.getId(), "Conteúdo ofensivo");
        }
    }

    @Então("ele poderá adicionar uma resposta diretamente abaixo")
    public void elePoderaAdicionarUmaRespostaAbaixo() {
        String textoResposta = "Esta é uma resposta ao comentário original";
        ComentarioDebate resposta = debateService.adicionarComentario(usuario.getId(), debate.getId(), textoResposta, comentarioExistente.getId());
        
        Assert.assertNotNull("Resposta não foi criada", resposta);
        Assert.assertEquals("Texto da resposta incorreto", textoResposta, resposta.getTexto());
        Assert.assertEquals("Autor da resposta incorreto", usuario.getId(), resposta.getAutor().getId());
        Assert.assertEquals("Comentário pai incorreto", comentarioExistente.getId(), resposta.getComentarioPai().getId());
        
        List<ComentarioDebate> respostas = comentarioDebateRepository.findByComentarioPaiId(comentarioExistente.getId());
        Assert.assertFalse("Não há respostas ao comentário", respostas.isEmpty());
    }

    @Dado("que o usuário visualiza comentários")
    public void queOUsuarioVisualizaComentarios() {
        queHaUmComentarioExistente();
    }

    @Então("o número de curtidas aumenta em 1")
    public void oNumeroDeCurtidasAumentaEm1() {
        Assert.assertTrue("Operação de curtir falhou", operacaoSucesso);
        
        ComentarioDebate comentarioAtualizado = comentarioDebateRepository.findById(comentarioExistente.getId())
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        
        Assert.assertEquals("Número de curtidas não aumentou", 1, comentarioAtualizado.getTotalCurtidas());
        Assert.assertTrue("Usuário não está na lista de curtidas", 
                comentarioAtualizado.getCurtidas().stream().anyMatch(u -> u.getId().equals(usuario.getId())));
    }

    @Dado("que um comentário viola as regras")
    public void queUmComentarioViolaAsRegras() {
        queHaUmComentarioExistente();
    }

    @Então("o sistema deve notificar os moderadores")
    public void oSistemaDeveNotificarOsModeradores() {
        Assert.assertTrue("Operação de denúncia falhou", operacaoSucesso);
        
        ComentarioDebate comentarioDenunciado = comentarioDebateRepository.findById(comentarioExistente.getId())
                .orElseThrow(() -> new RuntimeException("Comentário não encontrado"));
        
        Assert.assertTrue("Comentário não foi marcado como denunciado", comentarioDenunciado.isDenunciado());
        Assert.assertEquals("Número de denúncias não aumentou", 1, comentarioDenunciado.getTotalDenuncias());
        
        // Verificar se a notificação foi enviada (mockado)
        verify(notificacaoService).enviarNotificacaoAdmin(any(String.class));
    }

    @Dado("que há discussões com muita atividade")
    public void queHaDiscussoesComMuitaAtividade() {
        // Criar vários debates com muitos comentários
        for (int i = 0; i < 3; i++) {
            DebateFilme debateAtivo = new DebateFilme();
            debateAtivo.setFilme(filme);
            debateAtivo.setTitulo("Debate " + i + " sobre " + filme.getTitulo());
            debateAtivo.setDescricao("Descrição do debate " + i);
            debateAtivo.setDataCriacao(LocalDateTime.now().minusDays(i));
            debateAtivo.setAtivo(true);
            debateAtivo = debateFilmeRepository.save(debateAtivo);
            
            // Adicionar vários comentários
            for (int j = 0; j < 10; j++) {
                ComentarioDebate c = new ComentarioDebate();
                c.setDebate(debateAtivo);
                c.setAutor(usuario);
                c.setTexto("Comentário " + j + " no debate " + i);
                c.setDataCriacao(LocalDateTime.now().minusHours(j));
                comentarioDebateRepository.save(c);
            }
        }
    }

    @Quando("o usuário acessar a aba {string}")
    public void oUsuarioAcessarAAba(String aba) {
        // Simulação do acesso a uma aba da interface
    }

    @Então("ele verá os mais comentados recentemente")
    public void eleVeraOsMaisComentadosRecentemente() {
        List<DebateFilme> debatesEmAlta = debateService.listarDebatesEmAlta();
        Assert.assertFalse("Não há debates em alta", debatesEmAlta.isEmpty());
        
        // Verificar se os debates estão ordenados corretamente
        for (int i = 0; i < debatesEmAlta.size() - 1; i++) {
            int comentariosAtual = comentarioDebateRepository.countByDebateId(debatesEmAlta.get(i).getId());
            int comentariosProximo = comentarioDebateRepository.countByDebateId(debatesEmAlta.get(i + 1).getId());
            Assert.assertTrue("Debates não estão ordenados por número de comentários", 
                    comentariosAtual >= comentariosProximo);
        }
    }

    @Dado("que um evento de debate está agendado")
    public void queUmEventoDeDebateEstaAgendado() {
        // Criar um debate com especialista agendado
        debate = new DebateFilme();
        debate.setFilme(filme);
        debate.setTitulo("Debate com especialista sobre " + filme.getTitulo());
        debate.setDescricao("Venha conversar com nosso especialista sobre o filme!");
        debate.setDataCriacao(LocalDateTime.now().minusDays(1));
        debate.setAtivo(true);
        debate.setEspecialista(true);
        debate.setDataEvento(LocalDateTime.now().plusHours(1));
        debateFilmeRepository.save(debate);
    }

    @Quando("o horário chegar")
    public void oHorarioChegar() {
        // Simulação do horário do evento
        debate.setEmAndamento(true);
        debateFilmeRepository.save(debate);
    }

    @Então("o usuário poderá entrar na sala ao vivo")
    public void oUsuarioPoderaEntrarNaSalaAoVivo() {
        DebateFilme debateEmAndamento = debateFilmeRepository.findById(debate.getId())
                .orElseThrow(() -> new RuntimeException("Debate não encontrado"));
        
        Assert.assertTrue("Debate não está em andamento", debateEmAndamento.isEmAndamento());
        Assert.assertTrue("Debate não é com especialista", debateEmAndamento.isEspecialista());
        
        // Simular a inscrição do usuário no evento
        boolean inscricao = debateService.inscreverNoEvento(usuario.getId(), debate.getId());
        Assert.assertTrue("Falha ao inscrever no evento", inscricao);
    }

    @Dado("que o usuário participa de uma discussão")
    public void queOUsuarioParticipaDeUmaDiscussao() {
        queOUsuarioEstaEmUmaSalaDeDiscussao();
        eleEscreverEEnviarUmComentario();
    }

    @Quando("houver um novo comentário")
    public void houverUmNovoComentario() {
        // Criar um novo comentário de outro usuário
        User outroUsuario = new User();
        outroUsuario.setUsername("outro_usuario");
        outroUsuario.setEmail("outro@teste.com");
        outroUsuario.setPassword("senha123");
        outroUsuario.setRole("ROLE_USER");
        userRepository.save(outroUsuario);
        
        ComentarioDebate novoComentario = new ComentarioDebate();
        novoComentario.setDebate(debate);
        novoComentario.setAutor(outroUsuario);
        novoComentario.setTexto("Este é um novo comentário de outro usuário");
        novoComentario.setDataCriacao(LocalDateTime.now());
        comentarioDebateRepository.save(novoComentario);
        
        // Notificar participantes
        debateService.notificarParticipantes(debate.getId(), novoComentario.getId());
    }

    @Então("o sistema deve enviar uma notificação")
    public void oSistemaDeveEnviarUmaNotificacao() {
        // Verificar se a notificação foi enviada (mockado)
        verify(notificacaoService).enviarNotificacao(any(User.class), any(String.class));
    }
} 