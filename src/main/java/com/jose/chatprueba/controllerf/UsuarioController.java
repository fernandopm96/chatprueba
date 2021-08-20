package com.jose.chatprueba.controllerf;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.jose.chatprueba.dto.CreateUsuarioDTO;
import com.jose.chatprueba.dto.GetUsuarioDTO;
import com.jose.chatprueba.dto.converter.UsuarioDTOConverter;
import com.jose.chatprueba.excepciones.UsuarioNotFoundException;
import com.jose.chatprueba.models.*;
import com.jose.chatprueba.services.*;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class UsuarioController {

	private UsuarioServices usuarioService;
	private ChatServices chatService;
	private MensajeServices mensajeService;
	private IFicheroService iFicheroService;
	private UsuarioDTOConverter dtoConverter;

	@GetMapping("/usuario")
	public ResponseEntity<List<GetUsuarioDTO>> usuarios(){
		return ResponseEntity.ok(usuarioService.buscaTodosDTO());	
	}
	@GetMapping("/usuario/{id}")
	public ResponseEntity<GetUsuarioDTO> usuario(@PathVariable Integer id) {
		return ResponseEntity.ok(usuarioService.convierteDTO(id));
	}
	@PostMapping(value="/usuario", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<GetUsuarioDTO> registraUsuario(@RequestPart("nuevo") CreateUsuarioDTO nuevoUsuario,
			@RequestPart("file") MultipartFile file){
		String urlImagen = null;
		
		if(!file.isEmpty()) {
			String imagen = iFicheroService.store(file);
			urlImagen = MvcUriComponentsBuilder
					.fromMethodName(FicherosController.class, "serveFile", imagen)
					.build().toUriString();
		}
		nuevoUsuario.setImagen(urlImagen);
		return ResponseEntity.status(HttpStatus.CREATED).body(
					dtoConverter.convertToDTO(usuarioService.registra(nuevoUsuario)));
	}
	@PutMapping("/usuario/{id}")
	public Usuario editarUsuario(@RequestBody Usuario usuarioActualizado, @PathVariable Integer id) {
		return usuarioService.buscaPorId(id).map(u -> {
			u.setNombre(usuarioActualizado.getNombre());
			u.setPass(usuarioActualizado.getPass());
			u.setMail(usuarioActualizado.getMail());
			return usuarioService.registra(u);
			}).orElseThrow(() -> new UsuarioNotFoundException(id));
	}
	@DeleteMapping("/usuario/{id}")
	public ResponseEntity borrarUsuario(@PathVariable Integer id) {
		Usuario u = usuarioService.buscaPorId(id).orElseThrow(() -> new UsuarioNotFoundException(id));
		usuarioService.elimina(u);
		return ResponseEntity.noContent().build();
	}	
}