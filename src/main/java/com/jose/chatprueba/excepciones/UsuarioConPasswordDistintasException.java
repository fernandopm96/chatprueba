package com.jose.chatprueba.excepciones;

public class UsuarioConPasswordDistintasException extends RuntimeException {

	private static final long serialVersionUID = -1421802146453704709L;
	
	public UsuarioConPasswordDistintasException() {
		super("Las contraseñas no coinciden");
	}
}
