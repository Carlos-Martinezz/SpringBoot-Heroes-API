package com.carlos.springboot.cmheroesapi.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.carlos.springboot.cmheroesapi.app.entity.Heroe;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * DAO interface - para proveer los métodos de CrudRepository<>
 */
public interface IHeroeDao extends CrudRepository<Heroe, Long> {

	public List<Heroe> findByNombreContaining( String nombre );
	
	public List<Heroe> findByCasa( String casa );
	
}
