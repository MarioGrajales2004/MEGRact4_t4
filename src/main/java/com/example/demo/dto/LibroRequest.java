package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

/** Datos que recibe la API para crear o actualizar un libro. */
public record LibroRequest(

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 150, message = "El título no puede pasar de 150 caracteres")
        String titulo,

        @Size(max = 100, message = "El autor no puede pasar de 100 caracteres")
        String autor,

        @Min(value = 1450, message = "El año debe ser 1450 o posterior")
        @Max(value = 2100, message = "El año no es válido")
        Integer anio,

        @PositiveOrZero(message = "El precio no puede ser negativo")
        Double precio,

        @NotNull(message = "El id de la editorial es obligatorio")
        Long editorialId) {
}
