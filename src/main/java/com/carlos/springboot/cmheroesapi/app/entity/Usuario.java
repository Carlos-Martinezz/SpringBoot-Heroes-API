package com.carlos.springboot.cmheroesapi.app.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

/**
 * @author Carlos Martínez
 * @version 1
 * 
 * Clase Entity para definir la estructura de un usuario
 */
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	@Id()
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(unique = true)
	private String usuario;

	@NotBlank
	private String contrasena;
	
	private String token;

	public Usuario() {

	}

	public Usuario(Long id, @NotBlank String usuario, @NotBlank String contrasena) {
		this.id = id;
		this.usuario = usuario;
		this.contrasena = contrasena;
	}
	
	public Usuario(@NotBlank String usuario, @NotBlank String contrasena) {
		this.usuario = usuario;
		this.contrasena = contrasena;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
