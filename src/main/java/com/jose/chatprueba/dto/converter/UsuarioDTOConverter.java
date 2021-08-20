package com.jose.chatprueba.dto.converter;

import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.jose.chatprueba.dto.GetUsuarioDTO;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.security.*;
import com.jose.chatprueba.security.jwt.models.GetUsuarioDtoToken;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class UsuarioDTOConverter {
	
	public GetUsuarioDTO convertToDTO(Usuario usuario) {
		return GetUsuarioDTO.builder()
				.nombre(usuario.getNombre())
				.mail(usuario.getMail())
				.imagen(usuario.getImagen())
				.roles(usuario.getRoles().stream()
						.map(UserRole::name)
						.collect(Collectors.toSet())
				)
				.build();
	}
	
	public GetUsuarioDtoToken convertToDtoWithToken(Usuario usuario, String jwtToken) {
		return GetUsuarioDtoToken
				.builder()
				.nombre(usuario.getNombre())
				.mail(usuario.getMail())
				.imagen(usuario.getImagen())
				.roles(usuario.getRoles().stream()
						.map(UserRole::name)
						.collect(Collectors.toSet())
				)
				.token(jwtToken)
				.build();
	}
}
