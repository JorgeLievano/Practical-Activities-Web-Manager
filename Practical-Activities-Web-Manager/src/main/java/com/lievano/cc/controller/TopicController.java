package com.lievano.cc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lievano.cc.delegate.TopicDelagate;
import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.service.TopicService;

@Controller
public class TopicController {

	//private TopicService topicService;
	private TopicDelagate topicDelegate;
	
	
	@Autowired
	public TopicController(TopicService topicService,TopicDelagate topicDelegate) {
		this.topicDelegate= topicDelegate;
		//this.topicService=topicService;
	}
	
	@GetMapping("/topics/")
	public String indexTopics(Model model) {
		model.addAttribute("topics", topicDelegate.findAll());
		return "topics/index";
	}
	
	@GetMapping("/topics/add")
	public String addTopic(Model model) {
		TsscTopic topic= new TsscTopic();
		model.addAttribute("tsscTopic",topic);
		return "/topics/add-topic";
	}
	
	@PostMapping("/topics/add")
	public String addTopic(@Validated TsscTopic tsscTopic,BindingResult bindingResult,@RequestParam(value="action",required = true)String action,Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return "topics/add-topic";
			}else {
						topicDelegate.add(tsscTopic);
			}
		}
		return "redirect:/topics/";
	}
	
	@GetMapping("/topics/edit/{id}")
	public String editTopic(@PathVariable("id") long id,Model model) {
		TsscTopic topic= topicDelegate.findById(id);
		if (topic == null)
			throw new IllegalArgumentException("Invalid topic Id:" + id);
		
		model.addAttribute("tsscTopic",topic);
		return"/topics/edit-topic";
	}
	
	@PostMapping("/topics/edit/{id}")
	public String editTopic(@PathVariable("id") long id,@RequestParam(value="action",required = true) String action,
			@Validated TsscTopic tsscTopic,BindingResult bindingResult, Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return "topics/edit-topic";
			}else {
					topicDelegate.update(tsscTopic);
			}
		}
		return "redirect:/topics/";
	}
	
	
}
