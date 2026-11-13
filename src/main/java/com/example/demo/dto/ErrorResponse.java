package com.example.demo.dto;

import java.time.LocalDateTime;
import java.util.Map;

/** Formato único para todos los errores de la API. */
public record ErrorResponse(int estado, String error, String mensaje, String ruta,
                            Map<String, String> errores, String fecha) {

    public static ErrorResponse de(int estado, String error, String mensaje, String ruta) {
        return new ErrorResponse(estado, error, mensaje, ruta, null, LocalDateTime.now().withNano(0).toString());
    }

    public static ErrorResponse validacion(String ruta, Map<String, String> errores) {
        return new ErrorResponse(400, "Bad Request", "Datos inválidos", ruta, errores,
                LocalDateTime.now().withNano(0).toString());
    }
}
