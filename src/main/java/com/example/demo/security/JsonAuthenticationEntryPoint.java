package com.example.demo.security;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/** Respuesta 401 en JSON cuando se llama a un endpoint protegido sin token válido. */
@Component
public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        String json = """
                {"estado":401,"error":"Unauthorized","mensaje":"Se requiere un token JWT válido en el header Authorization: Bearer <token>","ruta":"%s","fecha":"%s"}"""
                .formatted(request.getRequestURI(), LocalDateTime.now().withNano(0));
        response.getWriter().write(json);
    }
}
