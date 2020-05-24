package com.lievano.cc.integration;

import static org.junit.jupiter.api.Assertions.*;

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
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.service.GameService;
import com.lievano.cc.service.TopicService;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameIntegrationTest {

	
	private GameService gameService;
	private TopicService topicService;
	private TsscGame game;
	private TsscTopic topic;
	
	@Autowired
	public GameIntegrationTest(GameService gameService, TopicService topicService) {
		// TODO Auto-generated constructor stub
		this.gameService=gameService;
		//this.topicRepository=topicRepository;
		this.topicService=topicService;
	}
	
	
	@BeforeEach
	void setup() {
		game= new TsscGame();
		//game.setId(1111);
		game.setName("Test Game");
		game.setNGroups(2);
		game.setNSprints(2);
		
		
		topic=new TsscTopic();
		//topic.setId(1111);
		topic.setDefaultGroups(2);
		topic.setDefaultSprints(2);
	}
	
	
	@Test
	@DisplayName("1. save game without topic")
	@Order(1)
	void saveGameWithoutTopicTest() {
		try {
			TsscGame guardado=gameService.saveGame(game);
			assertNotNull(guardado);
			assertEquals(game.getName(),guardado.getName() );
		}catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
	@Test
	@DisplayName("2. save game with a topic")
	@Order(2)
	void saveGameWithtopicTest() {
		
		game.setName("gametest 2");
		try {
			topic.setName("topic");
			 topic.setDescription("topic description");
			 topic.setGroupPrefix("prefijoo");
			game.setTsscTopic(topicService.saveTopic(topic));
			
			//topicRepository.save(topic);
			TsscGame guardado=gameService.saveGame(game);
			assertNotNull(guardado);
			assertEquals(game.getName(),guardado.getName() );
			assertNotNull(guardado.getTsscTopic());
			
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
