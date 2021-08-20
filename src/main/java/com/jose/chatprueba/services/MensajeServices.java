package com.jose.chatprueba.services;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.Mensaje;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.repositories.MensajeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MensajeServices implements IServices<Mensaje>, IMensajeServices{
    @Autowired
    MensajeRepository mensajeRepository;
    @Autowired
    UsuarioServices usuarioServices;
    @Autowired
    ChatServices chatServices;

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
    public void registra(Mensaje ... mensajes) {
        Arrays.stream(mensajes).forEach(mensajeRepository::save);
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

    //Metodos propios de IMensajeService
    @Override
    public Optional<List<Mensaje>> mensajesUsuarioChat(Integer id_usuario, Integer id_chat) {
        Optional<Chat> chat = chatServices.buscaPorId(id_chat);
        Hibernate.initialize(chat.get().getMensajes());
        Optional<List<Mensaje>> mensajes = Optional.of(chat.get().getMensajes());
        return mensajes;
    }
    @Override
    public void enviarMensaje(MensajeDTO mensajeDTO) {
    	String texto = mensajeDTO.getTexto();
    	Usuario user = usuarioServices.buscaPorNombre(mensajeDTO.getNombreUsuario()).get();
    	Chat chat = chatServices.buscaPorId(mensajeDTO.getIdChat()).get();
    	Mensaje mensaje = new Mensaje(texto, user, chat);
    	registra(mensaje);
    }
    @Override
    public Optional<List<Mensaje>> mensajesNoVistos(Integer id_usuario) {
        return mensajeRepository.mensajesNoVistos(id_usuario);
    }
	@Override
	public boolean compruebaPorId(Integer id) {
		return mensajeRepository.existsById(id);
	}
	public List<MensajeDTO> mensajesToDTO(List<Mensaje> mensajes){
		List<MensajeDTO> mensajesDTO = new ArrayList<MensajeDTO>();
		mensajes.forEach((m) -> {
			MensajeDTO mensajeDTO = MensajeDTO.builder()
										.texto(m.getTexto())
										.nombreUsuario(m.getUsuario().getNombre())
										.idChat(m.getChat().getId())
										.build();
			mensajesDTO.add(mensajeDTO);
		});
		
		return mensajesDTO;
	}
}
