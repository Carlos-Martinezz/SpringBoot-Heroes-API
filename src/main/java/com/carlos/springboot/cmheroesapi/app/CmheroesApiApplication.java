package com.carlos.springboot.cmheroesapi.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.carlos.springboot.cmheroesapi.app.services.IUploadService;

/**
 * @author Carlos Martínez
 * @version 1
 */
@SpringBootApplication
public class CmheroesApiApplication implements CommandLineRunner {
	
	@Autowired
	IUploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(CmheroesApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.deleteAll();
		uploadService.init();
	}

}
