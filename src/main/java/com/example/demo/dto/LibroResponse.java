package com.example.demo.dto;

import com.example.demo.model.Libro;

/** Lo que la API devuelve de un libro: datos planos, con el nombre de su editorial. */
public record LibroResponse(Long id, String titulo, String autor, Integer anio, Double precio,
                            Long editorialId, String editorialNombre) {

    public static LibroResponse de(Libro l) {
        return new LibroResponse(l.getId(), l.getTitulo(), l.getAutor(), l.getAnio(), l.getPrecio(),
                l.getEditorial().getId(), l.getEditorial().getNombre());
    }
}
