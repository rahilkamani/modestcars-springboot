package com.modestcarz.modestcarzbootapp.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.util.IOUtils;
import com.modestcarz.modestcarzbootapp.Controller.CarsController;

@Service
public class StorageServiceImpl implements StorageService {
	
	 @Value("${application.bucket.name}")
	    private String bucketName;

	    @Autowired
	    private AmazonS3 s3Client;

	    public String uploadFile(MultipartFile file, Integer maxTmcId) {
	    	
	    	File fileObj = convertMultiPartFileToFile(file);
	        String fileName = file.getOriginalFilename();
	        s3Client.putObject(new PutObjectRequest(bucketName, maxTmcId.toString() + "/"+ fileName, fileObj));
	        fileObj.delete();
	        return "File uploaded : " + fileName;
	    }


	    public byte[] downloadFile(String fileName,int postId) {
	        S3Object s3Object = s3Client.getObject(bucketName,postId + "/" + fileName);
	        S3ObjectInputStream inputStream = s3Object.getObjectContent();
	        try {
	            byte[] content = IOUtils.toByteArray(inputStream);
	            return content;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }


	    public String deleteFile(String folderName) {
//	    	DeleteObjectsRequest multiObjectDeleteRequest = new DeleteObjectsRequest(bucketName)
//                    .withp
//                    .withQuiet(false);
//	    	// Verify that the objects were deleted successfully.
//            DeleteObjectsResult delObjRes = s3Client.deleteObjects(multiObjectDeleteRequest);
//            int successfulDeletes = delObjRes.getDeletedObjects().size();
//            System.out.println(successfulDeletes + " objects successfully deleted.");
	    	ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
	                .withBucketName(bucketName)
	                .withPrefix(folderName + "/");
	        ObjectListing objectListing = s3Client.listObjects(listObjectsRequest);
	        while (true) {
	            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
	                s3Client.deleteObject(bucketName, objectSummary.getKey());
	            }
	            if (objectListing.isTruncated()) {
	                objectListing = s3Client.listNextBatchOfObjects(objectListing);
	            } else {
	                break;
	            }
	        }
	        return folderName + " removed ...";
	    }


	    public File convertMultiPartFileToFile(MultipartFile file) {
	        File convertedFile = new File(file.getOriginalFilename());
	        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
	            fos.write(file.getBytes());
	        } catch (IOException e) {
	        	e.printStackTrace();
	        }
	        return convertedFile;
	    }


		@Override
		public List<String> loadAllAwsFiles(int postId) {
			List<String> fileInfos = new ArrayList<String>();
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
					.withPrefix(String.valueOf(postId) + "/");

			ObjectListing objects = s3Client.listObjects(listObjectsRequest);
			for (;;) {
				List<S3ObjectSummary> summaries = objects.getObjectSummaries();
				if (summaries.size() < 1) {
					break;
				}
				for (int i = 0; i < summaries.size(); i++) {
					
					String tmpFileName = summaries.get(i).getKey();
					if(tmpFileName.contains(".")) {
						String split[] = tmpFileName.split("/");
						String actualFile = split[1];
						fileInfos.add(actualFile);
					}
				}
				objects = s3Client.listNextBatchOfObjects(objects);
			}
//			ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketName).withPrefix(String.valueOf(postId));
//	    	ListObjectsV2Result listing = s3Client.listObjectsV2(req);
//	    	for (S3ObjectSummary summary: listing.getObjectSummaries()) {
//	    		String fileNames = summary.getKey();
//	    		fileInfos.add(fileNames);
//	    	}
			return fileInfos;
		}
}
