package com.jose.chatprueba.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MensajeDTO {
	private String texto;
	private String nombreUsuario;
	private int idChat;
}
