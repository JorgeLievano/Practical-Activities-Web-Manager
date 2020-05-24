package com.lievano.cc.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lievano.cc.dao.TopicDao;
import com.lievano.cc.exceptions.DuplicatedEntityException;
import com.lievano.cc.exceptions.NotEnoughGroupsException;
import com.lievano.cc.exceptions.NotEnoughSprintsException;
import com.lievano.cc.exceptions.NullTopicException;
import com.lievano.cc.exceptions.EntityNotExistException;

import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.repository.TopicRepository;

@Service
public class TopicServiceImp implements TopicService {

	//private TopicRepository topicRepository;
	private TopicDao dao;
	
	@Autowired
	public  TopicServiceImp(TopicDao dao) {
		// TODO Auto-generated constructor stub
		this.dao=dao;
		
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscTopic saveTopic(TsscTopic topic) throws NotEnoughGroupsException, NotEnoughSprintsException,NullTopicException, DuplicatedEntityException {
		// TODO Auto-generated method stub
		TsscTopic result=null;
		if(topic ==null) {
			throw new NullTopicException();
		}else if(topic.getDefaultGroups()<1) {
			throw new NotEnoughGroupsException();
		}else if(topic.getDefaultSprints()<1) {
			throw new NotEnoughSprintsException();
		}else if(dao.existsById(topic.getId())){
			throw new DuplicatedEntityException("Topic");
		}else{
			dao.save(topic);
			result=dao.findByName(topic.getName()).get(0);
		}
		return result;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscTopic updateTopic(TsscTopic topic) throws NotEnoughGroupsException, NotEnoughSprintsException, NullTopicException, EntityNotExistException {
		// TODO Auto-generated method stub
		TsscTopic result=null;
		if(topic ==null) {
			throw new NullTopicException();
		}else if(topic.getDefaultGroups()<1) {
			throw new NotEnoughGroupsException();
		}else if(topic.getDefaultSprints()<1) {
			throw new NotEnoughSprintsException();
		}else {
			
			if(!dao.existsById(topic.getId())) {
				throw new EntityNotExistException();
			}else {
				TsscTopic ref=dao.findById(topic.getId());
				ref.setDefaultGroups(topic.getDefaultGroups());
				ref.setDefaultSprints(topic.getDefaultSprints());
				ref.setDescription(topic.getDescription());
				ref.setGroupPrefix(topic.getGroupPrefix());
				ref.setName(topic.getName());
				result=ref;
				
				dao.update(result);;
				
			}
		}
		return result;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Iterable<TsscTopic> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Optional<TsscTopic> findById(long id) {
		// TODO Auto-generated method stub
		return Optional.of(dao.findById(id));
	}

	
	
	
}
