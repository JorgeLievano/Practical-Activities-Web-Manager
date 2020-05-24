package com.lievano.cc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscAdmin;

@Repository
@Scope("singleton")
public class AdminDaoImp implements AdminDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(TsscAdmin admin) {
		entityManager.persist(admin);
	}

	@Override
	public void update(TsscAdmin admin) {
		entityManager.merge(admin);
	}

	@Override
	public void delete(TsscAdmin admin) {
		entityManager.remove(admin);
	}

	@Override
	public TsscAdmin findById(long id) {
		return entityManager.find(TsscAdmin.class, id);
	}

	@Override
	public List<TsscAdmin> findAll() {
		String s= "SELECT a FROM TsscAdmin a";
		return entityManager.createQuery(s).getResultList();
	}

	@Override
	public List< TsscAdmin> findByUser(String user) {
		String s= "SELECT a FROM TsscAdmin a WHERE a.user = '"+user+"'";
		return entityManager.createQuery(s).getResultList();
	}

	
	
	
}
