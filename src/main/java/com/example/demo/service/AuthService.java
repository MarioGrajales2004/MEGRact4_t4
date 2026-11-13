package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.AuthResponse;
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegistroRequest;
import com.example.demo.dto.UsuarioResponse;
import com.example.demo.exception.EmailYaRegistradoException;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.security.JwtTokenProvider;

/** Registro de usuarios y login con emisión de token JWT. */
@Service
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwt;

    public AuthService(UsuarioRepository usuarios, PasswordEncoder encoder,
                       AuthenticationManager authManager, JwtTokenProvider jwt) {
        this.usuarios = usuarios;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwt = jwt;
    }

    @Transactional
    public UsuarioResponse registrar(RegistroRequest req) {
        String email = req.email().trim().toLowerCase();
        if (usuarios.existsByEmail(email)) {
            throw new EmailYaRegistradoException(email);
        }
        Usuario u = new Usuario(req.nombre().trim(), email, encoder.encode(req.password()));
        return UsuarioResponse.de(usuarios.save(u));
    }

    /** Comprueba las credenciales (lanza BadCredentialsException si fallan) y genera el token. */
    public AuthResponse login(LoginRequest req) {
        String email = req.email().trim().toLowerCase();
        authManager.authenticate(new UsernamePasswordAuthenticationToken(email, req.password()));
        Usuario u = usuarios.findByEmail(email).orElseThrow();
        return new AuthResponse(jwt.generarToken(u), "Bearer", jwt.getExpiracionSegundos(), UsuarioResponse.de(u));
    }
}
