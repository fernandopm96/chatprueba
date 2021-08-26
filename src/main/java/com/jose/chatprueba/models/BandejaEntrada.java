package com.jose.chatprueba.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity @Table(name="bandeja_entrada")
public class BandejaEntrada {
	public BandejaEntrada(){
		
	}
	public BandejaEntrada(Mensaje mensaje, Usuario usuario) {
		this.mensaje = mensaje;
		this.usuario = usuario;
		visto = false;
		fecha_visto = new Date();
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
	@ManyToOne
	@JoinColumn(name = "id_mensaje")
	private Mensaje mensaje;
	@ManyToOne
    @JoinColumn(name="id_usuario")
	private Usuario usuario;
	@Column(name = "visto")
	private boolean visto;
	@Column(name = "fecha_visto")
	private Date fecha_visto;
}
