package com.jose.chatprueba.controllerf;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.security.jwt.JwtProvider;
import com.jose.chatprueba.security.jwt.models.*;
import com.jose.chatprueba.services.ChatServices;
import com.jose.chatprueba.services.UsuarioServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	
	@Lazy @Autowired 
	private ChatServices chatService;
	@Lazy @Autowired
	private UsuarioServices usuarioServices;
	private final AuthenticationManager authenticationManager;
	private final JwtProvider jwtProvider;
	
	@PostMapping("/auth/login")
	public ResponseEntity<GetUsuarioDtoToken> login(@Valid @RequestBody LoginRequest loginRequest
			){
		Authentication authentication =
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(
								loginRequest.getUsername(),loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		Usuario usuario = (Usuario)authentication.getPrincipal();
		
		String jwtToken = jwtProvider.generateToken(authentication);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioServices.creaDTOToken(usuario, jwtToken));
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/user/conversaciones")
	public Optional<List<Usuario>> conversacionesUsuario(@AuthenticationPrincipal Usuario user){
		return usuarioServices.conversaciones(user.getId());
	}	
}

/*	
@PreAuthorize("isAuthenticated()")
@GetMapping("/user/me")
public GetUsuarioDTO me(@AuthenticationPrincipal Usuario user) {
	return usuarioServices.creaDTO(user);
}
*/
