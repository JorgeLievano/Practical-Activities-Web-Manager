package com.lievano.cc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.IlegalPriorityValueException;
import com.lievano.cc.exceptions.NotEnoughBussinesValueException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscStory;
import com.lievano.cc.service.StoryService;

@RestController
public class StoryRestController {

	private StoryService storyService;
	
	@Autowired
	public StoryRestController(StoryService storyService) {
		this.storyService = storyService;
	}
	
	
	@GetMapping("/api/stories")
	public Iterable<TsscStory> findAll(){
		return storyService.findAll();
	}
	
	
	@GetMapping("/api/stories/{id}")
	public TsscStory findById(@PathVariable("id")long id) {
		return storyService.findById(id).get();
	}
	
	@GetMapping("/api/stories/game/{gameid}")
	public Iterable<TsscStory> findByGameId(@PathVariable("gameid")long gameid){
		return storyService.findAllByTsscGameId(gameid);
	}
	
	@PostMapping("/api/stories")
	public TsscStory save(@RequestBody TsscStory tsscStory) {
		TsscStory story = null;
		
		try {
			story = storyService.saveStory(tsscStory, tsscStory.getTsscGame().getId());
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | DuplicatedEntityException | NullTopicException
				| EntityNotExistException | NotEnoughBussinesValueException | IlegalPriorityValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return story;
	}
	
	@PutMapping("/api/stories")
	public TsscStory update(@RequestBody TsscStory tsscStory) {
		TsscStory story= null;
		try {
			story = storyService.updateStoryWiouhtGame(tsscStory);
		} catch (EntityNotExistException | NotEnoughGroupsException | NotEnoughSprintsException | NullTopicException
				| NotEnoughBussinesValueException | IlegalPriorityValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return story;
	}
	
}
