package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.enums.TipoUsuario;
import com.cesar.bracine.domain.services.UsuarioService;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

@CucumberContextConfiguration
@SpringBootTest(classes = com.cesar.bracine.bdd.config.TestConfig.class)
public class UsuarioSteps {


    private final UsuarioService usuarioService;
    private String erro;
    public Usuario usuario;

    public UsuarioSteps(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Cenário 1: Criar um novo usuário com sucesso
    @Given("que o usuário {string} deseja se cadastrar")
    public void que_o_usuario_deseja_se_cadastrar(String nome) {}

    @When("ele fornece o nome {string}, nome de usuário {string}, email {string}, e senha {string}")
    public void ele_fornece_o_nome_nome_de_usuario_email_e_senha(String nome, String nomeUsuario, String email, String senha) {
        Optional<Usuario> existente = usuarioService.buscarUsuarioPorEmail(email);
        if (existente.isPresent()) {
            usuario = existente.get();
        } else {
            usuario = usuarioService.criarUsuario(nome, nomeUsuario, TipoUsuario.USER, email, senha);
        }
    }

    @Then("o sistema deve criar o usuário com sucesso")
    public void o_sistema_deve_criar_o_usuario_com_sucesso() {
        Optional<Usuario> usuarioCadastrado = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());
        Assertions.assertTrue(usuarioCadastrado.isPresent());
        Assertions.assertEquals(usuario.getNome(), usuarioCadastrado.get().getNome());
    }

    // Cenário 2: Alterar a senha de um usuário
    @Given("que o usuário com email {string} já está cadastrado")
    public void que_o_usuario_com_email_ja_esta_cadastrado(String email) {
        usuario = usuarioService.buscarUsuarioPorEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    @When("ele altera sua senha para {string}")
    public void ele_altera_sua_senha_para(String novaSenha) {
        usuarioService.alterarSenha(usuario.getId().getValue(), novaSenha);
    }

    @Then("o sistema deve atualizar a senha do usuário")
    public void o_sistema_deve_atualizar_a_senha_do_usuario() {
        Optional<Usuario> usuarioAtualizado = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());
        Assertions.assertTrue(usuarioAtualizado.isPresent());
        Assertions.assertNotEquals("senha123", usuarioAtualizado.get().getSenhaHash());  // Verifica se a senha foi alterada
    }

    // Cenário 3: Criar conta com e-mail já cadastrado
    @Given("que o usuário {string} já está cadastrado com email {string}")
    public void que_o_usuario_ja_esta_cadastrado_com_email(String nome, String email) {
        Optional<Usuario> existente = usuarioService.buscarUsuarioPorEmail(email);
        usuario = existente.orElseGet(() -> usuarioService.criarUsuario(nome, "usuario123", TipoUsuario.USER, email, "senha123"));
    }

    @When("ele tenta se cadastrar novamente com o mesmo email")
    public void ele_tenta_se_cadastrar_novamente_com_o_mesmo_email() {
        try {
            usuarioService.criarUsuario("Outro Nome", "outrousuario", TipoUsuario.USER, usuario.getEmail(), "outrasenha");
            Assertions.fail("Email já cadastrado");
        } catch (IllegalArgumentException e) {
            erro = e.getMessage();
        }
    }

    @Then("o sistema deve impedir o cadastro com uma mensagem de erro")
    public void o_sistema_deve_impedir_o_cadastro_com_uma_mensagem_de_erro() {
        Assertions.assertEquals("Email já cadastrado", erro);
    }

    // Cenário 4: Editar dados da conta
    @Given("que o usuário {string} está cadastrado com email {string}")
    public void o_usuario_ja_esta_cadastrado(String nome, String email) {
        usuario = usuarioService.criarUsuario(nome, "carlosAntes", TipoUsuario.USER,email, "senha123");


    }

    @When("ele atualiza seu nome para {string}, nome de usuário para {string}, e email para {string}")
    public void ele_atualiza_seu_nome_para_nome_de_usuario_para_e_email_para(String novoNome, String novoNomeUsuario, String novoEmail) {
        usuarioService.editarUsuario(usuario.getId().getValue(), novoNome, novoNomeUsuario, novoEmail);
    }

    @Then("os dados do usuário devem ser atualizados com sucesso")
    public void os_dados_do_usuario_devem_ser_atualizados_com_sucesso() {
        Optional<Usuario> atualizado = usuarioService.buscarUsuarioPorEmail("c.silva@example.com");
        Assertions.assertTrue(atualizado.isPresent());
        Assertions.assertEquals("Carlos Silva", atualizado.get().getNome());
        Assertions.assertEquals("carloss", atualizado.get().getNomeUsuario());
    }
}
