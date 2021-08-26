package com.jose.chatprueba.services;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.*;
import com.jose.chatprueba.repositories.MensajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MensajeServices implements IServices<Mensaje>, IMensajeServices {
	
    @Lazy @Autowired
    MensajeRepository mensajeRepository;
    @Lazy @Autowired
    UsuarioServices usuarioServices;
    @Lazy @Autowired
    ChatServices chatServices;
    @Lazy @Autowired
    MensajeUsuarioServices mensajeUsuarioServices;
    @Lazy @Autowired
    MensajeGrupoServices mensajeGrupoServices;

    //Metodos comunes a los servicios
    @Override
    public List<Mensaje> buscaTodos(){
        return mensajeRepository.findAll();
    }
    @Override
    public Optional<Mensaje> buscaPorId(Integer id) {
        return mensajeRepository.findById(id);
    }
    @Override
    public Mensaje registra(Mensaje mensaje) {
    	return mensajeRepository.save(mensaje);
    }
    @Override
    public void elimina(Mensaje mensaje) {
        mensajeRepository.delete(mensaje);
    }
    @Override
    public void elimina(Integer id) {
        mensajeRepository.deleteById(id);
    }
    @Override
	public boolean compruebaPorId(Integer id) {
		return mensajeRepository.existsById(id);
	}
	// Servicio que convierte lista de mensajes a lista de mensajesDTO
	public List<MensajeDTO> mensajesToDTO(List<Mensaje> mensajes){
		List<MensajeDTO> mensajesDTO = new ArrayList<MensajeDTO>();
		mensajes.forEach((m) -> {
			MensajeDTO mensajeDTO = MensajeDTO.builder()
										.texto(m.getTexto())
										.nombreUsuario(m.getUsuario().getNombre())
										.build();
			mensajesDTO.add(mensajeDTO);
		});
		return mensajesDTO;
	}
	
	public List<MensajeDTO> mensajesUsuarioToDTO(List<MensajeUsuario> mensajesUsuario) {
		List<MensajeDTO> mensajesUsuarioDTO = new ArrayList<>();
		mensajesUsuario.forEach(m -> {
			MensajeDTO mensajeDTO = MensajeDTO.builder()
					.nombreUsuario(m.getUsuario().getNombre())
					.texto(m.getTexto()).build();
			mensajesUsuarioDTO.add(mensajeDTO);
		});
		return mensajesUsuarioDTO;
	}
	public List<MensajeDTO> mensajesGrupoToDTO(List<MensajeGrupo> mensajesGrupo){
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
	

	// Servicios de envío de mensajes(a usuarios y a grupos)
	public void enviarMensajeUsuario(MensajeDTO mensajeDTO, Integer idDestinatario) {
		Usuario usuario = usuarioServices.buscaPorNombre(mensajeDTO.getNombreUsuario()).get();
		Usuario destinatario = usuarioServices.buscaPorId(idDestinatario).get();
		Mensaje mensaje = new Mensaje(mensajeDTO.getTexto(), usuario);
    	registra(mensaje);
		MensajeUsuario mensajeUsuario = new MensajeUsuario(mensaje, destinatario);
		mensajeUsuarioServices.registra(mensajeUsuario);
	}
	public void enviarMensajeGrupo(Integer idChat, MensajeDTO mensajeDTO) {
		Chat chat = chatServices.buscaPorId(idChat).get();
		Usuario usuario = usuarioServices.buscaPorNombre(mensajeDTO.getNombreUsuario()).get();
		Mensaje mensaje = new Mensaje(mensajeDTO.getTexto(), usuario);
		registra(mensaje);
		MensajeGrupo mensajeGrupo = new MensajeGrupo(mensaje, chat);
		mensajeGrupoServices.registra(mensajeGrupo);
	}
}
//Metodos propios de IMensajeService
/*    @Override
public Optional<List<Mensaje>> mensajesUsuarioChat(Integer id_usuario, Integer id_chat) {
    Optional<Chat> chat = chatServices.buscaPorId(id_chat);
    Hibernate.initialize(chat.get().getMensajes());
    Optional<List<Mensaje>> mensajes = Optional.of(chat.get().getMensajes());
    return mensajes;
}
	public List<Integer> mensajesEnChat(Integer idChat){
		return mensajeRepository.mensajesEnChat(idChat);
	}*/

