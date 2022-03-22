package com.modestcarz.modestcarzbootapp.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import com.modestcarz.modestcarzbootapp.Exceptions.ResourceNotFoundException;
import com.modestcarz.modestcarzbootapp.Model.Cars;
import com.modestcarz.modestcarzbootapp.Model.ResponseMessage;
import com.modestcarz.modestcarzbootapp.Repository.CarsRepository;
import com.modestcarz.modestcarzbootapp.Service.FilesStorageService;
import com.modestcarz.modestcarzbootapp.Service.StorageService;

@RestController
@CrossOrigin(origins = "*")
public class CarsController {
	
	 @Autowired
	 private CarsRepository carsRepository;
	
	 @Autowired
	 FilesStorageService storageService;
	 
	 @Autowired
	 private StorageService service;
	
	@GetMapping("/allCars")
	public List<Cars> getAllCars() {
		List<Cars> retCarsObj = new ArrayList<Cars>();
		List<Cars> CarsObj = new ArrayList<Cars>();
		CarsObj =  carsRepository.findAll();
		if(CarsObj.size()>0) {
			for(int i=0;i<CarsObj.size();i++) {
				Cars dbCar = CarsObj.get(i);
				if(dbCar != null) {
					Long postId = dbCar.getCar_id();
					List<String> filePathLst = new ArrayList<String>();
					//for AWS
					filePathLst = getListAWSFiles(postId.intValue());
					//for local
					//filePathLst = getListFiles(postId.intValue());
					dbCar.setImageUrls(filePathLst);
					retCarsObj.add(dbCar);
				}
			}
		}		
		return retCarsObj;
	}
	
	@DeleteMapping("/removeCar/{id}")
	public Map<String, Boolean> deleteEnquiry(@PathVariable(value = "id") Long carId)
			throws ResourceNotFoundException {
		Cars dbCar = carsRepository.findById(carId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + carId));
		carsRepository.delete(dbCar);
		deleteFileAWS(carId.toString());
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/allFeaturedCars")
	public List<Cars> getAllFeaturedCars() {
		List<Cars> retCarsObj = new ArrayList<Cars>();
		List<Cars> CarsObj = new ArrayList<Cars>();
		CarsObj =  carsRepository.findAll();
		if(CarsObj.size()>0) {
			for(int i=0;i<CarsObj.size();i++) {
				Cars dbCar = CarsObj.get(i);
				if(dbCar != null) {
					if(dbCar.getIsFeatured().equalsIgnoreCase("YES")) {
						Long postId = dbCar.getCar_id();
						List<String> filePathLst = new ArrayList<String>();
						//for AWS
						filePathLst = getListAWSFiles(postId.intValue());
						//for local
						//filePathLst = getListFiles(postId.intValue());
						dbCar.setImageUrls(filePathLst);
						retCarsObj.add(dbCar);
					}
				}
			}
		}		
		return retCarsObj;
	}
	
	
	@GetMapping("/carDetails/{id}")
	public ResponseEntity<Cars> getCarDetailsById(@PathVariable(value = "id") Long carId)
			throws ResourceNotFoundException {
		Cars car = carsRepository.findById(carId).orElseThrow(() -> new ResourceNotFoundException("Enquiry not found for this id :: " + carId));
		if(car != null) {
			Long postId = car.getCar_id();
			List<String> filePathLst = new ArrayList<String>();
			//for AWS
			filePathLst = getListAWSFiles(postId.intValue());
			//for local
			//filePathLst = getListFiles(postId.intValue());
			car.setImageUrls(filePathLst);
		}
		return ResponseEntity.ok().body(car);
	}
	 
	
	@PostMapping("/postNewCar")
	public String postNewCar(@Valid @RequestBody Cars car) {
		carsRepository.save(car);
		Integer maxTmcId = carsRepository.getmaxTmcId();
		if(maxTmcId != null) {
			System.out.println("the id is"+maxTmcId);
		}
		return "You Will Shortly Recieve a Callback from us!!";
	}
	
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
	    String message = "";
	    try {
	    	Integer maxTmcId = carsRepository.getmaxTmcId();
	    	if(maxTmcId != null) {
	    		 storageService.save(file,maxTmcId);
	    		 message = "Uploaded the file successfully: " + file.getOriginalFilename();
	    	}
	    	return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
	     
	    } catch (Exception e) {
	      message = "Could not upload the file: " + file.getOriginalFilename() + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
	    }
	}
	
	  public List<String> getListFiles(int id) {
	    List<String> fileInfos = storageService.loadAll(id).map(path -> {
	      String url = MvcUriComponentsBuilder
	          .fromMethodName(CarsController.class, "getFile",path.getFileName().toString(),id).build().toString();
	      return url;
	    }).collect(Collectors.toList());

	    return fileInfos;
	  }

	  @GetMapping("/files/{postId}/{filename:.+}")
	  @ResponseBody
	  public ResponseEntity<Resource> getFile(@PathVariable String filename,@PathVariable int postId) {
	    Resource file = storageService.load(filename,postId);
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
	  }
	  
	  //added for AWS File Upload
	
	    @PostMapping("/Awsupload")
	    public ResponseEntity<String> uploadFileAWS(@RequestParam(value = "file") MultipartFile file) {
	    	Integer maxTmcId = carsRepository.getmaxTmcId();
	    	return new ResponseEntity<>(service.uploadFile(file,maxTmcId), HttpStatus.OK);
	    }

	    @GetMapping("/Awsdownload/{postId}/{fileName}")
	    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName,@PathVariable int postId) {
	        byte[] data = service.downloadFile(fileName,postId);
	        ByteArrayResource resource = new ByteArrayResource(data);
	        return ResponseEntity
	                .ok()
	                .contentLength(data.length)
	                .header("Content-type", "application/octet-stream")
	                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
	                .body(resource);
	    }

	    @DeleteMapping("/delete/{folderName}")
	    public ResponseEntity<String> deleteFileAWS(@PathVariable String folderName) {
	        return new ResponseEntity<>(service.deleteFile(folderName), HttpStatus.OK);
	    }
	    
	    public List<String> getListAWSFiles(int id) {
		    List<String> fileInfos = service.loadAllAwsFiles(id);
		    List<String> UrlfileInfos = new ArrayList<String>();
		    try {
		    	for(String filesStr:fileInfos) {
			    	String url = MvcUriComponentsBuilder
					          .fromMethodName(CarsController.class, "downloadFile",filesStr,id).build().toString();
			    	UrlfileInfos.add(url); 
			    }
			} catch (Exception e) {
				e.printStackTrace();
			}
		    return UrlfileInfos;
		  }

}
