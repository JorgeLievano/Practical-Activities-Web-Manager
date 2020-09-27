package com.lievano.cc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.service.TopicService;

@RestController
public class TopicRestController {

	private TopicService topicService;
	
	@Autowired
	public TopicRestController(TopicService topicService) {
		this.topicService = topicService;
	}
	
	
	@GetMapping("/api/topics")
	public Iterable<TsscTopic> getTopics(){
		return topicService.findAll();
	}
	
	
	@GetMapping("/api/topics/{id}")
	public TsscTopic findById(@PathVariable("id") long id) {
		return topicService.findById(id).get();
	}
	
	
	@PostMapping("/api/topics")
	public TsscTopic save(@RequestBody TsscTopic topic) {
		try {
			
			return topicService.saveTopic(topic);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	
	@PutMapping("/api/topics")
	public TsscTopic update(@RequestBody TsscTopic tsscTopic) {
		try {
			return topicService.updateTopic(tsscTopic);
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullTopicException
				| EntityNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
