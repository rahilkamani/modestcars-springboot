package com.modestcarz.modestcarzbootapp.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.modestcarz.modestcarzbootapp.Exceptions.ResourceNotFoundException;
import com.modestcarz.modestcarzbootapp.Model.Enquiry;
import com.modestcarz.modestcarzbootapp.Repository.EnquiryRepository;


@RestController
@CrossOrigin(origins = "*")
public class EnquiryController {
	
	@Autowired
	private EnquiryRepository enquiryRepository;
	
	@GetMapping("/allEnquires")
	public List<Enquiry> getAllEnquires() {
		return enquiryRepository.findAll();
	}
	
	@PostMapping("/putQuery")
	public String postEnquiry(@Valid @RequestBody Enquiry enquiry) {
		enquiryRepository.save(enquiry);
		return "You Will Shortly Recieve a Call back from Us!";
	}
	
	@DeleteMapping("/removeEnquiry/{id}")
	public Map<String, Boolean> deleteEnquiry(@PathVariable(value = "id") Long enquiryId)
			throws ResourceNotFoundException {
		Enquiry enquiry = enquiryRepository.findById(enquiryId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + enquiryId));

		enquiryRepository.delete(enquiry);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
	@GetMapping("/enquiry/{id}")
	public ResponseEntity<Enquiry> getEmployeeById(@PathVariable(value = "id") Long enquiryId)
			throws ResourceNotFoundException {
		Enquiry enquiry = enquiryRepository.findById(enquiryId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + enquiryId));
		return ResponseEntity.ok().body(enquiry);
	}

}
