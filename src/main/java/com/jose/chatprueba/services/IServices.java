package com.jose.chatprueba.services;

import java.util.List;
import java.util.Optional;

public interface IServices<T> {
	public boolean compruebaPorId(Integer id);
    public List<T> buscaTodos();
    public Optional<T> buscaPorId(Integer id);
    public void registra(T ... t);
    public T registra(T t);
    public void elimina(T t);
    public void elimina(Integer id);

}
