package com.lievano.cc.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.util.Pair;

import com.lievano.cc.model.TsscTopic;

public interface TopicDao {

	List<TsscTopic> findByName(String name);
	List<TsscTopic> findByDescription(String description);
	
	List<TsscTopic> findAll();
	TsscTopic findById(long id);
	void save(TsscTopic tsscTopic);
	void update(TsscTopic tsscTopic);
	//punto 2.a del taller
	List<Pair<TsscTopic, Integer>> findTopicsByDateWithGameCount(LocalDate date);
	boolean existsById(long Id);
	
}
