package com.jose.chatprueba.models;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="mensajes_grupo")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MensajeGrupo extends Mensaje{
	
	@ManyToOne
	@JoinColumn(name="id_chat")
	private Chat chat;
	
	public MensajeGrupo(Mensaje mensaje, Chat chat) {
		this.id = mensaje.id;
		this.fecha = mensaje.fecha;
		this.texto = mensaje.texto;
		this.usuario = mensaje.usuario;
		this.chat = chat;
	}
	
}
