package com.jose.chatprueba.services;

import com.jose.chatprueba.models.Usuario;

import java.util.List;

public interface IUsuarioServices {
    public Usuario buscaPorEmail(String email);
    public boolean compruebaEmail(String email);
    public boolean compruebaPassword(String email, String pass);
    public List<Usuario> buscaPorChat(Integer id_chat);
    public boolean cambiarNombre(Integer id_usuario, String nombre);
}
