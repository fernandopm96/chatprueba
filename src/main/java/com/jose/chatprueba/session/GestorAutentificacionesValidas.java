/*package com.jose.chatprueba.session;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class GestorAutentificacionesValidas implements AuthenticationSuccessHandler {

	private AlmacenUsuariosActivos usuariosActivos;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
        if (session != null) {
            UsuarioActivo usuarioActivo = new UsuarioActivo(authentication.getName(), usuariosActivos);
            session.setAttribute("user", usuarioActivo);
        }
	}

}
*/