package com.jose.chatprueba.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jose.chatprueba.security.UserRole;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.*;


@Builder
@Data @NoArgsConstructor @AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity @Table(name="usuario")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="nombre")
    private String nombre;
    @Column(name="pass")
    private String pass;
    @Column(name="mail")
    private String mail;
    @Column(name="imagen")
    private String imagen;
    @ElementCollection(fetch=FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="roles_usuario", joinColumns=@JoinColumn(name="id_usuario"))
    @Column(name="rol")
    private Set<UserRole> roles;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "usuarios")
    private List<Chat> chats;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, /*cascade = CascadeType.ALL ,*/ mappedBy = "usuario")
    private List<Mensaje> mensajes;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
    private List<BandejaEntrada> mensajesRecibidos;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "destinatario")
    private List<MensajeUsuario> mensajesPrivadosRecibidos;

    public Usuario(String nombre, String pass, String mail) {
        this.nombre = nombre;
        this.pass = pass;
        this.mail = mail;
    }
    public Usuario(String nombre, String pass, String mail, String imagen) {
    	this.nombre = nombre;
    	this.pass = pass;
    	this.mail = mail;
    	this.imagen = imagen;
    }

    //Helpers
    public void agregaChat(Chat c){
        chats.add(c);
    }

    public void agregaMensaje(Mensaje m){
        mensajes.add(m);
    }
/*    public void recibeMensaje(Mensaje m) {
    	if(mensajesRecibidos == null) {
    		mensajesRecibidos = new ArrayList<>();
    	}
    	mensajesRecibidos.add(m);
    }*/

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", pass='" + pass + '\'' +
                ", mail='" + mail + '\'' +
                '}';
    }
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.map(x -> new SimpleGrantedAuthority("ROLE_" + x.name()))
				.collect(Collectors.toList());
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return pass;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return nombre;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
