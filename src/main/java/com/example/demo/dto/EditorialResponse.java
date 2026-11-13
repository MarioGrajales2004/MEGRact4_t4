package com.example.demo.dto;

import com.example.demo.model.Editorial;

public record EditorialResponse(Long id, String nombre, String pais) {

    public static EditorialResponse de(Editorial e) {
        return new EditorialResponse(e.getId(), e.getNombre(), e.getPais());
    }
}
