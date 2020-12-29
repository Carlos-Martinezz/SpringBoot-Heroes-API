package com.carlos.springboot.cmheroesapi.app.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Carlos Martínez
 * @version 1
 * 
 * Clase Entity para definir la estructura de un héroe
 */
@Entity
@Table(name = "heroes")
public class Heroe implements Serializable {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String nombre;

	@NotBlank
	@Column(length = 1000)
	private String biografia;

	@Column(name = "ruta_imagen")
	private String rutaImagen;

	@NotNull
	private Date aparicion;

	@NotBlank
	private String casa;

	public Heroe() {

	}

	public Heroe(Long id, @NotBlank String nombre, @NotBlank String biografia, String rutaImagen, @NotNull Date aparicion,
			@NotBlank String casa) {
		this.id = id;
		this.nombre = nombre;
		this.biografia = biografia;
		this.rutaImagen = rutaImagen;
		this.aparicion = aparicion;
		this.casa = casa;
	}
	
	public Heroe(@NotBlank String nombre, @NotBlank String biografia, String rutaImagen, @NotNull Date aparicion,
			@NotBlank String casa) {
		this.nombre = nombre;
		this.biografia = biografia;
		this.rutaImagen = rutaImagen;
		this.aparicion = aparicion;
		this.casa = casa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getBiografia() {
		return biografia;
	}

	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}

	public Date getAparicion() {
		return aparicion;
	}

	public void setAparicion(Date aparicion) {
		this.aparicion = aparicion;
	}

	public String getCasa() {
		return casa;
	}

	public void setCasa(String casa) {
		this.casa = casa;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
