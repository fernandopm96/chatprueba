package com.jose.chatprueba.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.services.DetallesUsuarioService;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;
	private final DetallesUsuarioService detallesUsuarioService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		
		try {
			
			String token = getJwtFromRequest(request);
			if(StringUtils.hasText(token) && jwtProvider.validateToken(token)) {
				
				Integer idUsuario = jwtProvider.getUsuarioIdFromToken(token);
				Usuario usuario = (Usuario)detallesUsuarioService.loadUserById(idUsuario);
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(usuario, usuario.getRoles(), usuario.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch(Exception ex) {
			log.info("No se ha podido establecer la autenticación de usuario en el contexto de seguridad.");
		}
		
		filterChain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(jwtProvider.TOKEN_HEADER);
		
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(jwtProvider.TOKEN_PREFIX)) {
			return bearerToken.substring(jwtProvider.TOKEN_PREFIX.length(), bearerToken.length());
		}
		return null;
	}

}
