package vn.com.r2s.fms.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.com.r2s.fms.api.entity.Admin;
import vn.com.r2s.fms.api.entity.TypeFeedback;
import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AdminRepository;
import vn.com.r2s.fms.api.repository.TypeFeedbackRepository;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/typeFeedback")
public class TypeFeedbackController {
	

	@Autowired
	private TypeFeedbackRepository typeFeedbackRepository;
	
	//get all users
	@GetMapping("/getAllTypeFeedback")
	public List<TypeFeedback> getAllTypeFeedback(){
		return typeFeedbackRepository.findAll();
	}
	
	//Get user by id 
	@GetMapping("/getTypeFeedbackById/{id}")
	public ResponseEntity<TypeFeedback> getTypeFeedbackbyID(@PathVariable(value = "id") int typeID)
		throws ResourceNotFoundException{
		TypeFeedback typeFeedback = 
				typeFeedbackRepository
				.findById(typeID).orElseThrow(()->new ResourceNotFoundException("TypeID not found on ::"+typeID));
		
		return ResponseEntity.ok().body(typeFeedback);
		
	}
		
	
	//Create user
	@PostMapping("/addTypeFeedback")
	public TypeFeedback createUser(@Validated @RequestBody TypeFeedback typeFeedback) {
		return typeFeedbackRepository.save(typeFeedback);
	}
	
	//Update user
	@PutMapping("/updatetypeFeedback/{id}")
	public ResponseEntity<TypeFeedback> updateUser(@PathVariable(value = "id") int typeID
			, @Validated @RequestBody TypeFeedback typeFeedbackDetails) throws ResourceNotFoundException{
		TypeFeedback typeFeedback = 
				typeFeedbackRepository.findById(typeID)
				.orElseThrow(()->new ResourceNotFoundException("TypeID not found on ::"+typeID));
		typeFeedback.setTypeName(typeFeedbackDetails.getTypeName());
		typeFeedback.setIsDeleted(typeFeedbackDetails.getIsDeleted());
		
	
		
		final TypeFeedback updateTypeFeedback = typeFeedbackRepository.save(typeFeedback);
		
		return ResponseEntity.ok(updateTypeFeedback);
		
	}
	
	//Delete user
	@DeleteMapping("/deletetypeFeedback/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") int typeID) throws Exception{
		TypeFeedback typeFeedback = 
				typeFeedbackRepository
				.findById(typeID)
				.orElseThrow(()->new ResourceNotFoundException("User not found on ::"+typeID));
		
		typeFeedbackRepository.delete(typeFeedback);
		Map<String,Boolean> responce = new HashMap<>();
		responce.put("deleted",Boolean.FALSE);
		
		return responce;
	}	
}