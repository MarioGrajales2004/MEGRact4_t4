package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.EditorialResponse;
import com.example.demo.dto.LibroRequest;
import com.example.demo.dto.LibroResponse;
import com.example.demo.dto.PaginaResponse;
import com.example.demo.exception.RecursoNoEncontradoException;
import com.example.demo.model.Editorial;
import com.example.demo.model.Libro;
import com.example.demo.repository.EditorialRepository;
import com.example.demo.repository.LibroRepository;

/** Lógica del CRUD. Recibe y devuelve DTOs; las entidades no salen de esta capa. */
@Service
@Transactional(readOnly = true)
public class LibroService {

    private final LibroRepository libros;
    private final EditorialRepository editoriales;

    public LibroService(LibroRepository libros, EditorialRepository editoriales) {
        this.libros = libros;
        this.editoriales = editoriales;
    }

    public PaginaResponse<LibroResponse> listar(Pageable pageable) {
        return PaginaResponse.de(libros.findAll(pageable).map(LibroResponse::de));
    }

    public LibroResponse buscar(Long id) {
        return LibroResponse.de(obtenerLibro(id));
    }

    @Transactional
    public LibroResponse crear(LibroRequest req) {
        Libro libro = new Libro();
        copiarDatos(req, libro);
        return LibroResponse.de(libros.save(libro));
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroRequest req) {
        Libro libro = obtenerLibro(id);
        copiarDatos(req, libro);
        return LibroResponse.de(libros.save(libro));
    }

    @Transactional
    public void eliminar(Long id) {
        libros.delete(obtenerLibro(id));
    }

    public List<EditorialResponse> listarEditoriales() {
        return editoriales.findAll(Sort.by("nombre")).stream().map(EditorialResponse::de).toList();
    }

    private Libro obtenerLibro(Long id) {
        return libros.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el libro con id " + id));
    }

    private void copiarDatos(LibroRequest req, Libro libro) {
        Editorial editorial = editoriales.findById(req.editorialId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "No existe la editorial con id " + req.editorialId()));
        libro.setTitulo(req.titulo().trim());
        libro.setAutor(req.autor());
        libro.setAnio(req.anio());
        libro.setPrecio(req.precio());
        libro.setEditorial(editorial);
    }
}
