package com.jose.chatprueba.repositories;

import com.jose.chatprueba.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    @Query("select u " +
    		"from Usuario u join u.chats uc " +
    		"where u.id = :idUsuario " +
    		"and uc.id = :idChat")
    Optional<Usuario> usuarioPerteneceChat(@Param("idUsuario") Integer idUsuario, @Param("idChat") Integer idChat);
    @Query("SELECT cu.id FROM Chat c JOIN c.usuarios cu WHERE c.id = :id_chat")
    public List<Integer> usuariosEnChat(@Param("id_chat") Integer id_chat);
    @Query("SELECT DISTINCT u FROM Usuario u WHERE u.id IN "
    		+ "(SELECT mu.destinatario.id FROM MensajeUsuario mu WHERE mu.usuario.id = :id_usuario) "
    		+ "OR u.id IN "
    		+ "(SELECT mu.usuario.id FROM MensajeUsuario mu WHERE mu.destinatario.id = :id_usuario)")
	public Optional<List<Usuario>> conversaciones(@Param("id_usuario") Integer id_usuario);
}
