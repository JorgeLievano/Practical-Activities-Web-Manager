package com.lievano.cc.daoTest;

import static org.junit.jupiter.api.Assertions.*;

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

import com.lievano.cc.dao.TopicDao;
import com.lievano.cc.model.TsscTopic;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TopicDaoTest {

	private TopicDao dao;
	private TsscTopic topic;
	
	@Autowired
	public TopicDaoTest(TopicDao dao) {
		// TODO Auto-generated constructor stub
		this.dao=dao;
	}
	
	
	@BeforeEach
	void setup() {
		topic= new TsscTopic();
		topic.setDefaultSprints(3);
		topic.setDefaultGroups(5);
	}

	@Test
	@DisplayName("1. Topic dao find by topic name")
	@Order(1)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByNameTopicDao() {
		topic.setName("Topic uno");
		topic.setDescription("El primer topic");
		topic.setGroupPrefix("T1");
		dao.save(topic);
		
		List<TsscTopic> r=dao.findByName(topic.getName());
		assertEquals(topic.getName(), r.get(0).getName());
		assertEquals(topic.getDescription(), r.get(0).getDescription());
	}
	
	@Test
	@DisplayName("2. Topic dao find by topic description")
	@Order(2)
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	void findByDescriptionTopicDao() {
		topic.setName("Topic dos");
		topic.setDescription("El segundo topic");
		topic.setGroupPrefix("T2");
		dao.save(topic);
		
		List<TsscTopic> r=dao.findByDescription(topic.getDescription());
		assertEquals(topic.getName(), r.get(0).getName());
		assertEquals(topic.getDescription(), r.get(0).getDescription());
	}
	
}
