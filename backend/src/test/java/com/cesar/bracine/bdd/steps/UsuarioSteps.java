package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.services.UsuarioService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class UsuarioSteps {

    @Autowired
    private UsuarioService usuarioService;

    private Usuario usuario;

    // Cenário 1: Criar um novo usuário com sucesso
    @Given("que o usuário {string} deseja se cadastrar")
    public void que_o_usuario_deseja_se_cadastrar(String nome) {
        usuario = usuarioService.criarUsuario(nome, "joao123", "joao@example.com", "senha123");
    }

    @When("ele fornece o nome {string}, nome de usuário {string}, email {string}, e senha {string}")
    public void ele_fornece_o_nome_nome_de_usuario_email_e_senha(String nome, String nomeUsuario, String email, String senha) {
        usuario = usuarioService.criarUsuario(nome, nomeUsuario, email, senha);
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
        usuarioService.alterarSenha(usuario.getId(), novaSenha);
    }

    @Then("o sistema deve atualizar a senha do usuário")
    public void o_sistema_deve_atualizar_a_senha_do_usuario() {
        Optional<Usuario> usuarioAtualizado = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());
        Assertions.assertTrue(usuarioAtualizado.isPresent());
        Assertions.assertNotEquals("senha123", usuarioAtualizado.get().getSenhaHash());  // Verifica se a senha foi alterada
    }
}
