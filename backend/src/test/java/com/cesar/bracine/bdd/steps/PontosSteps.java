package com.cesar.bracine.bdd.steps;

import com.cesar.bracine.domain.entities.Usuario;
import com.cesar.bracine.domain.enums.TipoUsuario;
import com.cesar.bracine.domain.services.UsuarioService;
import io.cucumber.java.en.*;
import org.junit.jupiter.api.Assertions;

import java.util.Optional;
import java.util.UUID;

public class PontosSteps {

    private final UsuarioService usuarioService;
    private Usuario usuario;
    private String erro;
    private UUID idInexistente;

    public PontosSteps(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Given("que o sistema possui um usuário {string} com email {string}")
    public void que_o_sistema_possui_um_usuario_com_email(String nome, String email) {
        Optional<Usuario> existente = usuarioService.buscarUsuarioPorEmail(email);
        usuario = existente.orElseGet(() -> usuarioService.criarUsuario(nome, "usuarioPontos123", TipoUsuario.USER ,email, "senha123"));
    }

    @When("ele recebe {int} pontos")
    public void eleRecebePontos(int pontos) {
        usuarioService.adicionarPontos(usuario.getId(), pontos);
    }

    @Then("o sistema deve registrar os pontos corretamente com total de {int}")
    public void o_total_de_pontos_do_usuario_deve_ser(Integer totalEsperado) {
        Optional<Usuario> atualizado = usuarioService.buscarUsuarioPorEmail(usuario.getEmail());
        Assertions.assertTrue(atualizado.isPresent());
        Assertions.assertEquals(totalEsperado, atualizado.get().getPontos());
    }

    @When("eu tento adicionar {int} pontos ao usuário com ID inexistente")
    public void eu_tento_adicionar_pontos_ao_usuario_com_id_inexistente(Integer pontos) {
        idInexistente = UUID.randomUUID();
        try {
            usuarioService.adicionarPontos(idInexistente, pontos);
            Assertions.fail("Esperado erro de usuário não encontrado");
        } catch (IllegalArgumentException e) {
            erro = e.getMessage();
        }
    }

    @Then("o sistema deve lançar um erro de {string}")
    public void o_sistema_deve_lancar_um_erro_de(String mensagemEsperada) {
        Assertions.assertEquals(mensagemEsperada, erro);
    }
}
