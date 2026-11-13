package com.example.demo.exception;

/** Se lanza al registrar un email que ya existe; se convierte en 409 Conflict. */
public class EmailYaRegistradoException extends RuntimeException {

    public EmailYaRegistradoException(String email) {
        super("Ya existe un usuario con el email " + email);
    }
}
