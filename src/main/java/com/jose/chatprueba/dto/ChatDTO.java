package com.jose.chatprueba.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatDTO {
	
	private int id;
//	private List<UsuarioDTO> usuarios;
	private List<MensajeDTO> mensajes;
	
}
