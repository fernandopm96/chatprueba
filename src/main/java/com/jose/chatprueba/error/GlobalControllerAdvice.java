package com.jose.chatprueba.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.jose.chatprueba.excepciones.*;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {
	
	// Método "embudo" para las excepciones comunes
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, 
			HttpStatus status, WebRequest request){
		ApiError apiError = new ApiError(status, ex.getMessage());
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
	// Comprobación de usuario mediante la id
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<ApiError> handleUsuarioNoEncontrado(UsuarioNotFoundException ex){
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	// Comprobación de contraseña
	@ExceptionHandler(UsuarioConPasswordDistintasException.class)
	public ResponseEntity<ApiError> handlePasswordDistintas(Exception ex) {
		return buildErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage());
	}
	private ResponseEntity<ApiError> buildErrorResponseEntity(HttpStatus status, String message){
		return ResponseEntity.status(status)
				.body(ApiError.builder()
				.mensaje(message)
				.build());
	}
}
