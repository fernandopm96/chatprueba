package com.jose.chatprueba.controllerf;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.dto.ChatDTO;
import com.jose.chatprueba.error.ApiError;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.services.ChatServices;
import com.jose.chatprueba.services.MensajeServices;
import com.jose.chatprueba.services.UsuarioServices;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ChatController {
	
	private UsuarioServices usuarioService;
	private ChatServices chatServices;
	private MensajeServices mensajeServices;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/chat/{idChat}")
	public ResponseEntity<?> mensajesChat(@AuthenticationPrincipal Usuario user, @PathVariable String idChat){
		Integer id = Integer.valueOf(idChat);
		if(usuarioService.usuarioPerteneceChat(user.getId(), id) != null) {
			ChatDTO chatDTO = chatServices.convertToDTO(id);
		//	usuarioService.usuarioAccedeChat(user.getId(), id);
			System.out.println(chatDTO);
			return ResponseEntity.ok(chatDTO);
		} else {
			ApiError error = ApiError.builder().estado(HttpStatus.FORBIDDEN).mensaje("Acceso denegado.").build();
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
		}
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user/chats")
	public ResponseEntity<?> chatsUsuario(@AuthenticationPrincipal Usuario user){
		List<Chat> chats = chatServices.chatsUsuario(user.getId());
		System.out.println(chats);
		return ResponseEntity.status(HttpStatus.OK).body(chats);
	}
	@PreAuthorize("isAuthenticated()")
	@PutMapping("/user/chats/nuevoparticipante/{idNuevoPartipante}")
	public String nuevoUsuarioEnChat(@RequestBody Integer idChat,
			@PathVariable Integer idNuevoPartipante){
		if(usuarioService.usuarioPerteneceChat(idNuevoPartipante, idChat) == null) {
			chatServices.registraUsuariosEnChat(idChat, idNuevoPartipante);
			return "Usuario añadido";

		} else {
			return "El usuario ya pertenece a ese chat";
		}
	}
}
