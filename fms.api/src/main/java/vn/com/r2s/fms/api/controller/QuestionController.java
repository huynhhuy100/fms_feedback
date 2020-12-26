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
import vn.com.r2s.fms.api.entity.Question;
import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AdminRepository;
import vn.com.r2s.fms.api.repository.QuestionRepository;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/Question")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;

	@GetMapping("/getAllQuestion")
	public List<Question> getAllQuestion(){
		return questionRepository.findAll();
	}

	@GetMapping("getQuestionById/{questionID}")
	public ResponseEntity<Question> getQuestionById(@PathVariable(value = "questionID") int questionID) throws ResourceNotFoundException{
		Question question =
				questionRepository.findById(questionID).orElseThrow(() -> new ResourceNotFoundException("User not found ::" + questionID));
		return ResponseEntity.ok().body(question);
	}

/*	@GetMapping("getQuestionByIdTopic/{topicId}")
	public ResponseEntity<Question> getQuestionByIdTopic(@PathVariable(value = "topicId") int topicId) throws ResourceNotFoundException{
		Question question =
				questionRepository.findById(topicId).orElseThrow(() -> new ResourceNotFoundException("User not found ::" + topicId));
		return ResponseEntity.ok().body(question);
	}*/

	@PostMapping("/Addquestion")
	public Question createUser(@Validated @RequestBody Question question) {
		return questionRepository.save(question);
	}

	@PutMapping("/updatequestion/{questionID}")
	public ResponseEntity<Question> updateQuestion(@PathVariable(value = "questionID") int questionID, @Validated @RequestBody Question questionDetails) throws ResourceNotFoundException{
		Question question = questionRepository
				.findById(questionID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found ::" + questionID));
		question.setIsdeleted(questionDetails.isIsdeleted());
		question.settopicId(questionDetails.gettopicId());
		question.setQuestionContent(questionDetails.getQuestionContent());

		final Question updateUser  = questionRepository.save(question);
		return ResponseEntity.ok(updateUser);
	}

	@DeleteMapping("/deletequestion/{questionID}")
	public Map<String, Boolean> deleQuestion(@PathVariable(value = "questionID") int questionID) throws ResourceNotFoundException{
		Question question = questionRepository
				.findById(questionID)
				.orElseThrow(() -> new ResourceNotFoundException("User not found :" + questionID));
		questionRepository.delete(question);
		Map<String,Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;
	}
}

