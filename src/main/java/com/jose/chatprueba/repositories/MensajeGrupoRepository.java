package com.jose.chatprueba.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jose.chatprueba.models.MensajeGrupo;

public interface MensajeGrupoRepository extends JpaRepository<MensajeGrupo, Integer> {

}
