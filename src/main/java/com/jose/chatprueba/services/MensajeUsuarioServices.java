package com.jose.chatprueba.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.MensajeUsuario;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.repositories.MensajeUsuarioRepository;
@Service
@Transactional
public class MensajeUsuarioServices {
	@Autowired
	private MensajeUsuarioRepository mensajeUsuarioRepository;
	
	public void registra(MensajeUsuario mensajeUsuario) {
		mensajeUsuarioRepository.save(mensajeUsuario);
	}
	public List<Usuario> conversaciones(Integer idUsuarioPrincipal){
		return null;
	}
	public Optional<List<MensajeUsuario>> muestraMensajesUsuario(Integer idUsuario, Integer idDestinatario){
		return mensajeUsuarioRepository.muestraMensajesUsuario(idUsuario, idDestinatario);
	}
	public List<MensajeDTO> convertToDTO(List<MensajeUsuario> mensajesUsuario) {
		List<MensajeDTO> mensajesUsuarioDTO = new ArrayList<>();
		mensajesUsuario.forEach(m -> {
			MensajeDTO mensajeDTO = MensajeDTO.builder()
					.nombreUsuario(m.getUsuario().getNombre())
					.texto(m.getTexto()).build();
			mensajesUsuarioDTO.add(mensajeDTO);
		});
		return mensajesUsuarioDTO;
	}
	public void usuarioLeeMensajes(Integer idUsuario, List<MensajeUsuario> mensajes) {
		mensajes.forEach(m ->{
			if(m.getDestinatario().getId() == idUsuario && m.getFechaVisto() == null) {
				System.out.println("Mensaje visto");
				m.setVisto();
			}
		});
	}
}
