package vn.com.r2s.fms.api.controller;
import java.util.ArrayList;
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
import vn.com.r2s.fms.api.Dto.AnswerDto;
import vn.com.r2s.fms.api.Dto.TraineeCommentDto;
import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.entity.TraineeComment;
import vn.com.r2s.fms.api.entity.TraineeCommentKey;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.ClassRepository;
import vn.com.r2s.fms.api.repository.ModuleRepository;
import vn.com.r2s.fms.api.repository.TraineeCommentRepository;
import vn.com.r2s.fms.api.repository.TraineeRepository;

@RestController
@RequestMapping("/api/traineeComment")
public class TraineeCommentController {

	@Autowired
	TraineeCommentRepository traineeCommentRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	TraineeRepository traineeRepository;
	

	@GetMapping("/getAll")
	public List<TraineeCommentDto> getAllTraineeComment() {
//		List<AnswerDto> answer = answerRepository.findAll();
		List<TraineeComment> traineeComment = traineeCommentRepository.findAll();
		TraineeCommentKey traineeCommentKey = new TraineeCommentKey();
		TraineeCommentDto traineeCommentDto = new TraineeCommentDto();
		List<TraineeCommentDto> responseTraineeCommentDto = new ArrayList<TraineeCommentDto>();

		for (TraineeComment obj : traineeComment) {
			traineeCommentKey = obj.getTraineeCommentKey();

			Class responseClass = traineeCommentKey.getClassId();
			traineeCommentDto.setClassId(responseClass.getClassID());

			Module responseModule = traineeCommentKey.getModuleId();
			traineeCommentDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = traineeCommentKey.getTraineeId();
			traineeCommentDto.setTraineeId(responseTrainee.getUserName());
			

			traineeCommentDto.setComment(obj.getComment());
		
			responseTraineeCommentDto.add(traineeCommentDto);
		}

		return responseTraineeCommentDto;
	}

	@GetMapping("/getTraineeCommentById/{classId}/{moduleId}/{traineeId}")
	public ResponseEntity<TraineeCommentDto> getTraineeCommentById(
			@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId, 
			@PathVariable(value = "traineeId") Trainee traineeId)
			throws ResourceNotFoundException {

		TraineeCommentKey traineeCommentKey = new TraineeCommentKey(classId, moduleId, traineeId);
		TraineeComment traineeComment = traineeCommentRepository.findById(traineeCommentKey)
				.orElseThrow(() -> new ResourceNotFoundException("Answer Not Found"));
		TraineeCommentDto traineeCommentDto = new TraineeCommentDto();

		Class responseClass = traineeCommentKey.getClassId();
		traineeCommentDto.setClassId(responseClass.getClassID());

		Module responseModule = traineeCommentKey.getModuleId();
		traineeCommentDto.setModuleId(responseModule.getModuleId());

		Trainee responseTrainee = traineeCommentKey.getTraineeId();
		traineeCommentDto.setTraineeId(responseTrainee.getUserName());
		

//		asmDto.setRegistrationCode(asm.getRegistrationCode());
//		return ResponseEntity.ok().body(asmDto);
		
		traineeCommentDto.setComment(traineeComment.getComment());
		return ResponseEntity.ok().body(traineeCommentDto);
	}

	@GetMapping("/getTraineeCommentByTraineeId/{traineeId}")
	public List<TraineeCommentDto> getTraineeCommentByTrainerId(@PathVariable(value = "traineeId") Trainee traineeId) {
		
		List<TraineeComment> traineeComment = traineeCommentRepository.findByTraineeCommentKeyTraineeId(traineeId);
		TraineeCommentKey traineeCommentKey = new TraineeCommentKey();
		TraineeCommentDto traineeCommentDto = new TraineeCommentDto();
		List<TraineeCommentDto> responseTraineeCommentDto = new ArrayList<TraineeCommentDto>();
		
		for(TraineeComment obj : traineeComment) {
			traineeCommentKey = obj.getTraineeCommentKey();
			
			Class responseClass = traineeCommentKey.getClassId();
			traineeCommentDto.setClassId(responseClass.getClassID());

			Module responseModule = traineeCommentKey.getModuleId();
			traineeCommentDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = traineeCommentKey.getTraineeId();
			traineeCommentDto.setTraineeId(responseTrainee.getUserName());
		
			
			traineeCommentDto.setComment(obj.getComment());

			responseTraineeCommentDto.add(traineeCommentDto);
		}
		
		return responseTraineeCommentDto;
	}
	
