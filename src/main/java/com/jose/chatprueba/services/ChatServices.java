package com.jose.chatprueba.services;

import com.jose.chatprueba.dto.ChatDTO;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.repositories.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ChatServices implements IServices<Chat> , IChatServices{

    @Lazy @Autowired
    ChatRepository chatRepository;
    @Lazy @Autowired
    UsuarioServices usuarioServices;
    @Lazy @Autowired
    MensajeServices mensajeServices;
    @Lazy @Autowired
    MensajeGrupoServices mensajeGrupoServices;

    //Funciones comunes a los servicios
    @Override
    public List<Chat> buscaTodos(){
        return chatRepository.findAll();
    }
    @Override
    public Optional<Chat> buscaPorId(Integer id) {
        return chatRepository.findById(id);
    }
    @Override
    public Chat registra(Chat chat) {
    	return chatRepository.save(chat);
    }
    @Override
    public void elimina(Chat chat) {
        chatRepository.delete(chat);
    }
    @Override
    public void elimina(Integer id) {
        chatRepository.deleteById(id);
    }
    @Override
	public boolean compruebaPorId(Integer id) {
		return chatRepository.existsById(id);
	}

    // Servicios relacionadas con Usuario
    public void registraUsuariosEnChat(Integer id_chat, Integer id_usuario) {
    	chatRepository.getById(id_chat).agregaUsuario(usuarioServices.buscaPorId(id_usuario).get());
    }
    public List<Chat> chatsUsuario(Integer idUsuario){
		Usuario usuario = usuarioServices.buscaPorId(idUsuario).get();
		return usuario.getChats();
	}
    
	// Servicio que convierte un chat a chatDTO
    public ChatDTO convertToDTO(Integer idChat) {
		Chat chat = buscaPorId(idChat).get();
		
		ChatDTO chatDTO = ChatDTO.builder()
				.id(chat.getId())
				.mensajes(mensajeServices.mensajesGrupoToDTO(chat.getMensajes()))
				.build();
		return chatDTO;
	}
}

/*@Override
public boolean creaChat(Integer... id_usuario) {
// TODO Auto-generated method stub
return false;
}
*/