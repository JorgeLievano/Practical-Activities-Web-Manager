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
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscGame;
import com.lievano.cc.service.GameService;

@RestController
public class GameRestController {

	private GameService gameService;
	
	@Autowired
	public GameRestController(GameService gameService) {
		this.gameService=gameService;
	}
	
	
	@GetMapping("/api/games")
	public Iterable<TsscGame>  findAll() {
		return gameService.findAll();
	}
	
	@GetMapping("/api/games/{id}")
	public TsscGame findById(@PathVariable("id") long id) {
		return gameService.findById(id).get();
	}
	
	@PostMapping("/api/games")
	public TsscGame save(@RequestBody TsscGame tsscGame) {
		TsscGame game = null;
	
		try {
			if(tsscGame.getTsscTopic()==null) {
				game=gameService.saveGame(tsscGame);
			}else {
				game= gameService.saveGame2(tsscGame, tsscGame.getTsscTopic());
			}
			
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | DuplicatedEntityException | NullTopicException
				| EntityNotExistException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	
	
	@PutMapping("/api/games")
	public TsscGame update(@RequestBody TsscGame tsscGame) {
		TsscGame game=null;
		
		try {
			game= gameService.updateGame(tsscGame);
		} catch (EntityNotExistException | NotEnoughGroupsException | NotEnoughSprintsException
				| NullTopicException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return game;
	}
	
}