	@GetMapping("/getTraineeCommentByClassId/{classId}")
	public List<TraineeCommentDto> getTraineeCommentByTrainerId(@PathVariable(value = "classId") Class classId) {
		
		List<TraineeComment> traineeComment = traineeCommentRepository.findByTraineeCommentKeyClassId(classId);
		TraineeCommentKey traineeCommentKey = new TraineeCommentKey();
		TraineeCommentDto traineCommentDto = new TraineeCommentDto();
		List<TraineeCommentDto> responseTraineeCommentDto = new ArrayList<TraineeCommentDto>();
		
		for(TraineeComment obj : traineeComment) {
			traineeCommentKey = obj.getTraineeCommentKey();
			
			Class responseClass = traineeCommentKey.getClassId();
			traineCommentDto.setClassId(responseClass.getClassID());

			Module responseModule = traineeCommentKey.getModuleId();
			traineCommentDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = traineeCommentKey.getTraineeId();
			traineCommentDto.setTraineeId(responseTrainee.getUserName());
			
			
			traineCommentDto.setComment(obj.getComment());

			responseTraineeCommentDto.add(traineCommentDto);
		}
		
		return responseTraineeCommentDto;
	}
	
	@GetMapping("/getTraineeCommentByModuleId/{moduleId}")
	public List<TraineeCommentDto> getTraineeCommentByTraineeId(@PathVariable(value = "moduleId") Module moduleId) {
		
		List<TraineeComment> traineeComment = traineeCommentRepository.findByTraineeCommentKeyModuleId(moduleId);
		TraineeCommentKey traineeCommentKey = new TraineeCommentKey();
		TraineeCommentDto traineeCommentDto = new TraineeCommentDto();
		List<TraineeCommentDto> responseTraineeCommentDto = new ArrayList<TraineeCommentDto>();
		
		for(TraineeComment obj : traineeComment) {
			traineeCommentKey = obj.getTraineeCommentKey();
			
			Class responseClass = traineeCommentKey.getClassId();
			traineeCommentDto.setClassId(responseClass.getClassID());

			Module responseModule = traineeCommentKey.getModuleId();
			traineeCommentDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = traineeCommentKey.getTraineeId();
			traineeCommentDto.setTraineeId(responseTrainee.getUserName());
			
			traineeCommentDto.setComment(obj.getComment());

			responseTraineeCommentDto.add(traineeCommentDto);
		}
		
		return responseTraineeCommentDto;
	}
	
	
	@PutMapping("/updateTraineComment/{classId}/{moduleId}/{traineerId}")
	public ResponseEntity<TraineeComment> updateTraineeComment (@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId,
			@PathVariable(value = "trainerId") Trainee traineeId,
			@Validated @RequestBody AnswerDto answerDto) throws ResourceNotFoundException {
		
		TraineeCommentKey traineeCommentKey = new TraineeCommentKey(classId, moduleId, traineeId);
		
		TraineeComment traineeComment = traineeCommentRepository.findById(traineeCommentKey).orElseThrow(() -> new ResourceNotFoundException("TraineeComment Not Found"));
		
		Trainee trainee = traineeComment.getTraineeCommentKey().getTraineeId();
		
		trainee.setUserName(answerDto.getTraineeId());
		
		traineeCommentKey.setTraineeId(trainee);
		
		traineeComment.setTraineeCommentKey(traineeCommentKey);
		
		TraineeComment updateTraineeComment = traineeCommentRepository.save(traineeComment);
		
		return ResponseEntity.ok().body(updateTraineeComment);
		
	}

	@PostMapping("/addTraineeComment")
	public TraineeComment addTraineeComemnt(@RequestBody TraineeCommentDto traineeCommentDto) {

		TraineeComment traineeComment = new TraineeComment();

		Class cls = new Class();
		cls.setClassID(traineeCommentDto.getClassId());

		Trainee trainee = new Trainee();
		trainee.setUserName(traineeCommentDto.getTraineeId());
		
		Module module = new Module();
		module.setModuleId(traineeCommentDto.getModuleId());

		traineeComment.setTraineeCommentKey(new TraineeCommentKey(cls,module,trainee));
		traineeComment.setComment(traineeCommentDto.getComment());
		return traineeCommentRepository.save(traineeComment);
	}

	@DeleteMapping("/deleteTraineeComment/{classId}/{moduleId}/{traineeId}")
	public Map<String, Boolean> deleteTraineeComemnt(@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId,
			@PathVariable(value = "trainerId") Trainee traineeId)	
					throws ResourceNotFoundException {
		TraineeCommentKey traineeCommentKey = new TraineeCommentKey(classId, moduleId, traineeId);
		TraineeComment traineeComment = traineeCommentRepository.findById(traineeCommentKey).orElseThrow(() -> new ResourceNotFoundException("TraineeComment Not Found"));
		traineeCommentRepository.delete(traineeComment);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}