package com.jose.chatprueba.repositories;

import com.jose.chatprueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    @Query("SELECT u from Usuario u WHERE u.nombre = :nombre")
    Optional<Usuario> buscaPorNombre(@Param("nombre") String nombre);
    @Query("select u from Usuario u where u.mail = :mail")
    Usuario buscaPorEmail(@Param("mail") String mail);
    @Query("select a " +
            "from Usuario a " +
            "inner join fetch a.chats b " +
            "join fetch b.usuarios c " +
            "where a.mail = :mail " +
            "and c.id <> a.id")
    Usuario buscaPorEmailCompleto(@Param("mail") String mail);
}
