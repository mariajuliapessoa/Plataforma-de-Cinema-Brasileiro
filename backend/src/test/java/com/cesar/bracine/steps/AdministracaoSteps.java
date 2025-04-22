package com.cesar.bracine.steps;

import com.cesar.bracine.model.*;
import com.cesar.bracine.repository.*;
import com.cesar.bracine.service.*;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class AdministracaoSteps {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private DesafioRepository desafioRepository;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private DenunciaComentarioRepository denunciaRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private DesafioService desafioService;

    @Autowired
    private UserService userService;

    @MockBean
    private NotificacaoService notificacaoService;

    private User admin;
    private Filme filme;
    private Comentario comentario;
    private DenunciaComentario denuncia;
    private Desafio desafio;
    private User usuario;
    private boolean operacaoSucesso;
    private String mensagemErro;
    private Map<String, Object> relatorio;

    @Before
    public void setUp() {
        denunciaRepository.deleteAll();
        comentarioRepository.deleteAll();
        desafioRepository.deleteAll();
        filmeRepository.deleteAll();
    }

    @After
    public void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Dado("que existe um usuário administrador com o username {string}")
    public void queExisteUmUsuarioAdministradorComOUsername(String username) {
        admin = new User();
        admin.setUsername(username);
        admin.setEmail(username + "@admin.com");
        admin.setPassword("senha123");
        admin.setRole("ROLE_ADMIN");
        userRepository.save(admin);
    }

    @E("que o administrador está autenticado")
    public void queOAdministradorEstaAutenticado() {
        Authentication auth = new UsernamePasswordAuthenticationToken(
                admin, null, admin.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @Dado("que o administrador está logado")
    public void queOAdministradorEstaLogado() {
        queExisteUmUsuarioAdministradorComOUsername("admin");
        queOAdministradorEstaAutenticado();
    }

    @Quando("ele clicar em {string}")
    public void eleClicarEm(String botao) {
        // Simulação do clique em botão da interface
    }

    @Então("deve ser redirecionado para o painel de gestão da plataforma")
    public void deveSerRedirecionadoParaOPainelDeGestaoDaPlataforma() {
        // Verificar se o usuário tem acesso ao painel de administração
        boolean acessoPermitido = adminService.verificarAcessoAdmin(admin.getId());
        Assert.assertTrue("Acesso ao painel de administração negado", acessoPermitido);
    }

    @Dado("que há comentários denunciados")
    public void queHaComentariosDenunciados() {
        // Criar um usuário regular
        usuario = new User();
        usuario.setUsername("usuario_comum");
        usuario.setEmail("usuario@teste.com");
        usuario.setPassword("senha123");
        usuario.setRole("ROLE_USER");
        userRepository.save(usuario);
        
        // Criar um filme
        filme = new Filme();
        filme.setTitulo("Filme Teste");
        filme.setAno(2020);
        filme.setDiretor("Diretor Teste");
        filme.setSinopse("Sinopse do filme de teste");
        filmeRepository.save(filme);
        
        // Criar um comentário
        comentario = new Comentario();
        comentario.setAutor(usuario);
        comentario.setFilme(filme);
        comentario.setTexto("Este é um comentário que será denunciado");
        comentario.setDataCriacao(LocalDateTime.now().minusDays(1));
        comentarioRepository.save(comentario);
        
        // Criar uma denúncia
        denuncia = new DenunciaComentario();
        denuncia.setUsuario(usuario);
        denuncia.setComentario(comentario);
        denuncia.setMotivo("Conteúdo ofensivo");
        denuncia.setDescricao("Este comentário contém linguagem inapropriada");
        denuncia.setDataDenuncia(LocalDateTime.now());
        denuncia.setRevisada(false);
        denunciaRepository.save(denuncia);
    }

    @Quando("o admin acessar a aba de denúncias")
    public void oAdminAcessarAAbaDeDenuncias() {
        // Simulação do acesso à aba de denúncias
    }

    @Então("ele poderá aprovar, ocultar ou apagar o comentário")
    public void elePoderaAprovarOcultarOuApagarOComentario() {
        // Obter denúncias pendentes
        List<DenunciaComentario> denunciasPendentes = adminService.listarDenunciasPendentes();
        Assert.assertFalse("Não há denúncias pendentes", denunciasPendentes.isEmpty());
        
        // Aprovar a denúncia (marcar como procedente e remover o comentário)
        boolean aprovada = adminService.revisarDenuncia(denuncia.getId(), admin.getId(), true, "Comentário removido por violar as regras");
        Assert.assertTrue("Falha ao revisar a denúncia", aprovada);
        
        // Verificar se a denúncia foi marcada como revisada e procedente
        Optional<DenunciaComentario> denunciaRevisada = denunciaRepository.findById(denuncia.getId());
        Assert.assertTrue("Denúncia não encontrada", denunciaRevisada.isPresent());
        Assert.assertTrue("Denúncia não foi marcada como revisada", denunciaRevisada.get().getRevisada());
        Assert.assertTrue("Denúncia não foi marcada como procedente", denunciaRevisada.get().getProcedente());
        Assert.assertEquals("Revisor incorreto", admin.getId(), denunciaRevisada.get().getRevisor().getId());
    }

    @Dado("que um filme está marcado como impróprio")
    public void queUmFilmeEstaMarcadoComoImproprio() {
        // Criar um filme e marcá-lo como impróprio
        filme = new Filme();
        filme.setTitulo("Filme Impróprio");
        filme.setAno(2020);
        filme.setDiretor("Diretor Teste");
        filme.setSinopse("Sinopse do filme impróprio");
        filme.setImproprio(true);
        filmeRepository.save(filme);
    }

    @Então("o filme será excluído da plataforma")
    public void oFilmeSeraExcluidoDaPlataforma() {
        operacaoSucesso = adminService.removerFilme(filme.getId());
        Assert.assertTrue("Falha ao remover o filme", operacaoSucesso);
        
        // Verificar se o filme foi excluído
        Optional<Filme> filmeRemovido = filmeRepository.findById(filme.getId());
        Assert.assertFalse("Filme ainda existe na plataforma", filmeRemovido.isPresent());
    }

    @Dado("que o administrador quer engajar usuários")
    public void queOAdministradorQuerEngajarUsuarios() {
        // Criar alguns filmes para o desafio
        for (int i = 0; i < 3; i++) {
            Filme filmeDesafio = new Filme();
            filmeDesafio.setTitulo("Filme Desafio " + i);
            filmeDesafio.setAno(2020 + i);
            filmeDesafio.setDiretor("Diretor " + i);
            filmeDesafio.setSinopse("Sinopse do filme " + i);
            filmeRepository.save(filmeDesafio);
        }
    }

    @Quando("ele criar um novo desafio")
    public void eleCriarUmNovoDesafio() {
        // Criar um novo desafio
        desafio = new Desafio();
        desafio.setTitulo("Desafio Cinema Brasileiro");
        desafio.setDescricao("Assista a estes filmes brasileiros e ganhe pontos!");
        desafio.setDataInicio(LocalDate.now());
        desafio.setDataFim(LocalDate.now().plusDays(30));
        desafio.setFilmes(filmeRepository.findAll());
        desafio.setAtivo(true);
        
        desafio = desafioService.criarDesafio(desafio);
    }

    @Então("ele será publicado na seção de desafios")
    public void eleSeraPublicadoNaSecaoDeDesafios() {
        Assert.assertNotNull("Desafio não foi criado", desafio.getId());
        
        // Verificar se o desafio está na lista de desafios ativos
        List<Desafio> desafiosAtivos = desafioService.listarDesafiosAtivos();
        Assert.assertFalse("Não há desafios ativos", desafiosAtivos.isEmpty());
        
        boolean desafioEncontrado = desafiosAtivos.stream()
                .anyMatch(d -> d.getId().equals(desafio.getId()));
        Assert.assertTrue("Desafio não está na lista de desafios ativos", desafioEncontrado);
    }

    @Dado("que um usuário é reconhecido como crítico")
    public void queUmUsuarioEReconhecidoComoCritico() {
        // Criar um usuário regular
        usuario = new User();
        usuario.setUsername("critico");
        usuario.setEmail("critico@teste.com");
        usuario.setPassword("senha123");
        usuario.setRole("ROLE_USER");
        usuario.setContribuicoes(50); // Muitas contribuições
        userRepository.save(usuario);
    }

    @Quando("o admin alterar seu status")
    public void oAdminAlterarSeuStatus() {
        operacaoSucesso = adminService.promoverUsuario(usuario.getId(), "ROLE_ESPECIALISTA");
    }

    @Então("o usuário se tornará especialista e terá permissões especiais")
    public void oUsuarioSeTornaraEspecialistaETeraPermissoesEspeciais() {
        Assert.assertTrue("Falha ao promover usuário", operacaoSucesso);
        
        // Verificar se o usuário foi promovido
        Optional<User> usuarioAtualizado = userRepository.findById(usuario.getId());
        Assert.assertTrue("Usuário não encontrado", usuarioAtualizado.isPresent());
        Assert.assertEquals("Papel do usuário não foi atualizado", "ROLE_ESPECIALISTA", usuarioAtualizado.get().getRole());
        
        // Verificar se o usuário recebeu a notificação
        verify(notificacaoService).enviarNotificacao(any(User.class), any(String.class));
    }

    @Dado("que o admin deseja promover diversidade")
    public void queOAdminDesejaPromoverDiversidade() {
        // Criar um filme para destacar
        filme = new Filme();
        filme.setTitulo("Filme Diversidade");
        filme.setAno(2020);
        filme.setDiretor("Diretora Teste");
        filme.setSinopse("Filme que promove diversidade");
        filme.setDestaque(false);
        filmeRepository.save(filme);
    }

    @Quando("ele marcar um filme como destaque")
    public void eleMarcarUmFilmeComoDestaque() {
        operacaoSucesso = adminService.destacarFilme(filme.getId(), true);
    }

    @Então("ele será exibido na aba de tendências")
    public void eleSeraExibidoNaAbaDeTendencias() {
        Assert.assertTrue("Falha ao destacar filme", operacaoSucesso);
        
        // Verificar se o filme foi destacado
        Optional<Filme> filmeAtualizado = filmeRepository.findById(filme.getId());
        Assert.assertTrue("Filme não encontrado", filmeAtualizado.isPresent());
        Assert.assertTrue("Filme não foi marcado como destaque", filmeAtualizado.get().isDestaque());
        
        // Verificar se o filme está na lista de destaques
        List<Filme> filmesDestaque = filmeService.listarFilmesEmDestaque();
        Assert.assertFalse("Não há filmes em destaque", filmesDestaque.isEmpty());
        
        boolean filmeEncontrado = filmesDestaque.stream()
                .anyMatch(f -> f.getId().equals(filme.getId()));
        Assert.assertTrue("Filme não está na lista de destaques", filmeEncontrado);
    }

    @Dado("que o admin precisa visualizar dados da plataforma")
    public void queOAdminPrecisaVisualizarDadosDaPlataforma() {
        // Criar dados para o relatório
        for (int i = 0; i < 10; i++) {
            User usuarioRelatorio = new User();
            usuarioRelatorio.setUsername("usuario" + i);
            usuarioRelatorio.setEmail("usuario" + i + "@teste.com");
            usuarioRelatorio.setPassword("senha123");
            usuarioRelatorio.setRole("ROLE_USER");
            usuarioRelatorio.setDataCadastro(LocalDateTime.now().minusDays(i));
            userRepository.save(usuarioRelatorio);
            
            Filme filmeRelatorio = new Filme();
            filmeRelatorio.setTitulo("Filme " + i);
            filmeRelatorio.setAno(2020 + i % 5);
            filmeRelatorio.setDiretor("Diretor " + i % 3);
            filmeRelatorio.setSinopse("Sinopse do filme " + i);
            filmeRelatorio.setVisualizacoes(i * 10);
            filmeRepository.save(filmeRelatorio);
        }
    }

    @Quando("ele clicar em {string}")
    public void eleClicarEmRelatorios(String botao) {
        if ("Relatórios".equals(botao)) {
            relatorio = adminService.gerarRelatorioUso(LocalDate.now().minusDays(30), LocalDate.now());
        }
    }

    @Então("o sistema gera gráficos e métricas")
    public void oSistemaGeraGraficosEMetricas() {
        Assert.assertNotNull("Relatório não foi gerado", relatorio);
        Assert.assertFalse("Relatório está vazio", relatorio.isEmpty());
        
        // Verificar se o relatório contém as métricas esperadas
        Assert.assertTrue("Falta métrica de usuários cadastrados", relatorio.containsKey("totalUsuarios"));
        Assert.assertTrue("Falta métrica de filmes cadastrados", relatorio.containsKey("totalFilmes"));
        Assert.assertTrue("Falta métrica de visualizações", relatorio.containsKey("totalVisualizacoes"));
        Assert.assertTrue("Falta métrica de comentários", relatorio.containsKey("totalComentarios"));
        Assert.assertTrue("Falta métrica de denúncias", relatorio.containsKey("totalDenuncias"));
    }

    @Dado("que o administrador deseja atualizar as regras")
    public void queOAdministradorDesejaAtualizarAsRegras() {
        // Preparar configurações de moderação
    }

    @Quando("ele editar as configurações de moderação")
    public void eleEditarAsConfiguracoesDeModeracao() {
        Map<String, Object> novasRegras = new HashMap<>();
        novasRegras.put("palavrasProibidas", "palavra1,palavra2,palavra3");
        novasRegras.put("limiteDenunciasAutomaticas", 5);
        novasRegras.put("moderacaoPrevia", true);
        novasRegras.put("tempoEspera", 24);
        
        operacaoSucesso = adminService.atualizarRegrasModeracao(novasRegras);
    }

    @Então("as novas regras passam a valer imediatamente")
    public void asNovasRegrasPassamAValerImediatamente() {
        Assert.assertTrue("Falha ao atualizar regras de moderação", operacaoSucesso);
        
        // Verificar se as regras foram atualizadas
        Map<String, Object> regrasAtuais = adminService.obterRegrasModeracao();
        Assert.assertNotNull("Regras não foram obtidas", regrasAtuais);
        Assert.assertEquals("Palavras proibidas não foram atualizadas", 
                "palavra1,palavra2,palavra3", regrasAtuais.get("palavrasProibidas"));
        Assert.assertEquals("Limite de denúncias automáticas não foi atualizado", 
                5, regrasAtuais.get("limiteDenunciasAutomaticas"));
        Assert.assertEquals("Moderação prévia não foi atualizada", 
                true, regrasAtuais.get("moderacaoPrevia"));
        Assert.assertEquals("Tempo de espera não foi atualizado", 
                24, regrasAtuais.get("tempoEspera"));
    }
} 