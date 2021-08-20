package com.jose.chatprueba.controllerf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jose.chatprueba.dto.MensajeDTO;
import com.jose.chatprueba.models.MensajeSimple;
import com.jose.chatprueba.services.MensajeServices;

@Controller
public class WebSocketController {

/*	@MessageMapping("/grupos")
	@SendTo("/broker/mensajes")
	public String mensajeSimple(String mensaje) {
		System.out.println("Mensaje recibido: " + mensaje);
		return mensaje;
	}*/
	@Autowired
	private MensajeServices mensajeServices;
	
	@MessageMapping("/grupos/{grupoId}")
	@SendTo("/broker/mensajes/{grupoId}")
	public String mensajeSimple(@RequestBody MensajeDTO mensaje) {
		
		System.out.println("Mensaje recibido en el grupo " + mensaje.getIdChat());
		System.out.println("Enviado por " + mensaje.getNombreUsuario());
		System.out.println(mensaje.getTexto());
		mensajeServices.enviarMensaje(mensaje);
		return mensaje.getTexto();
	}
}
