package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscStory;

@Repository
public interface StoryRepository extends CrudRepository<TsscStory, Long>{

	Iterable<TsscStory> findAllByTsscGameId(long tsscGameId);
}
