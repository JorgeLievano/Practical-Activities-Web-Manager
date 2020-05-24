package com.lievano.cc.dao;

import java.util.List;

import com.lievano.cc.model.TsscStory;

public interface StoryDao {

	void save(TsscStory story);
	void update(TsscStory story);
	void delete(TsscStory story);
	TsscStory findById(long id);
	List<TsscStory> findAll();
	List<TsscStory> findByGameId(long gameId);
	boolean existsById(long id);
}
