package com.jose.chatprueba.security.jwt.models;

import com.jose.chatprueba.dto.GetUsuarioDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class GetUsuarioDtoToken extends GetUsuarioDTO {
	private String token;
}
