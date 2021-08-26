package com.jose.chatprueba.controllerf;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.dto.converter.UsuarioDTOConverter;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.MensajeUsuario;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.security.jwt.JwtProvider;
import com.jose.chatprueba.services.ChatServices;
import com.jose.chatprueba.services.MensajeGrupoServices;
import com.jose.chatprueba.services.MensajeUsuarioServices;
import com.jose.chatprueba.services.UsuarioServices;

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
	private MensajeGrupoServices mensajeGrupoServices;
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mensajesgrupo/{idGrupo}")
	public ResponseEntity<?> mensajesGrupo(@AuthenticationPrincipal Usuario user, @PathVariable Integer idGrupo){
		Chat chat = chatServices.buscaPorId(idGrupo).get();
		List<MensajeDTO> mensajesDTO = mensajeGrupoServices.convertToDTO(chat.getMensajes());
		if(!mensajesDTO.isEmpty()) {
			System.out.println(mensajesDTO);
			return ResponseEntity.status(HttpStatus.OK).body(mensajesDTO);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("No hay mensajes");
		}
	}
	
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/mensaje/{idDestinatario}")
	public ResponseEntity<?> mensajesUsuario(@AuthenticationPrincipal Usuario user, @PathVariable Integer idDestinatario){
		Integer id_usuario = user.getId();
		Optional<List<MensajeUsuario>> mensajesUsuario = mensajeUsuarioServices.muestraMensajesUsuario(id_usuario, idDestinatario); 
		if(!mensajesUsuario.isEmpty()) {
			mensajeUsuarioServices.usuarioLeeMensajes(id_usuario, mensajesUsuario.get());
			List<MensajeDTO> mensajes = mensajeUsuarioServices.convertToDTO(mensajesUsuario.get());
			return ResponseEntity.status(HttpStatus.OK).body(mensajes);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("No hay mensajes");
		}
	}
}
