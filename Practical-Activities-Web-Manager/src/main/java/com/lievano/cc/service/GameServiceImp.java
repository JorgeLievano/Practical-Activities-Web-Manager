package com.lievano.cc.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lievano.cc.dao.GameDao;
import com.lievano.cc.dao.StoryDao;
import com.lievano.cc.dao.TopicDao;
import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.EntityNotExistException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.model.TsscGame;
import com.lievano.cc.model.TsscStory;
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.repository.GameRepository;
import com.lievano.cc.repository.StoryRepository;
import com.lievano.cc.repository.TopicRepository;


@ Service
public class GameServiceImp implements GameService{

	//private GameRepository gameRepository;
	private GameDao gameDao;
	//private TopicRepository topicRepository;
	private TopicDao topicDao;
	//private StoryRepository storyRepository;
	private StoryDao storyDao;
	
	@Autowired
	public GameServiceImp(GameDao gameDao,TopicDao topicDao,StoryDao storyDao ) {
		// TODO Auto-generated constructor stub
		this.gameDao=gameDao;
		this.topicDao=topicDao;
		this.storyDao=storyDao;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscGame saveGame(TsscGame game)
			throws NotEnoughGroupsException, NotEnoughSprintsException, DuplicatedEntityException, NullTopicException, EntityNotExistException {
		// TODO Auto-generated method stub
		TsscGame result=null;
		
		if(game==null) {
			throw new NullTopicException();
		}else if(game.getNGroups().intValue()<1) {
			throw new NotEnoughGroupsException();
		}else if(game.getNSprints().intValue()<1) {
			throw new NotEnoughSprintsException();
		}else {
			TsscTopic topic=game.getTsscTopic();
			if(topic!=null && !topicDao.existsById(topic.getId())) {
				throw new EntityNotExistException();
				
			}else {
				gameDao.save(game);
				result=gameDao.findByName(game.getName()).get(0);
			}
		}
		
		
		
		return result;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscGame saveGame2(TsscGame game, TsscTopic topic)
			throws  EntityNotExistException {
		// TODO Auto-generated method stub
		TsscGame result=null;
		
		if(! topicDao.existsById(topic.getId())) {
			throw new EntityNotExistException();
		}else {
			
			result=game;
			
			result.setTsscTopic(topic);
			result.setNGroups((int) topic.getDefaultGroups());
			result.setNSprints((int)topic.getDefaultSprints() );
			
			for(TsscStory story: topic.getTsscStories()) {
				
					TsscStory copySto= cloneStory(story);
					
					//copySto=storyRepository.save(copySto);
					copySto.setTsscGame(result);
					storyDao.save(copySto);
					result.addTsscStory(copySto);
				
			}
			gameDao.save(result);
			result=gameDao.findByName(result.getName()).get(0);
		}
		return result;
	}
	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscGame updateGame(TsscGame game)
			throws EntityNotExistException, NotEnoughGroupsException, NotEnoughSprintsException, NullTopicException {
		// TODO Auto-generated method stub
		TsscGame result=null;
		if(game==null) {
			throw new NullTopicException();
		}else if(!gameDao.existsById(game.getId())) {
			throw new EntityNotExistException();
		}else if(game.getNGroups().intValue()<1) {
			throw new NotEnoughGroupsException();
		}else if(game.getNSprints().intValue()<1) {
			throw new NotEnoughSprintsException();
		}else {
			TsscTopic topic=game.getTsscTopic();
			if(topic!=null && !topicDao.existsById(topic.getId())) {
				throw new EntityNotExistException();
				
			}else {
				
				result=gameDao.findById(game.getId());
				result.setName(game.getName());
				result.setNGroups(game.getNGroups());
				result.setNSprints(game.getNSprints());
				result.setAdminPassword(game.getAdminPassword());
				result.setGuestPassword(game.getGuestPassword());
				result.setUserPassword(game.getUserPassword());
				result.setScheduledDate(game.getScheduledDate());
				result.setScheduledTime(game.getScheduledTime());
				result.setStartTime(game.getStartTime());
				gameDao.update(result);
				
			}
		}
		
		
		return result;
	}

	
	private TsscStory cloneStory(TsscStory originStory) {
		TsscStory clon= new TsscStory();
		clon.setAltDescripton(originStory.getAltDescripton());
		clon.setAltDescShown(originStory.getAltDescShown());
		clon.setBusinessValue(originStory.getBusinessValue());
		clon.setDescription(originStory.getDescription());
		clon.setInitialSprint(originStory.getInitialSprint());
		clon.setNumber(originStory.getNumber());
		clon.setPriority(originStory.getPriority());
		clon.setShortDescription(originStory.getShortDescription());
		return clon;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Iterable<TsscGame> findAll() {
		// TODO Auto-generated method stub
		return gameDao.findAll();
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Optional<TsscGame> findById(long id) {
		// TODO Auto-generated method stub
		return Optional.of( gameDao.findById(id));
	}
	
}
