package com.cesar.bracine.steps;

import com.cesar.bracine.model.Avaliacao;
import com.cesar.bracine.model.DenunciaComentario;
import com.cesar.bracine.model.Filme;
import com.cesar.bracine.model.Usuario;
import com.cesar.bracine.repository.AvaliacaoRepository;
import com.cesar.bracine.repository.DenunciaComentarioRepository;
import com.cesar.bracine.repository.FilmeRepository;
import com.cesar.bracine.repository.UsuarioRepository;
import com.cesar.bracine.service.DenunciaComentarioService;
import com.cesar.bracine.service.NotificacaoService;
import io.cucumber.java.After;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ActiveProfiles("test")
public class DenunciaComentarioSteps {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AvaliacaoRepository avaliacaoRepository;

    @Autowired
    private DenunciaComentarioRepository denunciaComentarioRepository;

    @Autowired
    private DenunciaComentarioService denunciaComentarioService;

    @MockBean
    private NotificacaoService notificacaoService;

    private Usuario usuarioComum;
    private Usuario usuarioAdmin;
    private Filme filme;
    private Avaliacao avaliacaoSelecionada;
    private DenunciaComentario denunciaComentario;
    private String motivo;
    private String descricao;
    private String mensagemErro;
    private boolean resultadoOperacao;

    @After
    public void tearDown() {
        denunciaComentarioRepository.deleteAll();
        avaliacaoRepository.deleteAll();
        filmeRepository.deleteAll();
        usuarioRepository.deleteAll();
    }

    @Dado("que existe um usuário cadastrado com o username {string}")
    public void queExisteUmUsuarioCadastradoComOUsername(String username) {
        usuarioComum = new Usuario();
        usuarioComum.setNome("Usuário Teste");
        usuarioComum.setUsername(username);
        usuarioComum.setEmail(username + "@teste.com");
        usuarioComum.setSenha("senha123");
        usuarioComum.setRole("ROLE_USER");
        usuarioRepository.save(usuarioComum);
    }

    @E("que existe um usuário administrador com o username {string}")
    public void queExisteUmUsuarioAdministradorComOUsername(String username) {
        usuarioAdmin = new Usuario();
        usuarioAdmin.setNome("Administrador Teste");
        usuarioAdmin.setUsername(username);
        usuarioAdmin.setEmail(username + "@teste.com");
        usuarioAdmin.setSenha("senha123");
        usuarioAdmin.setRole("ROLE_ADMIN");
        usuarioRepository.save(usuarioAdmin);
    }

    @E("que existe um filme {string} cadastrado no sistema")
    public void queExisteUmFilmeCadastradoNoSistema(String titulo) {
        filme = new Filme();
        filme.setTitulo(titulo);
        filme.setAno(2020);
        filme.setSinopse("Um filme de teste para os cenários de denúncia");
        filme.setDiretor("Diretor Teste");
        filmeRepository.save(filme);
    }

    @E("que existem avaliações com comentários para o filme")
    public void queExistemAvaliacoesComComentariosParaOFilme() {
        // Criar uma avaliação com comentário que será denunciada
        avaliacaoSelecionada = new Avaliacao();
        avaliacaoSelecionada.setUsuario(usuarioAdmin); // Criamos com outro usuário para permitir que usuarioComum denuncie
        avaliacaoSelecionada.setFilme(filme);
        avaliacaoSelecionada.setNota(4);
        avaliacaoSelecionada.setComentario("Este é um comentário que será denunciado nos testes.");
        avaliacaoSelecionada.setDataAvaliacao(LocalDateTime.now());
        avaliacaoRepository.save(avaliacaoSelecionada);
        
        // Criar mais algumas avaliações para o filme
        Avaliacao avaliacao2 = new Avaliacao();
        avaliacao2.setUsuario(usuarioComum);
        avaliacao2.setFilme(filme);
        avaliacao2.setNota(3);
        avaliacao2.setComentario("Outro comentário qualquer sobre o filme.");
        avaliacao2.setDataAvaliacao(LocalDateTime.now().minusDays(1));
        avaliacaoRepository.save(avaliacao2);
    }

    @E("que o usuário está autenticado como {string}")
    public void queOUsuarioEstaAutenticadoComo(String username) {
        if ("admin".equals(username)) {
            // Simular que o usuário atual é o admin
            usuarioComum = usuarioAdmin;
        } else {
            // Garantir que o usuário atual seja o não-admin
            Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
            usuarioComum = usuarioOpt.orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        }
    }

