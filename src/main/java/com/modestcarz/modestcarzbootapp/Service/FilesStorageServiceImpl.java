package com.modestcarz.modestcarzbootapp.Service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
	
	private final Path root = Paths.get("uploads");

	 @Override
	  public void init() {
	  }

	  @Override
	  public Resource load(String filename,int postId) {
	    try {
	      Path pathNew = Paths.get("uploads" + File.separator + postId);
	      Path file = pathNew.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	  @Override
	  public void deleteAll() {
	    FileSystemUtils.deleteRecursively(root.toFile());
	  }

	  @Override
	  public Stream<Path> loadAll(int id) {
	    try {
	     Path pathNew = Paths.get("uploads" + File.separator + id);
	      return Files.walk(pathNew, 1).filter(path -> !path.equals(pathNew)).map(pathNew::relativize);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not load the files!");
	    }
	  }

	@Override
	public void save(MultipartFile file, int id) {
		try {
	    	Path path = Paths.get("uploads" + File.separator + id);
	    	Files.createDirectories(path);
	      Files.copy(file.getInputStream(), this.root.resolve(id + File.separator +file.getOriginalFilename()));
	    } catch (Exception e) {
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    }
		
	}

}
