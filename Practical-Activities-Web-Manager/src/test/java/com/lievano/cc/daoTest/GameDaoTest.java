package com.lievano.cc.daoTest;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lievano.cc.dao.GameDao;
import com.lievano.cc.dao.StoryDao;
import com.lievano.cc.dao.TimeControlDao;
import com.lievano.cc.dao.TopicDao;
import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscStory;
import com.lievano.cc.model.TsscTimecontrol;
import com.lievano.cc.model.TsscTopic;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameDaoTest {

	private GameDao gameDao;
	private TsscGame  game;
	private TopicDao topicDao;
	private TsscTopic topic;
	
	private TimeControlDao tmDao;
	private StoryDao storyDao;
	
	@Autowired
	public GameDaoTest(GameDao gameDao, TopicDao topicDao,TimeControlDao tmDao,StoryDao storyDao) {
		this.gameDao=gameDao;
		this.topicDao=topicDao;
		this.tmDao=tmDao;
		this.storyDao=storyDao;
	}

	@BeforeEach
	void setup() {
		game= new TsscGame();
		game.setNGroups(6);
		game.setNSprints(3);
		
		
		topic= new TsscTopic();
		topic.setDefaultGroups(6);
		topic.setDefaultSprints(3);
		
	}
	
	
	@Test
	@DisplayName("1 Game dao find by game name")
	@Order(1)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByNameGameDao() {
		game.setName("Game 1");
		gameDao.save(game);
		
		TsscGame r= gameDao.findByName(game.getName()).get(0);
		
		assertNotNull(r);
		assertEquals(game.getName(), r.getName());
		assertEquals(game.getNGroups(), r.getNGroups());
	}
	
	/*
	@Test
	@DisplayName("2 Game dao find by game Description")
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByDescriptionGameDao() {
		// TsscGame doesn't have a description attribute
		fail();
	}*/
	
	@Test
	@DisplayName("3 Game dao find by topic id")
	@Order(3)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByTopicIdGameDao() {
		topic.setName("Topic3");
		topic.setDescription("topic 3");
		topic.setGroupPrefix("T3");
		
		game.setName("Game 3");
		game.setTsscTopic(topic);
		
		TsscGame aux= new TsscGame();
		aux.setName("aux1");
		aux.setNGroups(8);
		aux.setNSprints(9);
		
		topicDao.save(topic);
		gameDao.save(game);
		gameDao.save(aux);
		
		List<TsscGame> list= gameDao.findByTopicId(topicDao.findByName(topic.getName()).get(0).getId());
		assertEquals(1, list.size());
		assertEquals(list.get(0).getTsscTopic().getId(), topicDao.findByName(topic.getName()).get(0).getId());
	}
	
	@Test
	@DisplayName("4 Game dao find by scheduled date range")
	@Order(4)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByScheduledDateRangeGameDao() {
		game.setName("Game 4");
		game.setScheduledDate(LocalDate.of(2020, 05, 06));
		gameDao.save(game);
		
		List<TsscGame> list = gameDao.findByScheduledDateBetween(LocalDate.of(2020, 05, 05), LocalDate.of(2020, 05, 07));
		
		assertEquals(1, list.size());
		assertEquals(gameDao.findByName(game.getName()).get(0).getId(), list.get(0).getId());
	}
	
	@Test
	@DisplayName("5 Game dao find by scheduled date and scheduled time range")
	@Order(5)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByScheduledDateAndScheduledTimeRangeGameDao() {
		game.setName("Game 5");
		game.setScheduledDate(LocalDate.of(1945, 04, 07));
		game.setScheduledTime(LocalTime.of(11,20));
		
		gameDao.save(game);
		
		List<TsscGame>list=gameDao.findByScheduledDateAndScheduledTimeBetween(game.getScheduledDate(), LocalTime.of(6,20), LocalTime.of(11,50));
		assertEquals(1, list.size());
		assertEquals(gameDao.findByName(game.getName()).get(0).getId(), list.get(0).getId());
	}
	
	//pruebas punto 2.b
	@Test
	@DisplayName("6.1 find by scheduled date where stories < 10 Ignore TimeControls")
	@Order(6)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByScheduledDateLessStoriesGameDao() {
		int limit=10;
		game.setName("Game 6.1");
		game.setScheduledDate(LocalDate.of(2020, 12, 20));
		
		
		
		TsscStory story=new TsscStory();
		story.setBusinessValue(new BigDecimal(9));
		story.setDescription("story game 6.1");
		story.setInitialSprint(new BigDecimal(6));
		story.setNumber(new BigDecimal(8));
		story.setPriority(new BigDecimal(8));
		
		
		TsscTimecontrol tm=new TsscTimecontrol();
		tm.setTsscGame(game);
		tmDao.save(tm);
		//game.addTsscStory(story);
		
		gameDao.save(game);
		story.setTsscGame(gameDao.findByName(game.getName()).get(0));
		storyDao.save(story);
		
		List<TsscGame> list =gameDao.findByScheduleDateWithLessStoriesOrNoTimeControls(game.getScheduledDate(), limit);
		
		assertEquals(1, list.size());
		assertEquals(gameDao.findByName(game.getName()).get(0).getId(), list.get(0).getId());
		
	}
	
	@Test
	@DisplayName("6.2 find by scheduled date where not Time controls ignore stories")
	@Order(7)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByScheduledDateNonTimeControlsGameDao() {
		int limit=10;
		game.setName("Game 6.2");
		game.setScheduledDate(LocalDate.of(2020, 12, 21));
		gameDao.save(game);
		
		for(int i=0;i<12;i++) {
			TsscStory story=new TsscStory();
			story.setBusinessValue(new BigDecimal(9));
			story.setDescription("story game 6.2"+"---"+i);
			story.setInitialSprint(new BigDecimal(6));
			story.setNumber(new BigDecimal(8));
			story.setPriority(new BigDecimal(8));
			story.setTsscGame(gameDao.findByName(game.getName()).get(0));
			storyDao.save(story);
		}
		
		List<TsscGame> list =gameDao.findByScheduleDateWithLessStoriesOrNoTimeControls(game.getScheduledDate(), limit);
		
		assertEquals(1, list.size());
		assertEquals(gameDao.findByName(game.getName()).get(0).getId(), list.get(0).getId());
	}
	
}
