package com.cesar.bracine.presentation.controllers;

import com.cesar.bracine.application.UsuarioApplicationService;
import com.cesar.bracine.presentation.dtos.UsuarioLoginDTO;
import com.cesar.bracine.presentation.dtos.UsuarioLoginResponseDTO;
import com.cesar.bracine.presentation.dtos.UsuarioRegisterRequestDTO;
import com.cesar.bracine.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioApplicationService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UsuarioApplicationService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/entrar")
    public ResponseEntity<String> login(@RequestBody UsuarioLoginDTO loginRequest) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.nomeUsuario(), loginRequest.senha());
        Authentication authResult = authenticationManager.authenticate(authentication);
        String token = jwtUtil.gerarToken(loginRequest.nomeUsuario());

        return ResponseEntity.ok(token);

    }

    @PostMapping("/registrar")
    public ResponseEntity<String> register(@RequestBody UsuarioRegisterRequestDTO registerRequest) {
        try {
            userService.criarUsuario(
                    registerRequest.nome(),
                    registerRequest.nomeUsuario(),
                    registerRequest.email(),
                    registerRequest.cargo(),
                    registerRequest.senha()
            );
            return ResponseEntity.ok("Usuário registrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
