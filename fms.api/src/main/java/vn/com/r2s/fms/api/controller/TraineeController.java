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
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AdminRepository;
import vn.com.r2s.fms.api.repository.TraineeRepository;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/trainee")
public class TraineeController {
	
	@Autowired
	private TraineeRepository traineeRepository;
	
	@GetMapping("/getAllTrainee")
	public List<Trainee> getAllTrianee(){
		return traineeRepository.findAll();
	}
	
	@PostMapping("/addTrainee")
	public Trainee createUser(@Validated @RequestBody Trainee trainee) {
		return traineeRepository.save(trainee);
	}
	
	@PutMapping("/updateTrainee/{userName}")
	public ResponseEntity<Trainee> updateTrainee(@PathVariable(value = "userName") String userName, @Validated @RequestBody Trainee traineeDetails) throws ResourceNotFoundException{
		Trainee trainee = traineeRepository
				.findById(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + userName));
		trainee.setEmail(traineeDetails.getEmail());
		trainee.setName(traineeDetails.getName());
		trainee.setPhone(traineeDetails.getPhone());
		trainee.setAddress(traineeDetails.getAddress());
		trainee.setPassword(traineeDetails.getPassword());
		trainee.setActive(traineeDetails.isActive());;
		trainee.setUserName(traineeDetails.getUserName());
		final Trainee updateTrainee  = traineeRepository.save(trainee);
		return ResponseEntity.ok(updateTrainee);
	}
	
	@DeleteMapping("/deleteTrainee/{userName}")
	public Map<String, Boolean> deleteTrainee(@PathVariable(value = "userName") String userName) throws ResourceNotFoundException{
		Trainee trainee = traineeRepository
				.findById(userName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + userName));
		traineeRepository.delete(trainee);
		Map<String,Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;
	}
	
	@GetMapping("getTraineeById/{userName}")
	public ResponseEntity<Trainee> getTraineeById(@PathVariable(value = "userName") String userName) throws ResourceNotFoundException{
		Trainee trainee = 
				traineeRepository
				.findById(userName).orElseThrow(() -> new ResourceNotFoundException("User not found ::" + userName));
		return ResponseEntity.ok().body(trainee);
	}
}
