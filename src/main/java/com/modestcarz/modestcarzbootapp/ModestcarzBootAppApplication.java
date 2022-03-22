package com.modestcarz.modestcarzbootapp;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.modestcarz.modestcarzbootapp.Service.FilesStorageService;

@SpringBootApplication
public class ModestcarzBootAppApplication implements CommandLineRunner {
	
	@Resource
	FilesStorageService storageService;

	public static void main(String[] args) {
		SpringApplication.run(ModestcarzBootAppApplication.class, args);
	}
	
	 @Override
	  public void run(String... arg) throws Exception {
	    storageService.init();
	  }

}
