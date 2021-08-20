package com.jose.chatprueba.services;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.Mensaje;
import java.util.List;
import java.util.Optional;

public interface IMensajeServices {
    public Optional<List<Mensaje>> mensajesUsuarioChat(Integer id_usuario, Integer id_chat);
    public void enviarMensaje(MensajeDTO mensajeDTO);
    public Optional<List<Mensaje>> mensajesNoVistos(Integer id_usuario);
}
