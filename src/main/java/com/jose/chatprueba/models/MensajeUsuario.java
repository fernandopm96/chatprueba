package com.jose.chatprueba.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name="mensajes_usuario")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MensajeUsuario extends Mensaje {
    
	@ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario destinatario;
	@Column(name="fecha_visto")
	private Date fechaVisto;
	
	public MensajeUsuario(Mensaje mensaje, Usuario usuario) {
		this.id = mensaje.id;
		this.fecha = mensaje.fecha;
		this.texto = mensaje.texto;
		this.usuario = mensaje.usuario;
		this.fechaVisto = null;
		this.destinatario = usuario;
	}
	public void setVisto() {
		fechaVisto = new Date();
	}
}
