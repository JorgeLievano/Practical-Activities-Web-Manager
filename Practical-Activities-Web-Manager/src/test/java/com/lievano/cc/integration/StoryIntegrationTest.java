package com.lievano.cc.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscStory;
import com.lievano.cc.service.GameService;
import com.lievano.cc.service.StoryService;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StoryIntegrationTest {

	private StoryService storyService;
	private GameService gameService;
	private TsscStory story;
	private TsscGame game;
	
	@Autowired
	public StoryIntegrationTest(StoryService storyService, GameService gameService) {
		this.storyService=storyService;
		this.gameService=gameService;
	}

	@BeforeEach
	void setup() {
		story= new TsscStory();
		story.setInitialSprint(new BigDecimal(7));
		story.setBusinessValue(new BigDecimal(7));
		story.setPriority(new BigDecimal(7));
		story.setNumber(new BigDecimal(7));
		story.setDescription("la descripcion");
		
		game= new TsscGame();
		game.setName("gametest");
		game.setNGroups(2);
		game.setNSprints(2);
		
	}
	
	
	@Test
	@DisplayName("1. save story")
	@Order(1)
	void saveStory() {
		try {
			game=gameService.saveGame(game);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			TsscStory storysaved= storyService.saveStory(story, game.getId());
			assertNotNull(storysaved);
			assertEquals(story.getInitialSprint(), storysaved.getInitialSprint());
			assertEquals(story.getBusinessValue(), storysaved.getBusinessValue());
			assertEquals(story.getPriority(),storysaved.getPriority());
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	@DisplayName("2. update story")
	@Order(2)
	void updateStory() {
		TsscStory change= new TsscStory();
		change.setAltDescripton("new description");
		change.setBusinessValue(new BigDecimal(3));
		change.setPriority(new BigDecimal(1));
		change.setInitialSprint(new BigDecimal(25));
		
		game.setName("new game");
		//game.setId(3);
		try {
			game=gameService.saveGame(game);
			story=storyService.saveStory(story, game.getId());
			change.setId(story.getId());
			
			story=storyService.updateStory(change, game.getId());
			
			assertEquals(change.getId(), story.getId());
			assertEquals(change.getAltDescripton(),story.getAltDescripton());
			assertEquals(change.getBusinessValue(), story.getBusinessValue());
			assertEquals(change.getPriority(), story.getPriority());
			assertEquals(change.getInitialSprint(), story.getInitialSprint());
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	

	
}
