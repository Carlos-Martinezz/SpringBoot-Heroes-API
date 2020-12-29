package com.carlos.springboot.cmheroesapi.app.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Carlos Martínez
 * @version 1.0
 * 
 * Interface para definir métodos de nuestro @Service Upload
 */
public interface IUploadService {
	
		public String copy(MultipartFile file) throws IOException;
		
		public Resource load(String filename) throws MalformedURLException;
		
		public Path getPath(String filename);
		
		public void deleteFile(String filename) throws IOException;
		
		public void deleteAll();
		
		public void init() throws IOException;
		
}
