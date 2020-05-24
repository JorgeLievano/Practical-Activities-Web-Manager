package com.lievano.cc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscStory;

@Repository
@Scope("singleton")
public class StoryDaoImp implements StoryDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(TsscStory story) {
		entityManager.persist(story);
	}

	@Override
	public void update(TsscStory story) {
		entityManager.merge(story);
	}

	@Override
	public void delete(TsscStory story) {
		entityManager.remove(story);
	}

	@Override
	public TsscStory findById(long id) {
		return entityManager.find(TsscStory.class, id);
	}

	@Override
	public List<TsscStory> findAll() {
		String s= "SELECT t FROM TsscStory t";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public boolean existsById(long id) {
		return findById(id)!=null?true:false;
	}

	@Override
	public List<TsscStory> findByGameId(long gameId) {
		String s= "SELECT t FROM TsscStory t WHERE t.tsscGame.id = '"+gameId+"'";
		return entityManager.createQuery(s).getResultList();
	}
	
	
}
