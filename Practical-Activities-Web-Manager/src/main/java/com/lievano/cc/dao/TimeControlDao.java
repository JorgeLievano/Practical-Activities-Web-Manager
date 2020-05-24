package com.lievano.cc.dao;

import java.util.List;

import com.lievano.cc.model.TsscTimecontrol;

public interface TimeControlDao {

	void save(TsscTimecontrol timeControl);
	void update(TsscTimecontrol timeControl);
	void delete(TsscTimecontrol timeControl);
	TsscTimecontrol findById(long id);
	List<TsscTimecontrol> findAll();
}
