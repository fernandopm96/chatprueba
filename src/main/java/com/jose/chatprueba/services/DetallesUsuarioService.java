package com.jose.chatprueba.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jose.chatprueba.excepciones.UsuarioNotFoundException;

import lombok.RequiredArgsConstructor;

@Service("userDetailsService")
@RequiredArgsConstructor
public class DetallesUsuarioService implements UserDetailsService{

	private final UsuarioServices usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return usuarioService.buscaPorNombre(username)
					.orElseThrow(()-> new UsernameNotFoundException(username + " no encontrado"));
	}
	
	public UserDetails loadUserById(Integer id) throws UsuarioNotFoundException {
		return usuarioService.buscaPorId(id)
				.orElseThrow(() -> new UsuarioNotFoundException(id));
	}

}
