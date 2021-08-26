package com.jose.chatprueba.services;

import com.jose.chatprueba.dto.CreateUsuarioDTO;
import com.jose.chatprueba.dto.GetUsuarioDTO;
import com.jose.chatprueba.dto.converter.UsuarioDTOConverter;
import com.jose.chatprueba.excepciones.UsuarioConPasswordDistintasException;
import com.jose.chatprueba.excepciones.UsuarioNotFoundException;
import com.jose.chatprueba.models.BandejaEntrada;
import com.jose.chatprueba.models.Chat;
import com.jose.chatprueba.models.Mensaje;
import com.jose.chatprueba.models.Usuario;
import com.jose.chatprueba.repositories.UsuarioRepository;
import com.jose.chatprueba.security.PasswordEncoderConfig;
import com.jose.chatprueba.security.UserRole;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@AllArgsConstructor
public class UsuarioServices implements IServices<Usuario>, IUsuarioServices{
	@Lazy @Autowired
	ChatServices chatServices;
	@Lazy @Autowired
	MensajeServices mensajeServices;
	@Lazy @Autowired
    UsuarioRepository usuarioRepository;
	@Lazy @Autowired
	BandejaEntradaServices bandejaEntradaServices;
    @Autowired
    private UsuarioDTOConverter usuarioConverter;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    //Metodos comunes de los servicios
    @Override
    public List<Usuario> buscaTodos(){
        return usuarioRepository.findAll();
    }
    @Override
    public Optional<Usuario> buscaPorId(Integer id) {
        return usuarioRepository.findById(id);
    }
    public Optional<Usuario> buscaPorNombre(String nombre) {
    	return usuarioRepository.buscaPorNombre(nombre);
    }
    @Override
	public Usuario registra(Usuario usuario) {
    	usuario.setPass(passwordEncoder.encode(usuario.getPass()));
    	usuario.setRoles(Stream.of(UserRole.USER).collect(Collectors.toSet()));
		return usuarioRepository.save(usuario);
	}
    public Usuario registra(CreateUsuarioDTO nuevoUsuario) {
    	if(nuevoUsuario.getPass().contentEquals(nuevoUsuario.getPass2())) {
    		
    		Usuario usuario = Usuario.builder()
    				.nombre(nuevoUsuario.getNombre())
    				.mail(nuevoUsuario.getMail())
    				.pass(passwordEncoder.encode(nuevoUsuario.getPass()))
    				.imagen(nuevoUsuario.getImagen())
    				.roles(Stream.of(UserRole.USER).collect(Collectors.toSet()))
    				.build();
    		try {
    			return usuarioRepository.save(usuario);
    		} catch(DataIntegrityViolationException ex) {
    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre o el mail ya existe");
    		}
    	} else {
    		throw new UsuarioConPasswordDistintasException();
    	}
    }
    @Override
    public void elimina(Usuario usuario) {
        usuarioRepository.delete(usuario);
    }
    @Override
    public void elimina(Integer id) {
        usuarioRepository.deleteById(id);
    }

    //Metodos propios de IUsusarioServices
    @Override
    public Usuario buscaPorEmail(String email) {
        return usuarioRepository.buscaPorEmailCompleto(email);
    }
    @Override
    public boolean compruebaEmail(String email) {
        return false;
    }
    @Override
    public boolean compruebaPassword(String email, String pass) {
        return false;
    }
    @Override
    public List<Usuario> buscaPorChat(Integer id_chat) {
        return null;
    }
    @Override
    public boolean cambiarNombre(Integer id_usuario, String nombre) {
        try{
            Usuario usuario = buscaPorId(id_usuario).get();
            usuario.setNombre(nombre);
            return true;
        }catch (Exception e) {
            System.out.println("Ha ocurrido un error cambiando el nombre");
            e.printStackTrace();
            return false;
        }
    }
	@Override
	public boolean compruebaPorId(Integer id) {
		return usuarioRepository.existsById(id);
	}
	public List<GetUsuarioDTO> buscaTodosDTO(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(usuarioConverter::convertToDTO).collect(Collectors.toList());
	}
	public GetUsuarioDTO convierteDTO(Integer id) {
		Optional<Usuario> usuario = usuarioRepository.findById(id);
		return usuario.map(u -> usuarioConverter.convertToDTO(u)).orElseThrow(() -> new UsuarioNotFoundException(id));
	}
	public GetUsuarioDTO convierteDTO(Optional<Usuario> usuario) {
		return usuario.map(u -> usuarioConverter.convertToDTO(u)).orElseThrow(() -> new UsuarioNotFoundException());
	}
	@Override
	public void registra(Usuario... t) {
		// TODO Auto-generated method stub
		
	}
	public Usuario usuarioPerteneceChat(Integer idUsuario, Integer idChat) {
		return usuarioRepository.usuarioPerteneceChat(idUsuario, idChat).orElse(null);
	}
	public List<Integer> usuariosEnChat(Integer idChat) {
		return usuarioRepository.usuariosEnChat(idChat);
	}
	public Optional<List<Usuario>> conversaciones(Integer id_usuario){
		return usuarioRepository.conversaciones(id_usuario);
	}
	/* Al acceder a un chat, se cargan los mensajes de ese chat y también los mensajes recibidos de ese usuario en la tabla 'mensajes_recibidos'. 
	Los que coincidan son los mensajes recibidos en ese chat no leídos por el usuario, que pasarán a visto = true*/
/*	public void usuarioAccedeChat(Integer idUsuario, Integer idChat) {
		Optional<List<BandejaEntrada>> mensajesNoVistos = bandejaEntradaServices.mensajesNoVistos(idUsuario, idChat);
		if(mensajesNoVistos.isPresent()) {
			for(BandejaEntrada m:mensajesNoVistos.get()) {
				m.setVisto(true);
			}
		}
	}*/
}
