package com.lievano.cc.service;

import java.util.Optional;

import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.IlegalPriorityValueException;
import com.lievano.cc.exceptions.NotEnoughBussinesValueException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscStory;

public interface StoryService {

	
	TsscStory saveStory(TsscStory story, long gameId) throws NotEnoughGroupsException,NotEnoughSprintsException, DuplicatedEntityException, NullTopicException, EntityNotExistException, NotEnoughBussinesValueException, IlegalPriorityValueException;
	TsscStory updateStory(TsscStory story, long gameId) throws EntityNotExistException,NotEnoughGroupsException,NotEnoughSprintsException, NullTopicException, NotEnoughBussinesValueException, IlegalPriorityValueException;
	TsscStory updateStoryWiouhtGame(TsscStory story) throws EntityNotExistException,NotEnoughGroupsException,NotEnoughSprintsException, NullTopicException, NotEnoughBussinesValueException, IlegalPriorityValueException;
	
	Iterable<TsscStory> findAll();
	Optional<TsscStory> findById(long id);
	Iterable<TsscStory> findAllByTsscGameId(long tsscGameId);
}
