package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.EditorialResponse;
import com.example.demo.dto.LibroRequest;
import com.example.demo.dto.LibroResponse;
import com.example.demo.dto.PaginaResponse;
import com.example.demo.service.LibroService;

import jakarta.validation.Valid;

/** CRUD REST de libros. Todas estas rutas exigen un JWT válido. */
@RestController
@RequestMapping("/api")
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    /** GET /api/libros?page=0&size=5&sort=titulo,asc  ->  200 OK con la página pedida. */
    @GetMapping("/libros")
    public ResponseEntity<PaginaResponse<LibroResponse>> listar(
            @PageableDefault(size = 5, sort = "titulo", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(libroService.listar(pageable));
    }

    /** 200 OK o 404 Not Found. */
    @GetMapping("/libros/{id}")
    public ResponseEntity<LibroResponse> buscar(@PathVariable Long id) {
        return ResponseEntity.ok(libroService.buscar(id));
    }

    /** 201 Created con la ruta del nuevo libro en el header Location; 400 si no pasa la validación. */
    @PostMapping("/libros")
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest req) {
        LibroResponse creado = libroService.crear(req);
        return ResponseEntity.created(URI.create("/api/libros/" + creado.id())).body(creado);
    }

    /** 200 OK, 400 Bad Request o 404 Not Found. */
    @PutMapping("/libros/{id}")
    public ResponseEntity<LibroResponse> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequest req) {
        return ResponseEntity.ok(libroService.actualizar(id, req));
    }

    /** 204 No Content o 404 Not Found. */
    @DeleteMapping("/libros/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        libroService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    /** Lista de editoriales, para saber qué editorialId usar al crear un libro. */
    @GetMapping("/editoriales")
    public ResponseEntity<List<EditorialResponse>> editoriales() {
        return ResponseEntity.ok(libroService.listarEditoriales());
    }
}
