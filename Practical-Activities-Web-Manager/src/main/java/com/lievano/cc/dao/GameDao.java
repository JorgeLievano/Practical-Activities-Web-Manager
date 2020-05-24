package com.lievano.cc.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.lievano.cc.model.TsscGame;

public interface GameDao {

	void save(TsscGame game);
	void update(TsscGame game);
	
	
	List<TsscGame> findByName(String name);
	List<TsscGame> findByDescription(String description);
	List<TsscGame> findByTopicId(long topicId);
	List<TsscGame> findByScheduledDateBetween(LocalDate startScheduledDate, LocalDate endScheduledDate);
	List<TsscGame> findByScheduledDateAndScheduledTimeBetween(LocalDate scheduledDate,LocalTime startScheduleTime,LocalTime endtScheduleTime);
	List<TsscGame> findByScheduleDateWithLessStoriesOrNoTimeControls(LocalDate scheduledDate,int maxStories);
	TsscGame findById(long id);
	List<TsscGame> findAll();
	boolean existsById(long id);
	
}
