package com.jose.chatprueba.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jose.chatprueba.models.MensajeUsuario;

@Repository
public interface MensajeUsuarioRepository extends JpaRepository<MensajeUsuario, Integer>{
	@Query("SELECT mu FROM MensajeUsuario mu WHERE mu.id IN "
			+ "(SELECT mu.id FROM MensajeUsuario mu WHERE mu.usuario.id = :id_usuario AND mu.destinatario.id = :id_destinatario) "
			+ "OR mu.id IN "
			+ "(SELECT mu.id FROM MensajeUsuario mu WHERE mu.usuario.id = :id_destinatario AND mu.destinatario.id = :id_usuario) "
			+ "ORDER BY mu.fecha")
	public Optional<List<MensajeUsuario>> muestraMensajesUsuario(@Param("id_usuario")Integer id_usuario, @Param("id_destinatario")Integer id_destinatario);
}
