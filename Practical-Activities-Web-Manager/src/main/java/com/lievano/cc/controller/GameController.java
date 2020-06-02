package com.lievano.cc.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lievano.cc.delegate.GameDelegate;
import com.lievano.cc.delegate.TopicDelagate;

import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscGameValidateBasic;
import com.lievano.cc.model.TsscGameValidateComp;
import com.lievano.cc.model.TsscGameValidateEdit;
import com.lievano.cc.model.TsscGameValidateTopic;
import com.lievano.cc.service.GameService;

@Controller
public class GameController {

	//private GameService gameService;
	private TopicDelagate topicDelegate;
	private GameDelegate gameDelegate;
	
	@Autowired
	public GameController(GameService gameService,TopicDelagate topicDelegate,GameDelegate gameDelegate) {
		///this.gameService=gameService;
		this.topicDelegate=topicDelegate;
		this.gameDelegate=gameDelegate;
	}
	
	@GetMapping("/games/")
	public String indexGames(Model model) {
		model.addAttribute("games",gameDelegate.findAll());
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
					tsscGame.setNGroups(4);
					tsscGame.setNSprints(4);
					tsscGame=gameDelegate.save(tsscGame);
					
				
				//model.addAttribute("game",tsscGame);
					//model.addAttribute("gameid",tsscGame.getId());
					model.addAttribute("tsscGame",tsscGame);
				if(action.equals("usar tema")) {
					model.addAttribute("topics",topicDelegate.findAll());
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
				
					TsscGame gameParc= gameDelegate.findById(tsscGame.getId());
					gameParc.setNGroups(tsscGame.getNGroups());
					gameParc.setNSprints(tsscGame.getNSprints());
					gameDelegate.update(gameParc);
				
			}
		}
		return "redirect:/games/";
	}
	
	@PostMapping("/games/addtopic")
	public String addGameTopic(@Validated(TsscGameValidateTopic.class) TsscGame tsscGame,BindingResult bindingResult,@RequestParam(value = "action",required = true) String action,Model model) {
		if(!action.equals("Cancel")) {
			
			if(bindingResult.hasErrors()) {
				model.addAttribute("topics",topicDelegate.findAll());
				return"/games/add-game-topic";
			}else {
				TsscGame gameParc=gameDelegate.findById(tsscGame.getId());
				gameParc.setTsscTopic(tsscGame.getTsscTopic());
				gameDelegate.save(gameParc);
				
			}
			
		}
		return "redirect:/games/";
	}
	
	
	@GetMapping("/games/edit/{id}")
	public String editGame(@PathVariable("id")long id,Model model) {
		TsscGame tsscGame=gameDelegate.findById(id);
		if (tsscGame == null)
			throw new IllegalArgumentException("Invalid game Id:" + id);
		
		model.addAttribute("tsscGame",tsscGame);
		return"/games/edit-game";
	}

	@PostMapping("/games/edit/{id}")
	public String editGame(@PathVariable("id") long id,@RequestParam(value = "action",required = true) String action,
			@Validated(TsscGameValidateEdit.class) TsscGame tsscGame,BindingResult bindingResult, Model model) {
		if(!action.equals("Cancel")) {
			if(bindingResult.hasErrors()) {
				return"/games/edit-game";
			}else {
				
					gameDelegate.update(tsscGame);
				
			}
			
			
		}
		
		
		
		return "redirect:/games/";
	}
	
}
