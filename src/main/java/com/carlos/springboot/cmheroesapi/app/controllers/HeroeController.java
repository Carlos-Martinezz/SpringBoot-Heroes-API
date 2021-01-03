package com.carlos.springboot.cmheroesapi.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.carlos.springboot.cmheroesapi.app.entity.Heroe;
import com.carlos.springboot.cmheroesapi.app.services.IHeroeService;
import com.carlos.springboot.cmheroesapi.app.services.IUploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Carlos Martínez
 * @version 1
 * 
 * Controlador de las peticiones para el CRUD de los héroes
 */
@RestController
@RequestMapping( "heroes-api/" )
@CrossOrigin(origins = "*")
@Api(tags = "Héroes")
public class HeroeController {
	
	@Autowired
	private IHeroeService heroeService;
	
	@Autowired
	private IUploadService uploadService;
	
	
	/* Obtener todos los Héroes */
	@GetMapping( "getAll" )
	@ApiOperation(
			value = "Obtener todos los héroes", 
			notes = "Devuelve un array con todos los héroes disponibles."
	)
	public List<Heroe> getAll() {
		return heroeService.findAll();
	}
	
	/* Obtener un Héroe por id */
	@GetMapping( "getHeroe/{id}" )
	@ApiOperation(
			value = "Obtener héroe por ID", 
			notes = "Devuelve un único héroe, buscado mediante el ID proporcionado."
	)
	public Heroe getHeroe( @PathVariable Long id ) {
		return heroeService.findById(id);
	}
	
	/* Obtener Héroes por nombre */
	@GetMapping( "getHeroesForName/{nombre}" )
	@ApiOperation(
			value = "Obtener héroes por nombre", 
			notes = "Devuelve uno o varíos héroes, en función de la cadena (El nombre del héroe) que sea pasada como parámetro."
	)
	public List<Heroe> getHeroesForName( @PathVariable String nombre ) {
		return heroeService.findByNombre( nombre );
	}
	
	/* Guardar un Héroe */
	@PostMapping("saveHeroe/")
	@ApiOperation(
			value = "Guardar un héroe", 
			notes = "Recibe un objeto de tipo Heroe, y lo registra en la base de datos (Se debe omitir enviar el ID del héroe, ya que este es autogenerado).\n"
					+ "Tome en cuenta que los formatos de fecha aceptados son: 02/12/2020 o 2020/12/02"
	)
	public String saveHeroe(@RequestParam("nombre") String nombre, 
							@RequestParam("biografia") String biografia,
							@RequestParam("casa") String casa,
							@RequestParam("aparicion") Date aparicion,
							@RequestParam("file") MultipartFile foto,
							HttpServletResponse res) {	
		
		String nombreImagen = null;

		try {
			nombreImagen = uploadService.copy(foto);
			
			Heroe heroe = new Heroe(nombre, biografia, nombreImagen, aparicion, casa);
			heroeService.save( heroe );
			
			System.out.println(nombreImagen);
			res.setStatus(201);
			
			return "Se creó el héroe";
		} catch (IOException e) {
			System.out.println(e.getMessage());
			res.setStatus(500);
			
			return "Error al subir el héroe: ".concat(e.getMessage());
		}
		
	}
	
	/*Obtener imagen de cada héroe*/
	@GetMapping("getImage/{nombre:.+}")
	@ApiOperation(
			value = "Obtener imagen de cada héroe", 
			notes = "Recibe como parámetro el nombre de la imagen del héroe, ésta es proprocionada en cada héroe que se obtiene del servicio"
	)
	public ResponseEntity<Resource> getImage(@PathVariable String nombre) {
		
		Resource recurso = null; 
		
		try {
			recurso = uploadService.load(nombre);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
		
	}
	
	/* Obtener Héroes por Casa */
	@GetMapping("getHeroesForHome/{casa}")	
	@ApiOperation(
			value = "Obtener héroes por casa", 
			notes = "Devuelve uno o varios héroes, en función de la cadena (Casa  a la que pertenece el héroe) que sea pasada como parámetro."
	)
	public List<Heroe> getHeroesHome( @PathVariable String casa ) {
		return heroeService.findByCasa( casa );
	}
	
	/* Actualizar un Héroe */
	@PatchMapping("updateHeroe/")
	@ApiOperation(
			value = "Actualizar un héroe", 
			notes = "Actualiza la información de un héroe.\n Recibe un objeto de tipo Heroe (Es necesario especificar el ID del héroe que se desea actualizar). \nDevuelve el nuevo héroe actualizado."
	)
	@ApiResponses({
        @ApiResponse(code = 201, message = "Héroe actualizado"),
        @ApiResponse(code = 404, message = "Héroe no encontrado")
	})
	public Heroe updateHeroe(@RequestBody Heroe heroe, HttpServletResponse res) {
		
		Heroe nuevoHeroe = heroeService.findById(heroe.getId());
		
		if(nuevoHeroe == null) {
			res.setStatus(404);
			return null;
		}
		
		nuevoHeroe.setNombre(heroe.getNombre());
		nuevoHeroe.setBiografia(heroe.getBiografia());
		nuevoHeroe.setRutaImagen(heroe.getRutaImagen());
		nuevoHeroe.setAparicion(heroe.getAparicion());
		nuevoHeroe.setCasa(heroe.getCasa());
		
		heroeService.save(nuevoHeroe);
		res.setStatus(201);
		
		return nuevoHeroe;
	}
	
	@DeleteMapping("deleteHeroe/{id}")
	@ApiOperation(
			value = "Eliminar un héroe", 
			notes = "Elimina el héroe perteneciente al ID proporcionado como parámetro.\n El método devolverá el héroe que fue eliminado"
	)
	@ApiResponses({
        @ApiResponse(code = 201, message = "Héroe actualizado"),
        @ApiResponse(code = 404, message = "Héroe no encontrado")
	})
	public Heroe deleteHeroe(@PathVariable Long id) {
		
		Heroe heroeBorrar = heroeService.findById(id);
		heroeService.delete( heroeBorrar );
		
		try {
			uploadService.deleteFile(heroeBorrar.getRutaImagen());
		} catch (IOException e) {
			System.out.println("Error al borrar el archivo: ".concat(e.getMessage()));
		}
		
		return heroeBorrar;
		
	}
	
}




