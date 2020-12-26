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
import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AdminRepository;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
	@Autowired
	private AdminRepository adminRespository;

	
	@GetMapping("/getAllAdmin")
	public List<Admin> getAllAdmin() {
		return adminRespository.findAll();
	}

	@PostMapping("/addAdmin")
	public Admin addAdmin(@Validated @RequestBody Admin admin) {
		return adminRespository.save(admin);
	}

	@GetMapping("/getAdminById/{UserName}")
	public ResponseEntity<Admin> getAdminById(@PathVariable(value = "UserName") String UserName)
			throws ResourceNotFoundException {
		Admin admin = adminRespository.findById(UserName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + UserName));
		return ResponseEntity.ok().body(admin);
	}

	@PutMapping("/updateAdmin/{UserName}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable(value = "UserName") String UserName,
			@Validated @RequestBody Admin adminDetails) throws ResourceNotFoundException {
		Admin admin = adminRespository.findById(UserName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + UserName));
		admin.setName(adminDetails.getName());
		admin.setPassword(adminDetails.getPassword());
		admin.setEmail(adminDetails.getEmail());
		final Admin updateAdmin = adminRespository.save(admin);
		return ResponseEntity.ok(updateAdmin);
	}

	@DeleteMapping("/deleteAdmin/{UserName}")
	public Map<String, Boolean> deleteAdmin(@PathVariable(value = "UserName") String UserName)
			throws ResourceNotFoundException {
		Admin admin = adminRespository.findById(UserName)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + UserName));
		adminRespository.delete(admin);
		Map<String, Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;
	}
	
}
