package com.jose.chatprueba.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.jose.chatprueba.models.MensajeUsuario;
import com.jose.chatprueba.repositories.MensajeUsuarioRepository;

@Service
@Transactional
public class MensajeUsuarioServices {
	@Lazy @Autowired
	private MensajeUsuarioRepository mensajeUsuarioRepository;
	
	// Registro de mensaje recibido en la tabla puente 'mensajes_usuario'(id_mensaje, id_destinatario)
	public void registra(MensajeUsuario mensajeUsuario) {
		mensajeUsuarioRepository.save(mensajeUsuario);
	}
	/* Servicio que muestra todos los mensajes privados entre el usuario principal y 
	el usuario al que se ha suscrito en la conversación. */
	public Optional<List<MensajeUsuario>> muestraMensajesUsuario(Integer idUsuario, Integer idDestinatario){
		return mensajeUsuarioRepository.muestraMensajesUsuario(idUsuario, idDestinatario);
	}
}
/*	public void usuarioLeeMensajes(Integer idUsuario, List<MensajeUsuario> mensajes) {
mensajes.forEach(m ->{
	if(m.getDestinatario().getId() == idUsuario && m.getFechaVisto() == null) {
		System.out.println("Mensaje visto");
		m.setVisto();
	}
});
}*/
