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

import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.service.TopicService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TopicIntegrationTest {

	private TopicService topicService;
	private TsscTopic topic;
	
	@Autowired
	public TopicIntegrationTest(TopicService topicService) {
		// TODO Auto-generated constructor stub
		this.topicService=topicService;
	}
	
	@BeforeEach
	public void setup() {
		topic=new TsscTopic();
		topic.setName("Integration Topic");
		topic.setDescription("integration description");
		topic.setGroupPrefix("TP");
		
		topic.setDefaultGroups(2);
		topic.setDefaultSprints(2);
	}
	
	
	@Test
	@DisplayName("1. Integration save topic test")
	@Order(1)
	void saveTopicIntegrationTest() {
		
		try {
			assertEquals(topic, topicService.saveTopic(topic));
		} catch (NotEnoughGroupsException | NotEnoughSprintsException | NullTopicException
				| DuplicatedEntityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}

	@Test
	@DisplayName("2. Integration update topic")
	@Order(2)
	void updateTopicIntegrationTest() {
		try {
			
			TsscTopic actual=new TsscTopic();
			actual.setId(topicService.saveTopic(topic).getId());	// se crea un nuevo objeto y se copia el id del que se guarda	
			// se hacen modificaciones al nuevo objeto
			actual.setDescription("new Description");
			actual.setDefaultGroups(3);
			actual.setDefaultSprints(5);
			actual.setName(topic.getName());
			actual.setGroupPrefix(topic.getGroupPrefix());
			// se uso el nuevo objeto para actualizar el ya guadado dado que comparten id
			topic=topicService.updateTopic(actual);
			// se compruban que las caracteristicas sean iguales
			assertEquals(topic.getId(),actual.getId() );
			assertEquals(topic.getDescription(), actual.getDescription());
			assertEquals(topic.getDefaultGroups(),actual.getDefaultGroups());
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	
}