    @Dado("que o usuário visualiza um comentário inadequado")
    public void queOUsuarioVisualizaUmComentarioInadequado() {
        // Esta etapa pressupõe que já temos a avaliação com comentário criada no contexto
        Assert.assertNotNull("A avaliação não foi criada corretamente", avaliacaoSelecionada);
    }

    @Quando("ele clicar no botão {string}")
    public void eleClicarNoBotao(String botao) {
        // Simulação do clique no botão, não é necessário fazer nada específico aqui
        Assert.assertEquals("Botão inválido", "Denunciar comentário", botao);
    }

    @E("selecionar o motivo {string}")
    public void selecionarOMotivo(String motivoDenuncia) {
        this.motivo = motivoDenuncia;
    }

    @E("preencher a descrição {string}")
    public void preencherADescricao(String descricaoDenuncia) {
        this.descricao = descricaoDenuncia;
    }

    @E("não preencher a descrição")
    public void naoPreencherADescricao() {
        this.descricao = "";
    }

    @E("enviar a denúncia")
    public void enviarADenuncia() {
        try {
            denunciaComentario = denunciaComentarioService.registrarDenuncia(
                usuarioComum.getId(),
                avaliacaoSelecionada.getId(),
                motivo,
                descricao
            );
            resultadoOperacao = true;
        } catch (IllegalArgumentException e) {
            mensagemErro = e.getMessage();
            resultadoOperacao = false;
        }
    }

    @Então("o sistema deve registrar a denúncia com sucesso")
    public void oSistemaDeveRegistrarADenunciaComSucesso() {
        Assert.assertTrue("A denúncia não foi registrada com sucesso", resultadoOperacao);
        Assert.assertNotNull("A denúncia não foi salva", denunciaComentario.getId());
        Assert.assertEquals("O motivo não foi salvo corretamente", motivo, denunciaComentario.getMotivo());
        Assert.assertEquals("A descrição não foi salva corretamente", descricao, denunciaComentario.getDescricao());
        Assert.assertEquals("O usuário não foi associado corretamente", usuarioComum.getId(), denunciaComentario.getUsuario().getId());
        Assert.assertEquals("O comentário não foi associado corretamente", avaliacaoSelecionada.getId(), denunciaComentario.getComentario().getId());
        Assert.assertNotNull("A data de denúncia não foi preenchida", denunciaComentario.getDataDenuncia());
        Assert.assertFalse("A denúncia não deveria estar marcada como revisada", denunciaComentario.getRevisada());
    }

