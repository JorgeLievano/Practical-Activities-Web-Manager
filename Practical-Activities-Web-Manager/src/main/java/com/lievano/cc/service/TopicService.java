package com.lievano.cc.service;

import java.util.Optional;

import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.model.TsscTopic;

public interface TopicService {

	public TsscTopic saveTopic(TsscTopic topic) throws NotEnoughGroupsException, NotEnoughSprintsException, NullTopicException,DuplicatedEntityException;
	
	public TsscTopic updateTopic(TsscTopic topic) throws NotEnoughGroupsException, NotEnoughSprintsException,NullTopicException,EntityNotExistException ;
	
	public Iterable<TsscTopic> findAll();
	
	public Optional<TsscTopic> findById(long id);
	
}
