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

import com.fasterxml.jackson.databind.ser.std.ClassSerializer;

import vn.com.r2s.fms.api.Dto.AssignmentDto;
import vn.com.r2s.fms.api.entity.Assignment;
import vn.com.r2s.fms.api.entity.AssignmentKey;
import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Trainer;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AssignmentRepository;
import vn.com.r2s.fms.api.repository.ClassRepository;
import vn.com.r2s.fms.api.repository.ModuleRepository;
import vn.com.r2s.fms.api.repository.TrainerRepository;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {

	@Autowired
	AssignmentRepository asmRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	TrainerRepository trainerRepository;

	@GetMapping("/getAll")
	public List<AssignmentDto> getAllAssignment() {
		List<Assignment> asm = asmRepository.findAll();
		AssignmentKey asmKey = new AssignmentKey();
		List<AssignmentDto> responseAsmDto = new ArrayList<AssignmentDto>();

		for (Assignment obj : asm) {
			asmKey = obj.getAssignmentKey();
			AssignmentDto asmDto = new AssignmentDto();

			Class responseClass = asmKey.getClassId();
			asmDto.setClassId(responseClass.getClassID());

			Module responseModule = asmKey.getModuleId();
			asmDto.setModuleId(responseModule.getModuleId());

			Trainer responseTrainer = asmKey.getTrainerId();
			asmDto.setTrainerId(responseTrainer.getUserName());

			asmDto.setRegistrationCode(obj.getRegistrationCode());

			responseAsmDto.add(asmDto);
		}

		return responseAsmDto;
	}

	@GetMapping("/getAssignmentById/{classId}/{moduleId}/{trainerId}")
	public ResponseEntity<AssignmentDto> getAssignmentById(
			@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId, @PathVariable(value = "trainerId") Trainer trainerId)
			throws ResourceNotFoundException {

		AssignmentKey asmKey = new AssignmentKey(classId, moduleId, trainerId);
		Assignment asm = asmRepository.findById(asmKey)
				.orElseThrow(() -> new ResourceNotFoundException("Assignment Not Found"));
		AssignmentDto asmDto = new AssignmentDto();

		Class responseClass = asmKey.getClassId();
		asmDto.setClassId(responseClass.getClassID());

		Module responseModule = asmKey.getModuleId();
		asmDto.setModuleId(responseModule.getModuleId());

		Trainer responseTrainer = asmKey.getTrainerId();
		asmDto.setTrainerId(responseTrainer.getUserName());

		asmDto.setRegistrationCode(asm.getRegistrationCode());
		return ResponseEntity.ok().body(asmDto);
	}

	@GetMapping("/getAssignmentByTrainerId/{trainerId}")
	public List<AssignmentDto> getAssignmentByTrainerId(@PathVariable(value = "trainerId") Trainer trainerId) {

		List<Assignment> asm = asmRepository.findByAssignmentKeyTrainerId(trainerId);
		AssignmentKey asmKey = new AssignmentKey();
		List<AssignmentDto> responseAsmDto = new ArrayList<AssignmentDto>();

		for (Assignment obj : asm) {
			asmKey = obj.getAssignmentKey();
			AssignmentDto asmDto = new AssignmentDto();

			Class responseClass = asmKey.getClassId();
			asmDto.setClassId(responseClass.getClassID());

			Module responseModule = asmKey.getModuleId();
			asmDto.setModuleId(responseModule.getModuleId());

			Trainer responseTrainer = asmKey.getTrainerId();
			asmDto.setTrainerId(responseTrainer.getUserName());

			asmDto.setRegistrationCode(obj.getRegistrationCode());

			responseAsmDto.add(asmDto);
		}

		return responseAsmDto;
	}

	@GetMapping("/getAssignmentByClassId/{classId}")
	public List<AssignmentDto> getAssignmentByTrainerId(@PathVariable(value = "classId") Class classId) {

		List<Assignment> asm = asmRepository.findByAssignmentKeyClassId(classId);
		AssignmentKey asmKey = new AssignmentKey();
		List<AssignmentDto> responseAsmDto = new ArrayList<AssignmentDto>();

		for (Assignment obj : asm) {
			asmKey = obj.getAssignmentKey();
			AssignmentDto asmDto = new AssignmentDto();

			Class responseClass = asmKey.getClassId();
			asmDto.setClassId(responseClass.getClassID());

			Module responseModule = asmKey.getModuleId();
			asmDto.setModuleId(responseModule.getModuleId());

			Trainer responseTrainer = asmKey.getTrainerId();
			asmDto.setTrainerId(responseTrainer.getUserName());

			asmDto.setRegistrationCode(obj.getRegistrationCode());

			responseAsmDto.add(asmDto);
		}

		return responseAsmDto;
	}

	@GetMapping("/getAssignmentByModuleId/{moduleId}")
	public List<AssignmentDto> getAssignmentByTrainerId(@PathVariable(value = "moduleId") Module moduleId) {

		List<Assignment> asm = asmRepository.findByAssignmentKeyModuleId(moduleId);
		AssignmentKey asmKey = new AssignmentKey();
		List<AssignmentDto> responseAsmDto = new ArrayList<AssignmentDto>();

		for (Assignment obj : asm) {
			asmKey = obj.getAssignmentKey();
			AssignmentDto asmDto = new AssignmentDto();

			Class responseClass = asmKey.getClassId();
			asmDto.setClassId(responseClass.getClassID());

			Module responseModule = asmKey.getModuleId();
			asmDto.setModuleId(responseModule.getModuleId());

			Trainer responseTrainer = asmKey.getTrainerId();
			asmDto.setTrainerId(responseTrainer.getUserName());

			asmDto.setRegistrationCode(obj.getRegistrationCode());

			responseAsmDto.add(asmDto);
		}

		return responseAsmDto;
	}
	
	@GetMapping("/getAssignmentByRegistrationCode/{registrationCode}")
	public List<AssignmentDto> getAssignmentByRegistrationCode(@PathVariable(value = "registrationCode") String registrationCode) {

		List<Assignment> asm = asmRepository.findByRegistrationCode(registrationCode);
		AssignmentKey asmKey = new AssignmentKey();
		List<AssignmentDto> responseAsmDto = new ArrayList<AssignmentDto>();

		for (Assignment obj : asm) {
			asmKey = obj.getAssignmentKey();
			AssignmentDto asmDto = new AssignmentDto();

			Class responseClass = asmKey.getClassId();
			asmDto.setClassId(responseClass.getClassID());

			Module responseModule = asmKey.getModuleId();
			asmDto.setModuleId(responseModule.getModuleId());

			Trainer responseTrainer = asmKey.getTrainerId();
			asmDto.setTrainerId(responseTrainer.getUserName());

			asmDto.setRegistrationCode(obj.getRegistrationCode());

			responseAsmDto.add(asmDto);
		}

		return responseAsmDto;
	}

	@PutMapping("/updateAssignment/{classId}/{moduleId}/{trainerId}")
	public ResponseEntity<Assignment> updateAssignment (@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId, @PathVariable(value = "trainerId") Trainer trainerId,
			@Validated @RequestBody AssignmentDto asmDto) throws ResourceNotFoundException {
		
		AssignmentKey asmKey = new AssignmentKey(classId, moduleId, trainerId);

		Assignment asm = asmRepository.findById(asmKey).orElseThrow(() -> new ResourceNotFoundException("Assignment Not Found"));

		Trainer trainer = new Trainer();
		trainer = trainerRepository.findById(asmDto.trainerId).orElseThrow(() -> new ResourceNotFoundException("Trainer Not Found " + asmDto.getTrainerId()));
		
		Assignment addAsm = new Assignment();
		addAsm.setAssignmentKey(new AssignmentKey(classId, moduleId, trainer));
		
		addAsm.setRegistrationCode(asm.getRegistrationCode());
		
		asmRepository.delete(asm);

		Assignment updateAsm = asmRepository.save(addAsm);
		
		return ResponseEntity.ok().body(updateAsm);
		
	}

	@PostMapping("/addAssignment")
	public Assignment addAssignment(@RequestBody AssignmentDto asmDto) throws ResourceNotFoundException {

		Assignment asm = new Assignment();

		Class cls = classRepository.findById(asmDto.getClassId()).orElseThrow(() -> new ResourceNotFoundException("Class not found"));

		Module module = moduleRepository.findById(asmDto.getModuleId()).orElseThrow(() -> new ResourceNotFoundException("Module not found"));

		Trainer trainer = new Trainer();
		trainer.setUserName(asmDto.getTrainerId());

		asm.setAssignmentKey(new AssignmentKey(cls, module, trainer));
		
		asm.setRegistrationCode(asmDto.getRegistrationCode());
		return asmRepository.save(asm);
		
	}

	@DeleteMapping("/deleteAssignment/{classId}/{moduleId}/{trainerId}")
	public Map<String, Boolean> deleteAssignment(
			@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId, @PathVariable(value = "trainerId") Trainer trainerId)
			throws ResourceNotFoundException {
		
		AssignmentKey asmKey = new AssignmentKey(classId, moduleId, trainerId);
		
		Assignment asm = asmRepository.findById(asmKey)
				.orElseThrow(() -> new ResourceNotFoundException("Assignment Not Found"));
		
		asmRepository.delete(asm);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

}