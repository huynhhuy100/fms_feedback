package vn.com.r2s.fms.api.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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

import vn.com.r2s.fms.api.Dto.AssignmentDto;
import vn.com.r2s.fms.api.Dto.TraineeAssignmentDto;
import vn.com.r2s.fms.api.entity.Assignment;
import vn.com.r2s.fms.api.entity.AssignmentKey;
import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.entity.TraineeAssignment;
import vn.com.r2s.fms.api.entity.TraineeAssignmentKey;
import vn.com.r2s.fms.api.entity.Trainer;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AssignmentRepository;
import vn.com.r2s.fms.api.repository.ClassRepository;
import vn.com.r2s.fms.api.repository.ModuleRepository;
import vn.com.r2s.fms.api.repository.TraineeAssignmentRepository;
import vn.com.r2s.fms.api.repository.TraineeRepository;
import vn.com.r2s.fms.api.repository.TrainerRepository;

@RestController
@RequestMapping("/api/traineeAssignment")
public class TraineeAssignmentController {

	


	@Autowired
	TraineeAssignmentRepository traineeasmRepository;

	@Autowired
	AssignmentRepository registrationCodeRepository;

	@Autowired
	TraineeRepository traineeRepository;

	@GetMapping("/getAll")
	public List<TraineeAssignmentDto> gettraineeAllAssignment() {
		List<TraineeAssignment> traineeasm = traineeasmRepository.findAll();
		TraineeAssignmentKey traineeasmKey = new TraineeAssignmentKey();
		TraineeAssignmentDto traineeasmDto = new TraineeAssignmentDto();
		List<TraineeAssignmentDto> traineeAsmDto = new ArrayList<TraineeAssignmentDto>();

		for (TraineeAssignment obj : traineeasm) {
			traineeasmKey = obj.getTraineeAssignmentKey();

			
//			Assignment responseregistrationCode = traineeasmKey.getRegistrationCode();
//			asmDto.setRegistrationCode(responseregistrationCode.getRegistrationCode());

			Assignment responseregistrationcode = traineeasmKey.getRegistrationCode();
			traineeasmDto.setRegistrationCode(responseregistrationcode.getRegistrationCode());
			
			Trainee responseTrainee = traineeasmKey.getTraineeId();
			traineeasmDto.setTraineeId(responseTrainee.getUserName());

			

			traineeAsmDto.add(traineeasmDto);
		}

		return traineeAsmDto;
	}

	@GetMapping("/getTraineeAssignmentById/{registrationCode}/{traineeId}")
	public ResponseEntity<TraineeAssignmentDto> getTraineeAssignmentById(
			
			@PathVariable(value = "registrationCode") Assignment registrationCode, @PathVariable(value = "traineeId") Trainee traineeId)
			throws ResourceNotFoundException {

		TraineeAssignmentKey traineeasmKey = new TraineeAssignmentKey(registrationCode, traineeId);
		TraineeAssignment traineeasm = traineeasmRepository.findById(traineeasmKey)
				.orElseThrow(() -> new ResourceNotFoundException("TraineeAssignment Not Found"));
		TraineeAssignmentDto traineeAsmDto = new TraineeAssignmentDto();

		

		Assignment responseregistrationcode = traineeasmKey.getRegistrationCode();
		traineeAsmDto.setRegistrationCode(responseregistrationcode.getRegistrationCode());

		Trainee responseTrainee = traineeasmKey.getTraineeId();
		traineeAsmDto.setTraineeId(responseTrainee.getUserName());
		
		return ResponseEntity.ok().body(traineeAsmDto);

	}

	@PostMapping("/addTraineeAssignment")
	public TraineeAssignment addTraineeAssignment(@RequestBody TraineeAssignmentDto traineeAssignmentDto) {
		
		Assignment asm = new Assignment();
		asm.setRegistrationCode(traineeAssignmentDto.getRegistrationCode());
		
		Trainee trainee = new Trainee();
		trainee.setUserName(traineeAssignmentDto.getTraineeId());
		
		TraineeAssignmentKey traineeAsmKey = new TraineeAssignmentKey(asm, trainee);
		
		TraineeAssignment traineeAssignment = new TraineeAssignment(traineeAsmKey);
		
		return traineeasmRepository.save(traineeAssignment);
	}

}