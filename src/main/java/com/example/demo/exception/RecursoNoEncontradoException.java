package com.example.demo.exception;

/** Se lanza cuando un id no existe; el manejador global la convierte en 404. */
public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
