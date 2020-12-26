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

import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/getAllUsers")
	public List<User> getAllUsers(){
	 return userRepository.findAll();	
	}
	
	@GetMapping("/getUsersById/{id}")
	public ResponseEntity<User> getUsersById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
		User user = 
				userRepository
				.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found ::" + userId));
		return ResponseEntity.ok().body(user);
	}
	
	@PostMapping("/addUsers")
	public User createUser(@Validated @RequestBody User user) {
		return userRepository.save(user);
	}
	
	@PutMapping("/updateUsers/{id}")
	public ResponseEntity<User> updateUsers(@PathVariable(value = "id") Long userId, @Validated @RequestBody User userDetails) throws ResourceNotFoundException{
		User user = userRepository
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + userId));
		
		user.setEmail(userDetails.getEmail());
		user.setName(userDetails.getName());
		user.setPhone(userDetails.getPhone());
		
		final User updateUser  = userRepository.save(user);
		return ResponseEntity.ok(updateUser);
	}
	
	@DeleteMapping("/deleteUsers/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException{
		User user = userRepository
				.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + userId));
		userRepository.delete(user);
		Map<String,Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;
	}
}
