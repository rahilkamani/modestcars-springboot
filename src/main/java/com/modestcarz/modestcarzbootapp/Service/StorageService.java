package com.modestcarz.modestcarzbootapp.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

public interface StorageService {
   
    public String uploadFile(MultipartFile file, Integer maxTmcId);


    public byte[] downloadFile(String fileName,int postId);


    public String deleteFile(String fileName);


    public File convertMultiPartFileToFile(MultipartFile file);
    
    public List<String> loadAllAwsFiles(int postId);
}
