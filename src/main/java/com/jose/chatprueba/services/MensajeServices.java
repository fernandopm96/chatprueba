package com.jose.chatprueba.services;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.BandejaEntrada;
import com.jose.chatprueba.models.Mensaje;
import com.jose.chatprueba.models.MensajeGrupo;
import com.jose.chatprueba.models.MensajeUsuario;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.repositories.MensajeRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class MensajeServices implements IServices<Mensaje>, IMensajeServices{
    @Autowired
    MensajeRepository mensajeRepository;
    @Lazy
    @Autowired
    UsuarioServices usuarioServices;
    @Lazy
    @Autowired
    ChatServices chatServices;
    @Lazy @Autowired
    BandejaEntradaServices bandejaEntradaServices;
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
/*    @Override
    public Optional<List<Mensaje>> mensajesUsuarioChat(Integer id_usuario, Integer id_chat) {
        Optional<Chat> chat = chatServices.buscaPorId(id_chat);
        Hibernate.initialize(chat.get().getMensajes());
        Optional<List<Mensaje>> mensajes = Optional.of(chat.get().getMensajes());
        return mensajes;
    }*/
    
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
										.build();
			mensajesDTO.add(mensajeDTO);
		});
		return mensajesDTO;
	}
	@Override
  /*  public void enviarMensajeGrupo(MensajeGrupoDTO mensajeGrupoDTO) {
    	String texto = mensajeGrupoDTO.getTexto();
    	Usuario user = usuarioServices.buscaPorNombre(mensajeGrupoDTO.getNombreUsuario()).get();
    	Chat chat = chatServices.buscaPorId(mensajeGrupoDTO.getIdChat()).get();
    	
    	Mensaje mensaje = new Mensaje(texto, user, chat);
    	registra(mensaje);
      	Mensaje mensajeEnviado = buscaPorId(mensaje.getId()).get();
    	List<Integer> idUsuarios = usuarioServices.usuariosEnChat(chat.getId());
    	idUsuarios.forEach(id -> {
    		if(user.getId()!=id) {
    			MensajeUsuario mensajeUsuario = new MensajeUsuario(mensajeEnviado, usuarioServices.buscaPorId(id).get());
    			mensajeUsuarioServices.registra(mensajeUsuario);
    		}
    	});	
    }*/
	public void enviarMensajeUsuario(MensajeDTO mensajeDTO, Integer idDestinatario) {
		Usuario usuario = usuarioServices.buscaPorNombre(mensajeDTO.getNombreUsuario()).get();
		Usuario destinatario = usuarioServices.buscaPorId(idDestinatario).get();
		Mensaje mensaje = new Mensaje(mensajeDTO.getTexto(), usuario);
    	registra(mensaje);
		MensajeUsuario mensajeUsuario = new MensajeUsuario(mensaje, destinatario);
		mensajeUsuarioServices.registra(mensajeUsuario);
	}

/*	public List<Integer> mensajesEnChat(Integer idChat){
		return mensajeRepository.mensajesEnChat(idChat);
	}*/
	public void enviarMensajeGrupo(Integer idChat, MensajeDTO mensajeDTO) {
		Chat chat = chatServices.buscaPorId(idChat).get();
		Usuario usuario = usuarioServices.buscaPorNombre(mensajeDTO.getNombreUsuario()).get();
		Mensaje mensaje = new Mensaje(mensajeDTO.getTexto(), usuario);
		registra(mensaje);
		MensajeGrupo mensajeGrupo = new MensajeGrupo(mensaje, chat);
		mensajeGrupoServices.registra(mensajeGrupo);
	}
}
