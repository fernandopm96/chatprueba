package com.jose.chatprueba.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Inheritance(strategy = InheritanceType.JOINED)
@Entity @Table(name="mensaje")
@AllArgsConstructor
@Builder
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    protected int id;
    @Column(name="fecha")
    protected Date fecha;
    @Column(name="texto")
    protected String texto;
    @ManyToOne
    @JoinColumn(name="id_usuario")
    protected Usuario usuario;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mensaje")
    private List<BandejaEntrada> destinatarios;

    public Mensaje() {
    }

    public Mensaje(String texto) {
        this.fecha = new Date();
        this.texto = texto;
    }
    public Mensaje(String texto, Usuario usuario) {
    	this.fecha = new Date();
        this.texto = texto;
        this.usuario = usuario;
    }
 /*   public void enviaMensaje(Usuario u) {
    	if(destinatarios == null) {
    		destinatarios = new ArrayList<>();
    	}
    	destinatarios.add(u);
    }*/
    @Override
    public String toString() {
        return "Mensaje{" +
                ", fecha=" + fecha +
                ", texto='" + texto + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
