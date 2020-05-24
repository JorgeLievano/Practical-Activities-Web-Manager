package com.lievano.cc.dao;

import java.util.List;

import com.lievano.cc.model.TsscAdmin;

public interface AdminDao {

	void save(TsscAdmin admin);
	void update(TsscAdmin admin);
	void delete(TsscAdmin admin);
	TsscAdmin findById(long id);
	List<TsscAdmin> findAll();
	List<TsscAdmin> findByUser(String user);
	
}
