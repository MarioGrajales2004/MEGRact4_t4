package com.example.demo.dto;

/** Respuesta del login: el token JWT que el cliente enviará en "Authorization: Bearer ...". */
public record AuthResponse(String token, String tipo, long expiraEnSegundos, UsuarioResponse usuario) {
}
