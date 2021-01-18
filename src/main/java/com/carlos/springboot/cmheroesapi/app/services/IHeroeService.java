package com.carlos.springboot.cmheroesapi.app.services;

import java.util.List;

import com.carlos.springboot.cmheroesapi.app.entity.Heroe;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * Interface para definir métodos de nuestro @Service de héroes
 */
public interface IHeroeService {
	
	public List<Heroe> findAll();
	
	public Heroe findById( Long id );
	
	public List<Heroe> findByNombre( String nombre );
	
	public void save( Heroe heroe );
	
	public List<Heroe> findByCasa( String casa );
	
	public void delete( Heroe heroe );
	
	public List<String> getCasas();
}



