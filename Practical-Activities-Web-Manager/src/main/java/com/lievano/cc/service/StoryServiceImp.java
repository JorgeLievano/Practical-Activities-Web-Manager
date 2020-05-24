package com.lievano.cc.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lievano.cc.dao.GameDao;
import com.lievano.cc.dao.StoryDao;
import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.IlegalPriorityValueException;
import com.lievano.cc.exceptions.NotEnoughBussinesValueException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;

import com.lievano.cc.model.TsscStory;

import com.lievano.cc.repository.GameRepository;
import com.lievano.cc.repository.StoryRepository;

@Service
public class StoryServiceImp implements StoryService {

	//private StoryRepository storyRepository;
	private StoryDao storyDao;
	//private GameRepository gameRepository;
	private GameDao gameDao;
	
	@Autowired
	public StoryServiceImp(StoryDao storyDao, GameDao gameDao) {
		// TODO Auto-generated constructor stub
		this.gameDao=gameDao;
		this.storyDao=storyDao;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscStory saveStory(TsscStory story,long gameId) throws NotEnoughGroupsException, NotEnoughSprintsException,
			DuplicatedEntityException, NullTopicException, EntityNotExistException, NotEnoughBussinesValueException, IlegalPriorityValueException {
		// TODO Auto-generated method stub
		TsscStory	result=null;
		
		if(story==null) {
			throw new NullTopicException();
		}else if(storyDao.existsById(story.getId())) {
			throw new DuplicatedEntityException(" story");
		}else {
			
			if(!gameDao.existsById(gameId)) {
				throw new EntityNotExistException();
			}else {
				if(story.getBusinessValue().intValue()<=0) {
					throw new NotEnoughBussinesValueException();
				}else if(story.getInitialSprint().intValue()<=0) {
					throw new NotEnoughSprintsException();
				}else if(story.getPriority().intValue()<=0) {
					throw new IlegalPriorityValueException();
				}else {
					story.setTsscGame(gameDao.findById(gameId));
					storyDao.save(story);
					List<TsscStory> list =storyDao.findAll();
					result= list.get(list.size()-1);
					
				}
			}
		}
		return result;
	}

	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscStory updateStory(TsscStory story, long gameId)
			throws EntityNotExistException, NotEnoughGroupsException, NotEnoughSprintsException, NullTopicException, NotEnoughBussinesValueException, IlegalPriorityValueException {
		// TODO Auto-generated method stub
	TsscStory	result=null;
		
		if(story==null) {
			throw new NullTopicException();
		}else if(!storyDao.existsById(story.getId())) {
			throw new EntityNotExistException();
		}else {
			
			if(!gameDao.existsById(gameId)) {
				throw new EntityNotExistException();
			}else {
				if(story.getBusinessValue().intValue()<=0) {
					throw new NotEnoughBussinesValueException();
				}else if(story.getInitialSprint().intValue()<=0) {
					throw new NotEnoughSprintsException();
				}else if(story.getPriority().intValue()<=0) {
					throw new IlegalPriorityValueException();
				}else {
					result= storyDao.findById(story.getId());
					result.setBusinessValue(story.getBusinessValue());
					result.setAltDescripton(story.getAltDescripton());
					result.setPriority(story.getPriority());
					result.setInitialSprint(story.getInitialSprint());
					result.setTsscGame(gameDao.findById(gameId));
					storyDao.update(result);
					
				}
			}
		}
		return result;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Iterable<TsscStory> findAll() {
		// TODO Auto-generated method stub
		return storyDao.findAll();
		
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Optional<TsscStory> findById(long id) {
		// TODO Auto-generated method stub
		return  Optional.of(storyDao.findById(id));
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Iterable<TsscStory> findAllByTsscGameId(long tsscGameId) {
		// TODO Auto-generated method stub
		return storyDao.findByGameId(tsscGameId);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscStory updateStoryWiouhtGame(TsscStory story)
			throws EntityNotExistException, NotEnoughGroupsException, NotEnoughSprintsException, NullTopicException,
			NotEnoughBussinesValueException, IlegalPriorityValueException {
		// TODO Auto-generated method stub
TsscStory	result=null;
		
		if(story==null) {
			throw new NullTopicException();
		}else if(!storyDao.existsById(story.getId())) {
			throw new EntityNotExistException();
		}else {
				if(story.getBusinessValue().intValue()<=0) {
					throw new NotEnoughBussinesValueException();
				}else if(story.getInitialSprint().intValue()<=0) {
					throw new NotEnoughSprintsException();
				}else if(story.getPriority().intValue()<=0) {
					throw new IlegalPriorityValueException();
				}else {
					result= storyDao.findById(story.getId());
					result.setBusinessValue(story.getBusinessValue());
					result.setDescription(story.getDescription());
					result.setPriority(story.getPriority());
					result.setInitialSprint(story.getInitialSprint());
					result.setNumber(story.getNumber());
					storyDao.update(result);
					
				}
			
		}
		return result;
	}

	
	
}
