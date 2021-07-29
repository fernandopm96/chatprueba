package com.jose.chatprueba.repositories;

import com.jose.chatprueba.models.Mensaje;
import com.jose.chatprueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Integer> {
//    @Query("select m " +
//            "from Mensaje m " +
//            "where m.visto = false " +
//            "and m.usuario.id <> :id_usuario " +
//            "and m.chat.id IN " +
//                "(select uc.id " +
//                "from Usuario u join fetch u.chats uc " +
//                "where u.id = :id_usuario ) " +
//            "")
    @Query("select m " +
            "from Usuario u join u.chats uc join Mensaje m " +
            "on m.chat.id = uc.id " +
            "where u.id = :id_usuario " +
            "and m.visto = false " +
            "and m.usuario.id <> :id_usuario " +
            "")
    Optional<List<Mensaje>> mensajesNoVistos(@Param("id_usuario") Integer id_usuario);
}

