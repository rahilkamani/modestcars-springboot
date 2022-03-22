package com.modestcarz.modestcarzbootapp.Service;

import java.nio.file.Path;
import java.util.stream.Stream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {

	  public void init();

	  public void save(MultipartFile file,int id);

	  public Resource load(String filename,int postId);

	  public void deleteAll();

	  public Stream<Path> loadAll(int id);
}
