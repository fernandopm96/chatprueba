package com.jose.chatprueba.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UsuarioNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2083153308365259001L; 

	public UsuarioNotFoundException() {
		super("El usuario no existe");
	}
	public UsuarioNotFoundException(Integer id) {
		super("El usuario con la ID " + id + " no existe.");
	} 
}
