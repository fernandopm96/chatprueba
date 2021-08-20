/*package com.jose.chatprueba.session;

import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class UsuarioActivo implements HttpSessionBindingListener {
	
	private String nombre;
	@Autowired 
	AlmacenUsuariosActivos usuariosActivos;
	
	@Override
    public void valueBound(HttpSessionBindingEvent event) {
        List<String> usuarios = usuariosActivos.getUsuarios();
        UsuarioActivo usuarioActivo = (UsuarioActivo) event.getValue();
        if (!usuarios.contains(usuarioActivo.getNombre())) {
        	usuarios.add(usuarioActivo.getNombre());
        }
    }

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		List<String> usuarios = usuariosActivos.getUsuarios();
        UsuarioActivo usuarioActivo = (UsuarioActivo) event.getValue();
        if (!usuarios.contains(usuarioActivo.getNombre())) {
        	usuarios.remove(usuarioActivo.getNombre());
        }
	}
	
	
}*/
