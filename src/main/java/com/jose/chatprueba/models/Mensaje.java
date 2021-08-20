package com.jose.chatprueba.models;

import lombok.Data;
import javax.persistence.*;
import java.util.Date;

@Data
@Entity @Table(name="mensaje")
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="visto")
    private boolean visto;
    @Column(name="fecha")
    private Date fecha;
    @Column(name="texto")
    private String texto;
    @ManyToOne
    @JoinColumn(name="id_usuario")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name="id_chat")
    private Chat chat;

    public Mensaje() {
        visto = false;
    }

    public Mensaje(String texto) {
        this.fecha = new Date();
        this.texto = texto;
        visto = false;
    }
    public Mensaje(String texto, Usuario usuario, Chat chat) {
    	this.fecha = new Date();
        this.texto = texto;
        visto = false;
        this.usuario = usuario;
        this.chat = chat;
    }

    @Override
    public String toString() {
        return "Mensaje{" +
                "visto=" + visto +
                ", fecha=" + fecha +
                ", texto='" + texto + '\'' +
                ", usuario=" + usuario +
                ", chat=" + chat +
                '}';
    }
}
