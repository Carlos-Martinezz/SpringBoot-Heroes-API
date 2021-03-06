package com.carlos.springboot.cmheroesapi.app.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carlos.springboot.cmheroesapi.app.entity.Usuario;
import com.carlos.springboot.cmheroesapi.app.services.ILoginService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author Carlos Martínez
 * @version 1
 * 
 * Controlador para autenticación de usuarios
 */
@RestController
@RequestMapping("heroes-api/")
@CrossOrigin(origins = "*")
@Api(tags = "Seguridad")
public class LoginController {
	
	@Autowired
	private ILoginService loginService;
	
	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); 
	
	@PostMapping("login")
	@ApiOperation(
			value = "Autenticar usuario", 
			notes = "Recibe las credenciales {usuario, contraseña} para autenticar el usuario y devolver un token de acceso."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "El usuario existe y fue autenticado."),
        @ApiResponse(code = 404, message = "El usuario no fue encontrado")
	})
	public Usuario login(@RequestParam("usuario") String usuario, @RequestParam("contrasena") String contrasena, HttpServletResponse res) {
		
		Usuario buscarUsuario = loginService.findUsuarioByUsuario(usuario);
		
		if(buscarUsuario == null) {
			Usuario usuarioRes = new Usuario();
			usuarioRes.setUsuario(usuario);
			usuarioRes.setContrasena(null);
			usuarioRes.setToken("No encontramos ningún usuario con las credenciales que proporcionaste.");
			
			res.setStatus(404);
			
			return usuarioRes;
		}
		
		boolean isMatch = passwordEncoder.matches(contrasena, buscarUsuario.getContrasena());

		if(isMatch == false) {
			Usuario usuarioRes = new Usuario();
			usuarioRes.setUsuario(usuario);
			usuarioRes.setContrasena(null);
			usuarioRes.setToken("No encontramos ningún usuario con las credenciales que proporcionaste.");
			
			res.setStatus(404);
			
			return usuarioRes;
		}
		
		
		String token = loginService.getJWTToken(buscarUsuario.getUsuario());
		
		buscarUsuario.setUsuario(usuario);
		buscarUsuario.setContrasena(null);
		buscarUsuario.setToken(token);	
		
		res.setStatus(200);
		
		return buscarUsuario;
	}
	
	@PostMapping("crearUsuario")
	@ApiOperation(
			value = "Crear un usuario", 
			notes = "Recibe las credenciales {usuario, contraseña} para crear un usuario."
	)
	@ApiResponses({
        @ApiResponse(code = 201, message = "El usuario fue creado."),
        @ApiResponse(code = 409, message = "El usuario ya existe.")
	})
	public Usuario crearUsuario(@RequestParam("usuario") String usuario, @RequestParam("contrasena") String contrasena, HttpServletResponse res) {
		
		Usuario nuevoUsuario = loginService.findUsuarioByUsuario(usuario);
		
		if(nuevoUsuario == null) {
			
			Usuario nuevo = new Usuario();
			nuevo.setUsuario(usuario);
			nuevo.setContrasena(passwordEncoder.encode(contrasena));
			
			res.setStatus(201);
			
			Usuario saveUsuario = loginService.save(nuevo);
			saveUsuario.setContrasena("");
			saveUsuario.setToken("Se creó el usuario.");
			return saveUsuario;
		}
		
		nuevoUsuario = new Usuario(usuario, null);
		nuevoUsuario.setToken("El usuario ya existe.");
		res.setStatus(409);
		
		return nuevoUsuario;
	}
	
	@PutMapping("actualizarUsuario")
	@ApiOperation(
			value = "Actualizar un usuario", 
			notes = "Recibe las credenciales {usuario, contraseña y nueva contraseña} para actualizar un usuario."
	)
	@ApiResponses({
        @ApiResponse(code = 200, message = "El usuario fue actualizado."),
        @ApiResponse(code = 404, message = "El usuario no fue encontrado.")
	})
	public Usuario actualizarUsuario(@RequestParam("usuario") String usuario, 
									 @RequestParam("contrasena") String contrasena,
									 @RequestParam("nuevaContrasena") String nuevaContrasena,
									 HttpServletResponse res) {
		
		Usuario buscarUsuario = loginService.findUsuarioByUsuario(usuario);
		
		if(buscarUsuario != null) {
			
			boolean isMatch = passwordEncoder.matches(contrasena, buscarUsuario.getContrasena());
			
			if( isMatch == true ) {
				
				buscarUsuario.setContrasena(passwordEncoder.encode(nuevaContrasena));
				
				res.setStatus(200);
				Usuario usuarioActualizado = loginService.save(buscarUsuario);
				usuarioActualizado.setToken("La contraseña fue actualizada.");
				usuarioActualizado.setContrasena("");
				
				return usuarioActualizado;
			
			} else {
				res.setStatus(404);
				Usuario resUsuario = new Usuario(null, null);
				resUsuario.setToken("No encontramos ningún usuario con las credenciales que proporcionaste.");
				return resUsuario;
			}
			
		}
		
		res.setStatus(404);
		Usuario resUsuario = new Usuario(null, null);
		resUsuario.setToken("No encontramos ningún usuario con las credenciales que proporcionaste.");
		return resUsuario;
		
	}

}
