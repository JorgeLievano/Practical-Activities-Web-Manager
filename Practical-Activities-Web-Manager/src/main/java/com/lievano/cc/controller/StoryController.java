package com.lievano.cc.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lievano.cc.delegate.StoryDelegate;
import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.IlegalPriorityValueException;
import com.lievano.cc.exceptions.NotEnoughBussinesValueException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscAcceptanceCriteria;
import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscStory;
import com.lievano.cc.service.StoryService;

@Controller
public class StoryController {

	private StoryDelegate storyDelegate;
	//private StoryService storyService;
	
	@Autowired
	public StoryController(StoryService storyService,StoryDelegate storyDelegate) {
		this.storyDelegate=storyDelegate;
		//this.storyService=storyService;
	}
	
	
	@GetMapping("/stories/")
	public String indexStorires(Model model) {
		model.addAttribute("stories",storyDelegate.findAll());
		return("/stories/index");
	}
	
	@GetMapping("/stories/list/{gameId}")
	public String list(@PathVariable("gameId")long gameId,Model model) {
		model.addAttribute("stories",storyDelegate.findByGameid(gameId));
		model.addAttribute("gameId",gameId);
		return"/stories/list";
	}
	
	@GetMapping("/stories/addfromgame/{gameId}")
	public String addFromGame(@PathVariable("gameId")long gameId ,Model model) {
		model.addAttribute("tsscStory", new TsscStory());
		model.addAttribute("gameId",gameId);
		return"/stories/add-story-topic";
	}
	
	@PostMapping("/stories/addfromgame/{gameId}")
	public String addFromGame(@PathVariable("gameId")long gameId,@Validated TsscStory tsscStory, BindingResult bindingResult,
			@RequestParam(value = "action",required = true)String action,Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				model.addAttribute("gameId", gameId);
				return"/stories/add-story-topic";
			}else {
				
				
					TsscGame prov= new TsscGame();
					prov.setId(gameId);
					tsscStory.setTsscGame(prov);
					storyDelegate.save(tsscStory);
				
			}
			
			
		}
		return"redirect:/stories/";
	}
	
	
	@GetMapping("/stories/edit/{id}")
	public String editStory(@PathVariable("id")long id,Model model) {
		TsscStory story=storyDelegate.findById(id);
		if(story==null)
			throw new IllegalArgumentException("Invalid story Id:" + id);
		
		model.addAttribute("tsscStory",story);
		return "/stories/edit-story";
	}
	
	
	@PostMapping("/stories/edit/{id}")
	public String editStory(@PathVariable("id")long id, @RequestParam(value = "action",required = true)String action,
			@Validated TsscStory tsscStory,BindingResult bindingResult,Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return "/stories/edit-story";
			}else {
				
					storyDelegate.update(tsscStory);
					//storyService.updateStoryWiouhtGame(tsscStory);
				
			}
			
		}
		return"redirect:/stories/";
	}
	
}
