package com.jose.chatprueba.controllerf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.services.MensajeServices;

@Controller
public class WebSocketController {

	@Autowired
	private MensajeServices mensajeServices;
	
	@MessageMapping("/grupos/{grupoId}")
	@SendTo("/broker/mensajes/{grupoId}")
	public String mensajeSimple(@RequestBody MensajeDTO mensaje, @DestinationVariable Integer grupoId) {
		mensajeServices.enviarMensajeGrupo(grupoId, mensaje);
		return mensaje.getTexto();
	}
	@MessageMapping("/mensajesprivados/{idUsuario}")
	@SendTo("/broker/mensajesprivados/{idUsuario}")
	public String mensajeUsuario(@RequestBody MensajeDTO mensaje, @DestinationVariable Integer idUsuario) {
		System.out.println("Enviado por " + mensaje.getNombreUsuario());
		System.out.println(mensaje.getTexto());
		mensajeServices.enviarMensajeUsuario(mensaje, idUsuario);
		return mensaje.getTexto();
	}	
}
