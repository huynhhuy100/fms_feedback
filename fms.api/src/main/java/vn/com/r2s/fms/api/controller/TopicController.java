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
import vn.com.r2s.fms.api.entity.Topic;
import vn.com.r2s.fms.api.entity.User;
import vn.com.r2s.fms.api.exception.ResourceNotFoundException;
import vn.com.r2s.fms.api.repository.AdminRepository;
import vn.com.r2s.fms.api.repository.TopicRepository;
import vn.com.r2s.fms.api.repository.UserRepository;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
	@Autowired
	private TopicRepository topicRepository;

	//get all topics
	@GetMapping("/getAllTopics")
	public List<Topic> getAllTopic(){
		return topicRepository.findAll();
	}

	//Get topic by id
	@GetMapping("/gettopicID/{topicId}")
	public ResponseEntity<Topic> getTopicById(@PathVariable(value = "topicId") int topicId)
			throws ResourceNotFoundException{
		Topic topic =
				topicRepository
						.findById(topicId).orElseThrow(()->new ResourceNotFoundException("Topic not found on ::"+ topicId));

		return ResponseEntity.ok().body(topic);
	}

	//Create topic
	@PostMapping("/addtopics")
	public Topic createTopic(@Validated @RequestBody Topic topic) {
		return topicRepository.save(topic);
	}

	//Update topic
	@PutMapping("/updatetopics/{topicID}")
	public ResponseEntity<Topic> updateTopic(@PathVariable(value = "topicID") int topicID
			, @Validated @RequestBody Topic topicDetails) throws ResourceNotFoundException{
		Topic topic =
				topicRepository.findById(topicID)
						.orElseThrow(()->new ResourceNotFoundException("Topic not found on ::"+topicID));
		topic.setTopicName(topicDetails.getTopicName());

		final Topic updateTopic = topicRepository.save(topic);

		return ResponseEntity.ok(updateTopic);

	}
	//Delete topic
	@DeleteMapping("/deletetopics/{topicID}")
	public Map<String, Boolean> deleteTopic(@PathVariable(value = "topicID") int topicID) throws Exception{
		Topic topic =
				topicRepository
						.findById(topicID)
						.orElseThrow(()->new ResourceNotFoundException("Topic not found on ::"+topicID));

		topicRepository.delete(topic);
		Map<String,Boolean> responce = new HashMap<>();
		responce.put("deleted",Boolean.TRUE);

		return responce;
	}

}

