package com.jose.chatprueba.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.jose.chatprueba.models.*;
import io.jsonwebtoken.security.*;
import lombok.extern.java.Log;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

import com.jose.chatprueba.security.*;

@Log
@Component
public class JwtProvider {
	
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String TOKEN_TYPE = "JWT";
	private Key key;
	
	@Value("${jwt.secret}")
	private String jwtSecreto;
	
	@Value("${jwt.token-expiration}")
	private int jwtDurationTokenEnSegundos;
	
	@Autowired
	public void setKey(@Value("${jwt.secret}") String jwtSecreto) {
		this.key = Keys.hmacShaKeyFor(jwtSecreto.getBytes());
	}
	
	public String generateToken(Authentication authentication) {
		
		Usuario usuario = (Usuario)authentication.getPrincipal();
		
		Date tokenExpirationDate = new Date(System.currentTimeMillis() + (jwtDurationTokenEnSegundos * 1000));
		return Jwts.builder()
				.signWith(key)
				.setHeaderParam("typ", TOKEN_TYPE)
				.setSubject(Integer.toString(usuario.getId()))
				.setIssuedAt(new Date())
				.setExpiration(tokenExpirationDate)
				.claim("fullname", usuario.getNombre())
				.claim("roles",
						usuario.getRoles().stream()
						.map(UserRole::name)
						.collect(Collectors.joining(", "))
					)
				.compact();
	}
	
	public Integer getUsuarioIdFromToken(String token) {
		Claims claims = Jwts.parserBuilder()
								.setSigningKey(key)
								.build()
								.parseClaimsJws(token)
								.getBody();
		return Integer.parseInt(claims.getSubject());
	}
	
	public boolean validateToken(String authToken) {
			
			try {
				Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
				return true;
			} catch (SignatureException ex) {
				log.info("Error en la firma del token JWT: " + ex.getMessage());
			} catch (MalformedJwtException ex) {
				log.info("Token malformado: " + ex.getMessage());
			} catch (ExpiredJwtException ex) {
				log.info("El token ha expirado: " + ex.getMessage());
			} catch (UnsupportedJwtException ex) {
				log.info("Token JWT no soportado: " + ex.getMessage());
			} catch (IllegalArgumentException ex) {
				log.info("JWT claims vacío");
			}
	        return false;
		}
}
