package com.lievano.cc.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscTopic;

@Repository
@Scope("singleton")
public class TopicDaoImpl implements TopicDao{

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public List<TsscTopic> findByName(String name) {
		String s= "SELECT t FROM TsscTopic t WHERE t.name = '"+name+"'";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List<TsscTopic> findByDescription(String description) {
		String s= "SELECT t FROM TsscTopic t WHERE t.description = '"+description+"'";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List<TsscTopic> findAll() {
		String s= "SELECT t FROM TsscTopic t";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public TsscTopic findById(long id) {
		return entityManager.find(TsscTopic.class, id);
	}
	
	@Override
	public void save(TsscTopic tsscTopic) {
		entityManager.persist(tsscTopic);
	}

	@Override
	public void update(TsscTopic tsscTopic) {
		entityManager.merge(tsscTopic);
	}

	//punto 2.a del taller
	@Override
	public List<Pair<TsscTopic, Integer>> findTopicsByDateWithGameCount(LocalDate date) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public boolean existsById(long Id) {
		return findById(Id)!=null? true:false;
	}

	

	
}
