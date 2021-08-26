package com.jose.chatprueba.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jose.chatprueba.models.BandejaEntrada;
import com.jose.chatprueba.models.Mensaje;

public interface BandejaEntradaRepository extends JpaRepository<BandejaEntrada, Integer> {
/*	@Query("SELECT b FROM BandejaEntrada b JOIN b.mensaje bm JOIN b.usuario bu JOIN bm.chat bmc WHERE bu.id = :id_usuario AND b.visto = false AND bmc.id = :id_chat")
	Optional<List<BandejaEntrada>> mensajesNoVistos(@Param("id_usuario") Integer id_usuario, @Param("id_chat") Integer id_chat);*/
}
