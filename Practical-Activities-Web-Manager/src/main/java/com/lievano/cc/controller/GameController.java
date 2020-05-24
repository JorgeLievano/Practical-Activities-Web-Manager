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

import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscGameValidateBasic;
import com.lievano.cc.model.TsscGameValidateComp;
import com.lievano.cc.model.TsscGameValidateEdit;
import com.lievano.cc.model.TsscGameValidateTopic;
import com.lievano.cc.service.GameService;
import com.lievano.cc.service.TopicService;

@Controller
public class GameController {

	private GameService gameService;
	private TopicService topicService;
	
	@Autowired
	public GameController(GameService gameService,TopicService topicService) {
		// TODO Auto-generated constructor stub
		this.gameService=gameService;
		this.topicService=topicService;
	}
	
	@GetMapping("/games/")
	public String indexGames(Model model) {
		model.addAttribute("games",gameService.findAll());
		return("games/index");
	}
	
	@GetMapping("/games/add")
	public String addGame(Model model) {
		TsscGame game=new TsscGame();
		model.addAttribute("tsscGame",game);
		//model.addAttribute("topics",topicService.findAll());
		return "/games/add-game";
	}
	
	@PostMapping("/games/add")
	public String addGame(@Validated(TsscGameValidateBasic.class) TsscGame tsscGame,BindingResult bindingResult,@RequestParam(value = "action",required = true) String action,Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return"/games/add-game";
			}else {
				try {
					tsscGame.setNGroups(4);
					tsscGame.setNSprints(4);
					
					gameService.saveGame(tsscGame);
					
				} catch (NotEnoughGroupsException | NotEnoughSprintsException | DuplicatedEntityException
						| NullTopicException | EntityNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//model.addAttribute("game",tsscGame);
				if(action.equals("usar tema")) {
					model.addAttribute("topics",topicService.findAll());
					return"/games/add-game-topic";
				}else if(action.equals("No usar tema")) {
					return"/games/add-game-comp";
				}	
			}	
		}
		return "redirect:/games/";
	}
	
	@PostMapping("/games/addcomp")
	public String addGameComp(@Validated(TsscGameValidateComp.class) TsscGame tsscGame,BindingResult bindingResult,@RequestParam(value = "action",required = true)String action,Model model) {
		
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return"/games/add-game-comp";
			}else {
				try {
					TsscGame gameParc= gameService.findById(tsscGame.getId()).get();
					gameParc.setNGroups(tsscGame.getNGroups());
					gameParc.setNSprints(tsscGame.getNSprints());
					gameService.saveGame(gameParc);
				} catch (NotEnoughGroupsException | NotEnoughSprintsException | DuplicatedEntityException
						| NullTopicException | EntityNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return "redirect:/games/";
	}
	
	@PostMapping("/games/addtopic")
	public String addGameTopic(@Validated(TsscGameValidateTopic.class) TsscGame tsscGame,BindingResult bindingResult,@RequestParam(value = "action",required = true) String action,Model model) {
		if(!action.equals("Cancel")) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("topics",topicService.findAll());
				return"/games/add-game-topic";
			}else {
				TsscGame gameParc=gameService.findById(tsscGame.getId()).get();
				try {
					gameService.saveGame2(gameParc, tsscGame.getTsscTopic());
				} catch (EntityNotExistException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		return "redirect:/games/";
	}
	
	
	@GetMapping("/games/edit/{id}")
	public String editGame(@PathVariable("id")long id,Model model) {
		Optional<TsscGame> tsscGame=gameService.findById(id);
		if (tsscGame == null)
			throw new IllegalArgumentException("Invalid game Id:" + id);
		
		model.addAttribute("tsscGame",tsscGame.get());
		return"/games/edit-game";
	}

	@PostMapping("/games/edit/{id}")
	public String editGame(@PathVariable("id") long id,@RequestParam(value = "action",required = true) String action,
			@Validated(TsscGameValidateEdit.class) TsscGame tsscGame,BindingResult bindingResult, Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return"/games/edit-game";
			}else {
				
				try {
					gameService.updateGame(tsscGame);
				} catch (EntityNotExistException | NotEnoughGroupsException | NotEnoughSprintsException
						| NullTopicException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			
		}
		
		
		
		return "redirect:/games/";
	}
	
}
