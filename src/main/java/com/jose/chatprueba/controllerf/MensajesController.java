package com.jose.chatprueba.controllerf;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.MensajeUsuario;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.services.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MensajesController {
	
	@Autowired @Lazy
	private MensajeUsuarioServices mensajeUsuarioServices;
	@Autowired @Lazy
	private UsuarioServices usuarioServices;
	@Autowired @Lazy
	private ChatServices chatServices;
	@Autowired @Lazy
	private MensajeServices mensajeServices;
	
	// Función que devuelve los mensajes de un grupo
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mensajesgrupo/{idGrupo}")
	public ResponseEntity<?> mensajesGrupo(@AuthenticationPrincipal Usuario user, @PathVariable Integer idGrupo){
		Chat chat = chatServices.buscaPorId(idGrupo).get();
		List<MensajeDTO> mensajesDTO = mensajeServices.mensajesGrupoToDTO(chat.getMensajes());
		if(!mensajesDTO.isEmpty()) {
			System.out.println(mensajesDTO);
			return ResponseEntity.status(HttpStatus.OK).body(mensajesDTO);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("No hay mensajes");
		}
	}
	
	// Función que devuelve los mensajes de una conversación privada
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mensaje/{idDestinatario}")
	public ResponseEntity<?> mensajesUsuario(@AuthenticationPrincipal Usuario user, @PathVariable Integer idDestinatario){
		Integer id_usuario = user.getId();
		Optional<List<MensajeUsuario>> mensajesUsuario = mensajeUsuarioServices.muestraMensajesUsuario(id_usuario, idDestinatario); 
		if(!mensajesUsuario.isEmpty()) {
			List<MensajeDTO> mensajes = mensajeServices.mensajesUsuarioToDTO(mensajesUsuario.get());
			return ResponseEntity.status(HttpStatus.OK).body(mensajes);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("No hay mensajes");
		}
	}
}
