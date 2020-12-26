package vn.com.r2s.fms.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import vn.com.r2s.fms.api.entity.Enrollment;
import vn.com.r2s.fms.api.entity.EnrollmentID;
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.EnrollmentRepository;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {

	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	@GetMapping("/getAll")
	public List<Enrollment> getAll(){
	 return enrollmentRepository.findAll();	
	}
		
	@GetMapping("/getByTrainee/{id}")
	public List<Enrollment> getAllEnrollmentIdTraineeId(@PathVariable("id") String id){
		return enrollmentRepository.findByEnrollmentIDTraineeId(id);
		
	}
	
	
	@GetMapping("/getByClass/{id}")
	public List<Enrollment> getAllEnrollmentIdClassId(@PathVariable("id") Integer id){
		return enrollmentRepository.findByEnrollmentIDClassId(id);
		
	}
	
	@GetMapping("getId/{traineeId}/{classID}")
	public Optional<Enrollment> getbyid(@PathVariable(value = "classID") Integer classID, @PathVariable(value = "traineeId") String traineeId ){
		return enrollmentRepository.findByEnrollmentIDClassIdAndEnrollmentIDTraineeId(classID, traineeId); 
		
	}
	
	@PostMapping("/save")
	public Enrollment save(@Validated @RequestBody Enrollment enrollment) {
		
		return enrollmentRepository.save(enrollment);
	}
	
	@PutMapping("/updateEnrollment/{traineeId}/{classID}")
	public ResponseEntity<Enrollment> updateEnrollment(@PathVariable(value = "classID") int classID, @PathVariable(value = "traineeId") String traineeId, @Validated @RequestBody Enrollment enrollmentDetails) throws ResourceNotFoundException{
		Enrollment enrollment = enrollmentRepository
				.findByEnrollmentIDClassIdAndEnrollmentIDTraineeId(classID,traineeId)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollment not found ::" + classID + traineeId));
		
		enrollmentRepository.delete(enrollment);
		enrollment.setEnrollmentID(enrollmentDetails.getEnrollmentID());
		
		final Enrollment updateEnrollment  = enrollmentRepository.save(enrollment);
		return ResponseEntity.ok(updateEnrollment);
	}

	@DeleteMapping("/delete/{traineeId}/{classID}")
	public Map<String, Boolean> deleteEnrollment(@PathVariable(value = "classID") Integer classID,@PathVariable(value = "traineeId") String traineeId ) throws ResourceNotFoundException{
		Enrollment classs =  enrollmentRepository
				.findByEnrollmentIDClassIdAndEnrollmentIDTraineeId(classID,traineeId)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollment :" + classID + traineeId));
		enrollmentRepository.delete(classs);
		Map<String,Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;

	}



}
