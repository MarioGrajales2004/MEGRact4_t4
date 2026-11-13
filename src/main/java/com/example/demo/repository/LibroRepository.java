package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Libro;

/** JpaRepository ya incluye findAll(Pageable) para la paginación. */
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
