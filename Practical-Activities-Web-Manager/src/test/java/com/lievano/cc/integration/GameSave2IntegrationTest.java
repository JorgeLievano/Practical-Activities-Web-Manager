package com.lievano.cc.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.service.GameService;
import com.lievano.cc.service.StoryService;
import com.lievano.cc.service.TopicService;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameSave2IntegrationTest {

	private GameService gameService;
	private StoryService storyService;
	private TopicService topicService;
	private TsscTopic topic;
	private TsscGame game;
	private List<TsscStory> stories;
	
	@Autowired
	public GameSave2IntegrationTest(GameService gameService, StoryService storyService, TopicService topicService) {
		// TODO Auto-generated constructor stub
		this.gameService=gameService;
		this.topicService=topicService;
		this.storyService=storyService;
	}

	
	@BeforeEach
	void setup() {
		game=new TsscGame();
		game.setName("Game Test");
		game.setTsscStories(new ArrayList<TsscStory>());
		
		TsscStory story1= new TsscStory();
		story1.setAltDescripton("Altdescrip 1");
		story1.setAltDescShown("Altdescrp1");
		story1.setBusinessValue(new BigDecimal(7));
		story1.setDescription("xxxxxxxx");
		story1.setInitialSprint(new BigDecimal(3));
		story1.setNumber(new BigDecimal(4));
		story1.setPriority(new BigDecimal(5));
		story1.setShortDescription("zzzzzzzzzz");
		story1.setTsscTopic(topic);
		
		TsscStory story2= new TsscStory();
		story2.setAltDescripton("Altdescrip 2");
		story2.setAltDescShown("Altdescrp1");
		story2.setBusinessValue(new BigDecimal(7));
		story2.setDescription("xxxxxxxx");
		story2.setInitialSprint(new BigDecimal(3));
		story2.setNumber(new BigDecimal(4));
		story2.setPriority(new BigDecimal(5));
		story2.setShortDescription("zzzzzzzzzz");
		story2.setTsscTopic(topic);
		
		stories=new ArrayList<>();
		stories.add(story1);
		stories.add(story2);
		
		
		topic=new TsscTopic();
		topic.setDefaultGroups(5);
		topic.setDefaultSprints(3);
		topic.setName("Topic test");
		topic.setDescription("description test");
		topic.setGroupPrefix("TTP");
		topic.setTsscStories(stories);
		
		
	}
	
	
	
	@Test
	@DisplayName("1. save game 2v")
	@Order(1)
	void saveGame2() {
		try {
			
			
			stories.get(0);
			topic=topicService.saveTopic(topic);
			game=gameService.saveGame2(game, topic);
			

			
			assertEquals(game.getNGroups(),(int) topic.getDefaultGroups());
			assertEquals(game.getNSprints(), (int) topic.getDefaultSprints());

			assertNotEquals(stories.get(0).getId(),game.getTsscStories().get(0) );
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
		
	}
}
