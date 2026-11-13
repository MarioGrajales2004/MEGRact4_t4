package com.example.demo.dto;

import com.example.demo.model.Usuario;

/** Lo que la API devuelve de un usuario: sin el password. */
public record UsuarioResponse(Long id, String nombre, String email, String rol) {

    public static UsuarioResponse de(Usuario u) {
        return new UsuarioResponse(u.getId(), u.getNombre(), u.getEmail(), u.getRol());
    }
}
