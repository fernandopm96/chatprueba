package com.jose.chatprueba.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.chatprueba.models.MensajeGrupo;
import com.jose.chatprueba.repositories.MensajeGrupoRepository;

@Service
public class MensajeGrupoServices {
	@Autowired
	MensajeGrupoRepository mensajeGrupoRepository;
	@Autowired
	ChatServices chatServices;
	
	public void registra(MensajeGrupo mensajeGrupo) {
		mensajeGrupoRepository.save(mensajeGrupo);
	}
}
