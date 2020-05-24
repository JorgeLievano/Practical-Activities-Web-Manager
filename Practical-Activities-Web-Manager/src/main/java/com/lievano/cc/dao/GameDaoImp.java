package com.lievano.cc.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscGame;

@Repository
@Scope("singleton")
public class GameDaoImp implements GameDao  {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(TsscGame game) {
		entityManager.persist(game);
	}

	@Override
	public void update(TsscGame game) {
		entityManager.merge(game);
	}

	@Override
	public List<TsscGame> findByName(String name) {
		String s= "SELECT g FROM TsscGame g WHERE g.name = '"+name+"'";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List<TsscGame> findByDescription(String description) {
		String s= "SELECT g FROM TsscGame g WHERE g.description = '"+description+"'";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List<TsscGame> findByTopicId(long topicId) {
		String s= "SELECT g FROM TsscGame g WHERE g.tsscTopic.id = '"+topicId+"'";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List<TsscGame> findByScheduledDateBetween(LocalDate startScheduledDate, LocalDate endScheduledDate) {
		String s= "SELECT g FROM TsscGame g WHERE g.scheduledDate BETWEEN '"+startScheduledDate+"' AND '"+endScheduledDate+"'";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List<TsscGame> findByScheduledDateAndScheduledTimeBetween(LocalDate scheduledDate,
			LocalTime startScheduleTime, LocalTime endtScheduleTime) {
		String s= "SELECT g FROM TsscGame g WHERE g.scheduledDate = '"+scheduledDate+"' "
				+ "AND  g.scheduledTime BETWEEN '"+startScheduleTime+"' AND '"+endtScheduleTime+"'";
		return entityManager.createQuery(s).getResultList();
	}

	//punto 2.b del taller
	@Override
	public List<TsscGame> findByScheduleDateWithLessStoriesOrNoTimeControls(LocalDate scheduledDate, int maxStories) {
		String s= "SELECT g FROM TsscGame g WHERE g.scheduledDate = '"+scheduledDate+"' AND"
				+ "(SIZE(g.tsscStories) < '"+ maxStories+"' OR SIZE(g.tsscTimecontrols) = 0)";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public TsscGame findById(long id) {
		return entityManager.find(TsscGame.class, id);
	}

	@Override
	public List<TsscGame> findAll() {
		String s= "SELECT t FROM TsscGame t";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public boolean existsById(long id) {
		return findById(id)!=null?true:false;
	}
}
