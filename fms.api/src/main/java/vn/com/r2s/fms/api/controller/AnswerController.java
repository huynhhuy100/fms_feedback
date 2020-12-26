package vn.com.r2s.fms.api.controller;

import java.util.ArrayList;
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

import vn.com.r2s.fms.api.Dto.AnswerDto;
import vn.com.r2s.fms.api.Dto.AssignmentDto;
import vn.com.r2s.fms.api.entity.Answer;
import vn.com.r2s.fms.api.entity.AnswerKey;
import vn.com.r2s.fms.api.entity.Assignment;
import vn.com.r2s.fms.api.entity.AssignmentKey;
import vn.com.r2s.fms.api.entity.Class;
import vn.com.r2s.fms.api.entity.Module;
import vn.com.r2s.fms.api.entity.Question;
import vn.com.r2s.fms.api.entity.Trainee;
import vn.com.r2s.fms.api.entity.Trainer;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AnswerRepository;
import vn.com.r2s.fms.api.repository.AssignmentRepository;
import vn.com.r2s.fms.api.repository.ClassRepository;
import vn.com.r2s.fms.api.repository.ModuleRepository;
import vn.com.r2s.fms.api.repository.QuestionRepository;
import vn.com.r2s.fms.api.repository.TrainerRepository;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {

	@Autowired
	AnswerRepository answerRepository;

	@Autowired
	ClassRepository classRepository;

	@Autowired
	ModuleRepository moduleRepository;

	@Autowired
	TrainerRepository trainerRepository;
	
	@Autowired
	QuestionRepository questionRepository;

	@GetMapping("/getAll")
	public List<AnswerDto> getAllAnswer() {
//		List<AnswerDto> answer = answerRepository.findAll();
		List<Answer> answer = answerRepository.findAll();
		AnswerKey answerKey = new AnswerKey();
		AnswerDto answerDto = new AnswerDto();
		List<AnswerDto> responseAnswerDto = new ArrayList<AnswerDto>();

		for (Answer obj : answer) {
			answerKey = obj.getAnswerKey();

			Class responseClass = answerKey.getClassId();
			answerDto.setClassId(responseClass.getClassID());

			Module responseModule = answerKey.getModuleId();
			answerDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = answerKey.getTraineeId();
			answerDto.setTraineeId(responseTrainee.getUserName());
			
			Question responseQuestion = answerKey.getQuestionID();
			answerDto.setQuestionID(responseQuestion.getQuestionID());

			answerDto.setValue(obj.getValue());
			
			responseAnswerDto.add(answerDto);
		}

		return responseAnswerDto;
	}

	@GetMapping("/getAnswerById/{classId}/{moduleId}/{traineeId}/{questionId}")
	public ResponseEntity<AnswerDto> getAnswerById(
			@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId, 
			@PathVariable(value = "traineeId") Trainee traineeId,
			@PathVariable(value = "questionId") Question questionId)
			throws ResourceNotFoundException {

		AnswerKey answerKey = new AnswerKey(classId, moduleId, traineeId, questionId);
		Answer answer = answerRepository.findById(answerKey)
				.orElseThrow(() -> new ResourceNotFoundException("Answer Not Found"));
		AnswerDto answerDto = new AnswerDto();

		Class responseClass = answerKey.getClassId();
		answerDto.setClassId(responseClass.getClassID());

		Module responseModule = answerKey.getModuleId();
		answerDto.setModuleId(responseModule.getModuleId());

		Trainee responseTrainee = answerKey.getTraineeId();
		answerDto.setTraineeId(responseTrainee.getUserName());
		
		Question responseQuestion = answerKey.getQuestionID();
		answerDto.setQuestionID(responseQuestion.getQuestionID());

//		asmDto.setRegistrationCode(asm.getRegistrationCode());
//		return ResponseEntity.ok().body(asmDto);
		
		answerDto.setValue(answer.getValue());
		return ResponseEntity.ok().body(answerDto);
	}

	@GetMapping("/getAnswerByTraineeId/{traineeId}")
	public List<AnswerDto> getAnswerByTrainerId(@PathVariable(value = "traineeId") Trainee traineeId) {
		
		List<Answer> answer = answerRepository.findByAnswerKeyTraineeId(traineeId);
		AnswerKey answerKey = new AnswerKey();
		AnswerDto answerDto = new AnswerDto();
		List<AnswerDto> responseAnswerDto = new ArrayList<AnswerDto>();
		
		for(Answer obj : answer) {
			answerKey = obj.getAnswerKey();
			
			Class responseClass = answerKey.getClassId();
			answerDto.setClassId(responseClass.getClassID());

			Module responseModule = answerKey.getModuleId();
			answerDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = answerKey.getTraineeId();
			answerDto.setTraineeId(responseTrainee.getUserName());
			
			Question responseQuestion = answerKey.getQuestionID();
			answerDto.setQuestionID(responseQuestion.getQuestionID());
			
			answerDto.setValue(obj.getValue());

			responseAnswerDto.add(answerDto);
		}
		
		return responseAnswerDto;
	}
	
	@GetMapping("/getAnswerByClassId/{classId}")
	public List<AnswerDto> getAnswerByTrainerId(@PathVariable(value = "classId") Class classId) {
		
		List<Answer> answer = answerRepository.findByAnswerKeyClassId(classId);
		AnswerKey answerKey = new AnswerKey();
		AnswerDto answerDto = new AnswerDto();
		List<AnswerDto> responseAnswerDto = new ArrayList<AnswerDto>();
		
		for(Answer obj : answer) {
			answerKey = obj.getAnswerKey();
			
			Class responseClass = answerKey.getClassId();
			answerDto.setClassId(responseClass.getClassID());

			Module responseModule = answerKey.getModuleId();
			answerDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = answerKey.getTraineeId();
			answerDto.setTraineeId(responseTrainee.getUserName());
			
			Question responseQuestion = answerKey.getQuestionID();
			answerDto.setQuestionID(responseQuestion.getQuestionID());;
			
			answerDto.setValue(obj.getValue());

			responseAnswerDto.add(answerDto);
		}
		
		return responseAnswerDto;
	}
	
	@GetMapping("/getAnswerByModuleId/{moduleId}")
	public List<AnswerDto> getAnswerByTraineeId(@PathVariable(value = "moduleId") Module moduleId) {
		
		List<Answer> answer = answerRepository.findByAnswerKeyModuleId(moduleId);
		AnswerKey answerKey = new AnswerKey();
		AnswerDto answerDto = new AnswerDto();
		List<AnswerDto> responseAnswerDto = new ArrayList<AnswerDto>();
		
		for(Answer obj : answer) {
			answerKey = obj.getAnswerKey();
			
			Class responseClass = answerKey.getClassId();
			answerDto.setClassId(responseClass.getClassID());

			Module responseModule = answerKey.getModuleId();
			answerDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = answerKey.getTraineeId();
			answerDto.setTraineeId(responseTrainee.getUserName());
			
			answerDto.setValue(obj.getValue());

			responseAnswerDto.add(answerDto);
		}
		
		return responseAnswerDto;
	}
	
	@GetMapping("/getAnswerByQuestionId/{questionID}")
	public List<AnswerDto> getAnswerByQuestionID(@PathVariable(value = "questionID") Question questionID) {
		
		List<Answer> answer = answerRepository.findByAnswerKeyQuestionID(questionID);
		AnswerKey answerKey = new AnswerKey();
		AnswerDto answerDto = new AnswerDto();
		List<AnswerDto> responseAnswerDto = new ArrayList<AnswerDto>();
		
		for(Answer obj : answer) {
			answerKey = obj.getAnswerKey();
			
			Class responseClass = answerKey.getClassId();
			answerDto.setClassId(responseClass.getClassID());

			Module responseModule = answerKey.getModuleId();
			answerDto.setModuleId(responseModule.getModuleId());

			Trainee responseTrainee = answerKey.getTraineeId();
			answerDto.setTraineeId(responseTrainee.getUserName());
			
			answerDto.setValue(obj.getValue());

			responseAnswerDto.add(answerDto);
		}
		
		return responseAnswerDto;
	}
	
	
	@PutMapping("/updateAnswer/{classId}/{moduleId}/{traineerId}/{questionID}")
	public ResponseEntity<Answer> updateAnswer (@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId,
			@PathVariable(value = "trainerId") Trainee traineeId,
			@PathVariable(value = "questionID") Question questionID,
			@Validated @RequestBody AnswerDto answerDto) throws ResourceNotFoundException {
		
		AnswerKey answerKey = new AnswerKey(classId, moduleId, traineeId, questionID);
		
		Answer answer = answerRepository.findById(answerKey).orElseThrow(() -> new ResourceNotFoundException("Answer Not Found"));
		
		Trainee trainee = answer.getAnswerKey().getTraineeId();
		
		trainee.setUserName(answerDto.getTraineeId());
		
		answerKey.setTraineeId(trainee);
		
		answer.setAnswerKey(answerKey);
		
		Answer updateAnswer = answerRepository.save(answer);
		
		return ResponseEntity.ok().body(updateAnswer);
		
	}

	@PostMapping("/addAnswer")
	public Answer addAnswer(@RequestBody AnswerDto answerDto) {

		Answer answer = new Answer();

		Class cls = new Class();
		cls.setClassID(answerDto.getClassId());

		Module module = new Module();
		module.setModuleId(answerDto.getModuleId());

		Trainee trainee = new Trainee();
		trainee.setUserName(answerDto.getTraineeId());
		
		Question question = new Question();
		question.setQuestionID(answerDto.getQuestionID());

		answer.setAnswerKey(new AnswerKey(cls, module, trainee, question));
		answer.setValue(answerDto.getValue());
		return answerRepository.save(answer);
	}

	@DeleteMapping("/deleteAnswer/{classId}/{moduleId}/{traineeId}/{questionId}")
	public Map<String, Boolean> deleteAssignment(@PathVariable(value = "classId") vn.com.r2s.fms.api.entity.Class classId,
			@PathVariable(value = "moduleId") Module moduleId,
			@PathVariable(value = "trainerId") Trainee traineeId,
			@PathVariable(value = "questionId") Question question)	throws ResourceNotFoundException {
		AnswerKey answerKey = new AnswerKey(classId, moduleId, traineeId,question);
		Answer answer = answerRepository.findById(answerKey).orElseThrow(() -> new ResourceNotFoundException("Assignment Not Found"));
		answerRepository.delete(answer);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	
}