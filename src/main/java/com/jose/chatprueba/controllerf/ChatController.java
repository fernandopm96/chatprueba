package com.jose.chatprueba.controllerf;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.dto.ChatDTO;
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
	private ChatServices chatService;
	private MensajeServices mensajeServices;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/chat/{idChat}")
	public ChatDTO mensajesChat(@AuthenticationPrincipal Usuario user, @PathVariable String idChat){
		Integer id = Integer.valueOf(idChat);
		ChatDTO chatDTO = chatService.convertToDTO(id);
		System.out.println(chatDTO);
		return chatDTO;
	}
}
