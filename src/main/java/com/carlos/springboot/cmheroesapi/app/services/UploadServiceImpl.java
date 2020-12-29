package com.carlos.springboot.cmheroesapi.app.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * Clase @Service para gestionar las imagenes de los héroes
 */
@Service
public class UploadServiceImpl implements IUploadService {
	
	private final static String UPLOADS_FOLDER = "uploads";

	@Override
	public String copy(MultipartFile file) throws IOException {
		
		//Guardamos imagen con un nombre unico mas el nombre del archivo
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);
		
		
		Files.copy(file.getInputStream(), rootPath);

		return uniqueFilename;
	}
	
	@Override
	public Resource load(String filename) throws MalformedURLException {
		
		Path pathFoto = getPath(filename);
		
		Resource recurso = null;
		recurso = new UrlResource(pathFoto.toUri());
			
		if(!recurso.exists() && !recurso.isReadable()) {
			throw new RuntimeException("Error: No se puede cargar la imagen -> " + pathFoto.toString());
		}
		
		return recurso;
	}
	
	@Override
	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());
	}
	
	@Override 
	public void deleteFile(String filename) throws IOException {
		String separador = System.getProperty("file.separator");
		File file = new File(UPLOADS_FOLDER + separador + filename);
		file.delete();
	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOADS_FOLDER));
	}
	
}
