package com.lievano.cc.service;

import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;

import java.util.Optional;

import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscTopic;

public interface GameService {

	public TsscGame saveGame(TsscGame game) throws NotEnoughGroupsException,NotEnoughSprintsException, DuplicatedEntityException, NullTopicException, EntityNotExistException;
	public TsscGame saveGame2(TsscGame game, TsscTopic topic)throws EntityNotExistException;
	public TsscGame updateGame(TsscGame game) throws EntityNotExistException,NotEnoughGroupsException,NotEnoughSprintsException, NullTopicException;
	
	Iterable<TsscGame> findAll();
	
	Optional<TsscGame> findById(long id);
}
