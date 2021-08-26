package com.jose.chatprueba.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.MensajeGrupo;
import com.jose.chatprueba.repositories.MensajeGrupoRepository;

@Service
public class MensajeGrupoServices {
	@Autowired
	MensajeGrupoRepository mensajeGrupoRepository;
	@Autowired
	ChatServices chatServices;
	
	public List<MensajeDTO> convertToDTO(List<MensajeGrupo> mensajesGrupo){
		List<MensajeDTO> mensajesGrupoDTO = new ArrayList<>();
		mensajesGrupo.forEach(m -> {
			MensajeDTO mensajeDTO = MensajeDTO.builder()
					.nombreUsuario(m.getUsuario().getNombre())
					.texto(m.getTexto())
					.build();
			mensajesGrupoDTO.add(mensajeDTO);
		});
		return mensajesGrupoDTO;
	}
	public void registra(MensajeGrupo mensajeGrupo) {
		mensajeGrupoRepository.save(mensajeGrupo);
	}
}
