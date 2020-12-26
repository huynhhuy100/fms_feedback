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
import vn.com.r2s.fms.api.entity.Feedback;
import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AdminRepository;
import vn.com.r2s.fms.api.repository.FeedbackRepository;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {
	
	@Autowired
	private FeedbackRepository feedbackRepository;

	@GetMapping("/getallfeedbacks")
	public List<Feedback> getallfeedback(){
	 return feedbackRepository.findAll();	
	}

	@PostMapping("/addFeedbacks")
	public Feedback createFeedback(@Validated @RequestBody Feedback feedback) {
		return feedbackRepository.save(feedback);
	}
	
	@GetMapping("/getFeedbacksById/{feedbackID}")
	public ResponseEntity<Feedback> getFeedbacksById(@PathVariable(value = "feedbackID") int feedbackID) throws ResourceNotFoundException{
		Feedback feedback = 
				feedbackRepository
				.findById(feedbackID).orElseThrow(() -> new ResourceNotFoundException("FeedbackID not found ::" + feedbackID));
		return ResponseEntity.ok().body(feedback);
	}
	
	@PutMapping("/updateFeedbacks/{feedbackID}")
	public ResponseEntity<Feedback> updateFeedback(@PathVariable(value = "feedbackID") int feedbackID, @Validated @RequestBody Feedback feedbackDetails) throws ResourceNotFoundException{
		Feedback feedback = feedbackRepository	
				.findById(feedbackID)
				.orElseThrow(() -> new ResourceNotFoundException("FeedbackID not found ::" + feedbackID));
		feedback.setTitle(feedbackDetails.getTitle());
		feedback.setAdminID(feedbackDetails.getAdminID());
		feedback.setisDeleted(feedbackDetails.isDeleted());;
		feedback.settypeFeedbackID(feedbackDetails.gettypeFeedbackID());
		final Feedback updateFeedback  = feedbackRepository.save(feedback); 
		return ResponseEntity.ok(updateFeedback);
	}

	@DeleteMapping("/deleteFeedback/{feedbackID}")
	public Map<String, Boolean> deleteFeedback(@PathVariable(value = "feedbackID") int feedbackID) throws ResourceNotFoundException{
		Feedback feedback = feedbackRepository
				.findById(feedbackID)
				.orElseThrow(() -> new ResourceNotFoundException("Deleted FeedbackID  ::" + feedbackID));
		feedbackRepository.delete(feedback);
		Map<String,Boolean> respone = new HashMap<>();
		respone.put("deleted", Boolean.TRUE);
		return respone;
	}
}
