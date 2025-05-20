package com.cesar.bracine.domain.entities;

import com.cesar.bracine.domain.enums.TipoUsuario;
import com.cesar.bracine.domain.valueobjects.UsuarioId;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.UUID;

public final class Usuario {

    private final UsuarioId id;
    private String nome;
    private String nomeUsuario;
    private String email;
    private String senhaHash;
    private int pontos;
    private TipoUsuario cargo;

    // Construtor privado para evitar criação direta fora da classe
    private Usuario(UsuarioId id, String nome, String nomeUsuario, String email, String senhaHash, TipoUsuario cargo) {
        this.id = Objects.requireNonNull(id);
        this.nome = validarNome(nome);
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.senhaHash = senhaHash;
        this.cargo = cargo;
        this.pontos = 0;  // Inicializa os pontos como zero
    }

    // Factory method para criar um novo usuário
    public static Usuario criar(String nome, String nomeUsuario, String email, TipoUsuario cargo,String senha, PasswordEncoder encoder) {
        String id = UUID.randomUUID().toString();
        return new Usuario(
                new UsuarioId(UUID.randomUUID()),
                nome,
                nomeUsuario,
                email,
                encoder.encode(senha),
                cargo
        );
    }

    public static Usuario reconstruir(UsuarioId id, String nome, String nomeUsuario, String email, TipoUsuario cargo, String senhaHash, int pontos) {
        Usuario usuario = new Usuario(id, nome, nomeUsuario, email, senhaHash, cargo);
        usuario.pontos = pontos;
        return usuario;
    }

    public void editarDados(String novoNome, String novoNomeUsuario, String novoEmail) {
        this.nome = novoNome;
        this.nomeUsuario = novoNomeUsuario;
        this.email = novoEmail;
    }

    // Método de domínio para adicionar pontos
    public void adicionarPontos(int pontos) {
        if (pontos <= 0) throw new IllegalArgumentException("Pontos devem ser positivos");
        this.pontos += pontos;
    }

    // Método de domínio para alterar a senha
    public void alterarSenha(String novaSenha, PasswordEncoder encoder) {
        this.senhaHash = encoder.encode(novaSenha);
    }

    // Validação do nome
    private String validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome inválido");
        }
        return nome.trim();
    }

    // Getters
    public UsuarioId getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public int getPontos() {
        return pontos;
    }

    public TipoUsuario getCargo() {
        return cargo;
    }
}
