package com.carlos.springboot.cmheroesapi.app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carlos.springboot.cmheroesapi.app.dao.IHeroeDao;
import com.carlos.springboot.cmheroesapi.app.entity.Heroe;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * Clase @Service para gestionar héroes
 */
@Service
public class HeroeServiceImpl implements IHeroeService {

	@Autowired
	private IHeroeDao heroeDao;
	
	@Override
	@Transactional( readOnly = true )
	public List<Heroe> findAll() {
		return (List<Heroe>) heroeDao.findAll();
	}

	@Override
	@Transactional( readOnly = true )
	public Heroe findById( Long id ) {
		return heroeDao.findById(id).orElse(null);
	}

	@Override
	@Transactional( readOnly = true )
	public List<Heroe> findByNombre( String nombre ) {
		return heroeDao.findByNombreContaining( nombre );
	}

	@Override
	@Transactional
	public void save(Heroe heroe) {
		heroeDao.save( heroe );
	}

	@Override
	@Transactional( readOnly = true )
	public List<Heroe> findByCasa(String casa) {
		return heroeDao.findByCasa( casa );
	}

	@Override
	@Transactional
	public void delete(Heroe heroe) {
		heroeDao.delete( heroe );
	}

}
