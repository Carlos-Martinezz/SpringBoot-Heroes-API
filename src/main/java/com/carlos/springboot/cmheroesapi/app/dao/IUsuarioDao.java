package com.carlos.springboot.cmheroesapi.app.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.carlos.springboot.cmheroesapi.app.entity.Usuario;

/**
 * @author Carlos Martínez
 * @version 1
 * 
 * DAO interface - para proveer los métodos de CrudRepository<>
 */
public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	@Query("select u from Usuario u where u.usuario = ?1 and u.contrasena = ?2")
	public Usuario findUsuario(String usuario, String contrasena);
	
	public Usuario findUsuarioByUsuario(String usuario);
	
}
