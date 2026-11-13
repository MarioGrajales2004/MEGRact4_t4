package com.example.demo.dto;

import java.util.List;

import org.springframework.data.domain.Page;

/** Respuesta paginada con un formato estable (contenido + datos de la página). */
public record PaginaResponse<T>(List<T> contenido, int pagina, int tamanio,
                                long totalElementos, int totalPaginas, boolean ultima) {

    public static <T> PaginaResponse<T> de(Page<T> page) {
        return new PaginaResponse<>(page.getContent(), page.getNumber(), page.getSize(),
                page.getTotalElements(), page.getTotalPages(), page.isLast());
    }
}
