package com.carlos.springboot.cmheroesapi.app.services;

import com.carlos.springboot.cmheroesapi.app.entity.Usuario;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * Interface para definir métodos de nuestro @Service Login
 */
public interface ILoginService {

	public Usuario findUsuario(String usuario, String contrasena);
	
	public String getJWTToken(String username);
	
	public Usuario save(Usuario usuario);
	
}