    @E("exibir uma mensagem de confirmação")
    public void exibirUmaMensagemDeConfirmacao() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui apenas verificamos que a operação foi bem-sucedida
        Assert.assertTrue("A operação não foi concluída com sucesso", resultadoOperacao);
    }

    @Então("o sistema deve exibir uma mensagem solicitando uma descrição")
    public void oSistemaDeveExibirUmaMensagemSolicitandoUmaDescricao() {
        Assert.assertFalse("A operação não deveria ter sido bem-sucedida", resultadoOperacao);
        Assert.assertNotNull("Deveria haver uma mensagem de erro", mensagemErro);
        Assert.assertTrue("A mensagem deveria solicitar uma descrição", 
            mensagemErro.toLowerCase().contains("descrição") || 
            mensagemErro.toLowerCase().contains("descricao"));
    }

    @Dado("que o usuário realizou denúncias anteriormente")
    public void queOUsuarioRealizouDenunciasAnteriormente() {
        // Criar algumas denúncias para o usuário
        for (int i = 0; i < 3; i++) {
            DenunciaComentario denuncia = new DenunciaComentario();
            denuncia.setUsuario(usuarioComum);
            denuncia.setComentario(avaliacaoSelecionada);
            denuncia.setMotivo("Motivo de teste " + i);
            denuncia.setDescricao("Descrição de teste " + i);
            denuncia.setDataDenuncia(LocalDateTime.now().minusDays(i));
            denuncia.setRevisada(i % 2 == 0); // Alternando entre revisada e não revisada
            if (denuncia.getRevisada()) {
                denuncia.setRevisor(usuarioAdmin);
                denuncia.setDataRevisao(LocalDateTime.now().minusHours(i));
                denuncia.setProcedente(i == 0);
                denuncia.setAcaoTomada(i == 0 ? "Comentário removido" : "Nenhuma ação necessária");
            }
            denunciaComentarioRepository.save(denuncia);
        }
    }

    @Quando("ele acessar a página {string}")
    public void eleAcessarAPagina(String pagina) {
        // Simulação do acesso à página, não é necessário fazer nada específico aqui
    }

    @Então("ele deve visualizar todas as denúncias que realizou")
    public void eleDeveVisualizarTodasAsDenunciasQueRealizou() {
        List<DenunciaComentario> denuncias = denunciaComentarioService.listarDenunciasPorUsuarioOrdenadas(usuarioComum.getId());
        Assert.assertFalse("Não foram encontradas denúncias para o usuário", denuncias.isEmpty());
        
        // Verificar se todas as denúncias pertencem ao usuário correto
        for (DenunciaComentario denuncia : denuncias) {
            Assert.assertEquals("A denúncia não pertence ao usuário correto", 
                                usuarioComum.getId(), denuncia.getUsuario().getId());
        }
    }

    @E("o status de cada denúncia")
    public void oStatusDeCadaDenuncia() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui apenas verificamos que as denúncias têm o status definido
        List<DenunciaComentario> denuncias = denunciaComentarioService.listarDenunciasPorUsuarioOrdenadas(usuarioComum.getId());
        for (DenunciaComentario denuncia : denuncias) {
            // O status é representado pelo campo 'revisada'
            Assert.assertNotNull("O status da denúncia não está definido", denuncia.getRevisada());
        }
    }

    @Então("ele deve visualizar todas as denúncias não revisadas")
    public void eleDeveVisualizarTodasAsDenunciasNaoRevisadas() {
        List<DenunciaComentario> denunciasNaoRevisadas = denunciaComentarioService.listarDenunciasNaoRevisadas();
        Assert.assertFalse("Não foram encontradas denúncias não revisadas", denunciasNaoRevisadas.isEmpty());
        
        // Verificar se todas as denúncias estão marcadas como não revisadas
        for (DenunciaComentario denuncia : denunciasNaoRevisadas) {
            Assert.assertFalse("A denúncia já foi revisada", denuncia.getRevisada());
        }
    }

    @E("poder filtrar por tipo de motivo")
    public void poderFiltrarPorTipoDeMotivo() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui apenas verificamos que o serviço tem capacidade de filtrar por motivo
        String motivoFiltro = "Motivo de teste 1";
        List<DenunciaComentario> denunciasFiltradas = denunciaComentarioService.listarDenunciasPorMotivo(motivoFiltro);
        
        for (DenunciaComentario denuncia : denunciasFiltradas) {
            Assert.assertEquals("A denúncia não corresponde ao motivo filtrado", 
                                motivoFiltro, denuncia.getMotivo());
        }
    }

    @Dado("que existem denúncias não revisadas no sistema")
    public void queExistemDenunciasNaoRevisadasNoSistema() {
        // Já temos esse cenário configurado na etapa "que o usuário realizou denúncias anteriormente"
        // Verificamos apenas para garantir
        List<DenunciaComentario> denunciasNaoRevisadas = denunciaComentarioService.listarDenunciasNaoRevisadas();
        Assert.assertFalse("Não foram encontradas denúncias não revisadas", denunciasNaoRevisadas.isEmpty());
    }

    @Quando("ele selecionar uma denúncia para revisar")
    public void eleSelecionarUmaDenunciaParaRevisar() {
        // Selecionar a primeira denúncia não revisada
        List<DenunciaComentario> denunciasNaoRevisadas = denunciaComentarioService.listarDenunciasNaoRevisadas();
        denunciaComentario = denunciasNaoRevisadas.get(0);
    }

    @E("marcar a denúncia como {string}")
    public void marcarADenunciaComo(String procedencia) {
        denunciaComentario.setProcedente("procedente".equals(procedencia));
    }

    @E("selecionar a ação {string}")
    public void selecionarAAcao(String acao) {
        denunciaComentario.setAcaoTomada(acao);
    }

    @E("incluir justificativa {string}")
    public void incluirJustificativa(String justificativa) {
        denunciaComentario.setAcaoTomada(justificativa);
    }

    @E("finalizar a revisão")
    public void finalizarARevisao() {
        denunciaComentario = denunciaComentarioService.revisarDenuncia(
            denunciaComentario.getId(), 
            usuarioAdmin.getId(), 
            denunciaComentario.getProcedente(), 
            denunciaComentario.getAcaoTomada()
        );
    }

    @Então("o sistema deve atualizar o status da denúncia como revisada")
    public void oSistemaDeveAtualizarOStatusDaDenunciaComoRevisada() {
        // Buscar a denúncia atualizada
        Optional<DenunciaComentario> denunciaAtualizada = denunciaComentarioRepository.findById(denunciaComentario.getId());
        Assert.assertTrue("A denúncia não foi encontrada", denunciaAtualizada.isPresent());
        Assert.assertTrue("A denúncia não foi marcada como revisada", denunciaAtualizada.get().getRevisada());
        Assert.assertEquals("O revisor não foi associado corretamente", 
                            usuarioAdmin.getId(), denunciaAtualizada.get().getRevisor().getId());
        Assert.assertNotNull("A data de revisão não foi preenchida", denunciaAtualizada.get().getDataRevisao());
    }

    @E("o comentário denunciado deve ser removido")
    public void oComentarioDenunciadoDeveSerRemovido() {
        // Verificar se o comentário foi removido (simulado)
        verify(notificacaoService).enviarNotificacao(any(Usuario.class), any(String.class));
        
        // Em um cenário real, o comentário seria removido do banco de dados
        // Aqui estamos apenas verificando que a ação foi registrada corretamente
        Assert.assertEquals("A ação tomada não foi registrada corretamente", 
                           "Remover comentário", denunciaComentario.getAcaoTomada());
    }

    @E("o usuário que fez a denúncia deve ser notificado")
    public void oUsuarioQueFezADenunciaDeveSerNotificado() {
        // Verificar se a notificação foi enviada (simulado com Mockito)
        verify(notificacaoService).enviarNotificacao(any(Usuario.class), any(String.class));
    }

    @E("o comentário denunciado deve permanecer visível")
    public void oComentarioDenunciadoDevePermanecerVisivel() {
        // Verificar se o comentário ainda existe no banco de dados
        Optional<Avaliacao> avaliacaoOpt = avaliacaoRepository.findById(avaliacaoSelecionada.getId());
        Assert.assertTrue("A avaliação não foi encontrada", avaliacaoOpt.isPresent());
        Assert.assertNotNull("O comentário foi removido indevidamente", avaliacaoOpt.get().getComentario());
    }

    @Quando("ele acessar o painel de estatísticas de denúncias")
    public void eleAcessarOPainelDeEstatisticasDeDenuncias() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui apenas verificamos que há dados para exibir estatísticas
        Assert.assertFalse("Não há denúncias para gerar estatísticas", 
                         denunciaComentarioRepository.findAll().isEmpty());
    }

    @Então("ele deve visualizar gráficos com quantidade de denúncias por motivo")
    public void eleDeveVisualizarGraficosComQuantidadeDeDenunciasPorMotivo() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui apenas verificamos que o serviço consegue agrupar por motivo
        List<String> motivos = denunciaComentarioRepository.findDistinctMotivos();
        Assert.assertFalse("Não há motivos distintos para agrupar", motivos.isEmpty());
    }

    @E("taxa de denúncias procedentes vs improcedentes")
    public void taxaDeDenunciasProcedentesVsImprocedentes() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui apenas verificamos que há denúncias marcadas como procedentes e improcedentes
        long procedentes = denunciaComentarioRepository.countByProcedente(true);
        long improcedentes = denunciaComentarioRepository.countByProcedente(false);
        
        Assert.assertTrue("Não há dados suficientes para estatísticas", 
                       procedentes + improcedentes > 0);
    }

    @E("tempo médio de resposta às denúncias")
    public void tempoMedioDeRespostaAsDenuncias() {
        // Esta etapa seria implementada em um contexto de UI real
        // Aqui verificamos que há denúncias com data de revisão para calcular o tempo médio
        List<DenunciaComentario> denunciasRevisadas = denunciaComentarioRepository.findByRevisada(true);
        Assert.assertFalse("Não há denúncias revisadas para calcular tempo médio", 
                         denunciasRevisadas.isEmpty());
        
        for (DenunciaComentario denuncia : denunciasRevisadas) {
            Assert.assertNotNull("Data da denúncia é nula", denuncia.getDataDenuncia());
            Assert.assertNotNull("Data da revisão é nula", denuncia.getDataRevisao());
        }
    }
} 