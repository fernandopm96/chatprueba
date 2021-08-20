package com.jose.chatprueba.controllerf;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.dto.GetUsuarioDTO;
import com.jose.chatprueba.dto.converter.UsuarioDTOConverter;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.security.jwt.JwtProvider;
import com.jose.chatprueba.security.jwt.models.*;
import com.jose.chatprueba.services.ChatServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	private final UsuarioDTOConverter usuarioDTOConverter;
//	@Autowired private SessionRegistry sessionRegistry;
	@Autowired ChatServices chatService;
	
/*	@GetMapping("auth/logout")
	public void logout(@AuthenticationPrincipal Usuario usuario) {
		sessionRegistry.removeSessionInformation(usuario.getNombre());
	}*/
	@PostMapping("/auth/login")
	public ResponseEntity<GetUsuarioDtoToken> login(@Valid @RequestBody LoginRequest loginRequest
			){
		Authentication authentication =
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginRequest.getUsername(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
//		sessionRegistry.registerNewSession("", authentication.getPrincipal());
		
		Usuario usuario = (Usuario)authentication.getPrincipal();
		
		String jwtToken = jwtProvider.generateToken(authentication);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioDTOConverter.convertToDtoWithToken(usuario, jwtToken));
	}
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user/me")
	public GetUsuarioDTO me(@AuthenticationPrincipal Usuario user) {
		return usuarioDTOConverter.convertToDTO(user);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user/chats")
	public Optional<List<Chat>> chatsUsuario(@AuthenticationPrincipal Usuario user){
		return chatService.buscaPorUsuario(user.getId());
	}
	
/*	@GetMapping("/usuariosLogeados")
	public List<Object> usuariosLogeados(){
		return sessionRegistry.getAllPrincipals();
	}*/
}
