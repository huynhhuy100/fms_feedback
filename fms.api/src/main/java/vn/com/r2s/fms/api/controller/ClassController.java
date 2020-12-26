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

import vn.com.r2s.fms.api.entity.Class;

import vn.com.r2s.fms.api.exception.ResourceNotFoundException;

import vn.com.r2s.fms.api.repository.ClassRepository;

@RestController
@RequestMapping("/api/class")
public class ClassController {
	
	@Autowired
	private ClassRepository classRepository;
	
	@GetMapping("/getAllClass")
	public List<Class> getAllClass(){
	 return classRepository.getAllClass();	
	}
	
	@GetMapping("/getClassById/{classID}")
	public ResponseEntity<Class> getClassById(@PathVariable(value = "classID") int classID) throws ResourceNotFoundException{
		Class classs = 
				classRepository
				.findById(classID).orElseThrow(() -> new ResourceNotFoundException("User not found ::" + classID));
		return ResponseEntity.ok().body(classs);
	}
	
	@GetMapping("/getClassByClassName/{className}")
	public ResponseEntity<Class> getClassByClassName(@PathVariable(value = "className") String className) throws ResourceNotFoundException{
		Class classs = 
				classRepository
				.findByClassName(className).orElseThrow(() -> new ResourceNotFoundException("User not found ::" + className));
		return ResponseEntity.ok().body(classs);
	}
	
	@PostMapping("/addClass")
	public Class addClass(@Validated @RequestBody Class classs) {
		return classRepository.save(classs);
	}
	
	@PutMapping("/updateClass/{classID}")
	public ResponseEntity<Class> updateClass(@PathVariable(value = "classID") int classID, @Validated @RequestBody Class classDetails) throws ResourceNotFoundException{
		Class classs = classRepository
				.findById(classID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + classID));
		classs.setClassName(classDetails.getClassName());
		classs.setCapadity(classDetails.getCapadity());
		classs.setStartTime(classDetails.getStartTime());
		classs.setEndTime(classDetails.getEndTime());
		classs.setIsDeleted(classDetails.getIsDeleted());
		final Class updateClass  = classRepository.save(classs);
		return ResponseEntity.ok(updateClass);
	}
	
	@DeleteMapping("/deleteClass/{classID}")
	public Map<String, Boolean> deleteClass(@PathVariable(value = "classID") int classID) throws ResourceNotFoundException{
		Class classs = classRepository
				.findById(classID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + classID));
		classRepository.delete(classs);
		Map<String,Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;
	}
}
