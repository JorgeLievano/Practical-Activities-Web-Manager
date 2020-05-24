package com.lievano.cc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscTimecontrol;

@Repository
@Scope("singleton")
public class TimeControlDaoImp implements TimeControlDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(TsscTimecontrol timeControl) {
		entityManager.persist(timeControl);
	}

	@Override
	public void update(TsscTimecontrol timeControl) {
		entityManager.merge(timeControl);
	}

	@Override
	public void delete(TsscTimecontrol timeControl) {
		entityManager.remove(timeControl);
	}

	@Override
	public TsscTimecontrol findById(long id) {
		return entityManager.find(TsscTimecontrol.class, id);
	}

	@Override
	public List<TsscTimecontrol> findAll() {
		String s= "SELECT t FROM TsscTimeControl t";
		return entityManager.createQuery(s).getResultList();
	}
	
	
}
