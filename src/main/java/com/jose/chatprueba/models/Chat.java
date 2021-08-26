package com.jose.chatprueba.models;

import lombok.Data;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

@Data
@Entity @Table(name="chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="fecha")
    private Date fecha;
    @Column(name="nombre")
    private String nombre;
    @Column(name="descripcion")
    private String descripcion;
    @Column(name="foto")
    private String foto;
    @Column(name="ultima_edicion")
    private Date ultimaEdicion;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            name="participantes_chat",
            joinColumns =  @JoinColumn(name="id_chat"),
            inverseJoinColumns = @JoinColumn(name = "id_usuario")
    )
    private Set<Usuario> usuarios;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private List<MensajeGrupo> mensajes;
    
    public Chat() {
        fecha = new Date();
    }

    //Helpers
    public void agregaUsuario(Usuario u){
        if(usuarios == null) usuarios = new HashSet<Usuario>() {
        };
        usuarios.add(u);
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", fecha=" + fecha +
                '}';
    }
}
