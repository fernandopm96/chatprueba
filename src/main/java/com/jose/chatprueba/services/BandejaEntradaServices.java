package com.jose.chatprueba.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jose.chatprueba.models.BandejaEntrada;
import com.jose.chatprueba.repositories.BandejaEntradaRepository;

@Service
@Transactional
public class BandejaEntradaServices {
	@Autowired
	private BandejaEntradaRepository bandejaEntradaRepository;
	
	public void registra(BandejaEntrada bandejaEntrada) {
		bandejaEntradaRepository.save(bandejaEntrada);
	}
/*	public Optional<List<BandejaEntrada>> mensajesNoVistos(Integer idUsuario, Integer idChat){
		return bandejaEntradaRepository.mensajesNoVistos(idUsuario, idChat);
	}*/
}
