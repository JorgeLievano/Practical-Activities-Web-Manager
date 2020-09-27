package com.lievano.cc.service;


import com.lievano.cc.model.TsscTimecontrol;

public interface TimecontrolService {
	
	TsscTimecontrol save(TsscTimecontrol tsscTimecontrol);
	TsscTimecontrol update(TsscTimecontrol tsscTimecontrol);
	void delete(long id);
	
	TsscTimecontrol findById(long id);
	Iterable<TsscTimecontrol> findAll();
	Iterable<TsscTimecontrol> findByGameId(long gameid);
	

}
