package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Editorial;
import com.example.demo.model.Libro;
import com.example.demo.repository.EditorialRepository;
import com.example.demo.repository.LibroRepository;

/** Si la base de datos está vacía, carga editoriales y libros de ejemplo (6 libros para probar la paginación). */
@Configuration
public class DatosIniciales {

    @Bean
    CommandLineRunner cargarDatos(EditorialRepository editoriales, LibroRepository libros) {
        return args -> {
            if (editoriales.count() > 0) {
                return;
            }
            Editorial alfaguara = editoriales.save(new Editorial("Alfaguara", "España"));
            Editorial fce = editoriales.save(new Editorial("Fondo de Cultura Económica", "México"));
            Editorial planeta = editoriales.save(new Editorial("Planeta", "España"));

            libros.save(libro("Cien años de soledad", "Gabriel García Márquez", 1967, 389.0, alfaguara));
            libros.save(libro("Pedro Páramo", "Juan Rulfo", 1955, 189.0, fce));
            libros.save(libro("El laberinto de la soledad", "Octavio Paz", 1950, 245.0, fce));
            libros.save(libro("La sombra del viento", "Carlos Ruiz Zafón", 2001, 329.0, planeta));
            libros.save(libro("Aura", "Carlos Fuentes", 1962, 159.0, fce));
            libros.save(libro("Rayuela", "Julio Cortázar", 1963, 299.0, alfaguara));
        };
    }

    private static Libro libro(String titulo, String autor, int anio, double precio, Editorial editorial) {
        Libro l = new Libro();
        l.setTitulo(titulo);
        l.setAutor(autor);
        l.setAnio(anio);
        l.setPrecio(precio);
        l.setEditorial(editorial);
        return l;
    }
}
