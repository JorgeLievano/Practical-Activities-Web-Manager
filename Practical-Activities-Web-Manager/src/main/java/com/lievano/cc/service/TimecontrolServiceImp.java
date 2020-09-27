package com.lievano.cc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lievano.cc.dao.TimeControlDao;
import com.lievano.cc.model.TsscTimecontrol;

@Service
public class TimecontrolServiceImp implements TimecontrolService {

	private TimeControlDao timeDao;
	
	@Autowired
	public TimecontrolServiceImp(TimeControlDao timeDao) {
		this.timeDao= timeDao;
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscTimecontrol save(TsscTimecontrol tsscTimecontrol) {
		timeDao.save(tsscTimecontrol);
		return tsscTimecontrol;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscTimecontrol update(TsscTimecontrol tsscTimecontrol) {
		timeDao.update(tsscTimecontrol);
		return tsscTimecontrol;
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public void delete(long id) {
		TsscTimecontrol tc=timeDao.findById(id);
		timeDao.delete(tc);
				
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public TsscTimecontrol findById(long id) {
		return timeDao.findById(id);
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Iterable<TsscTimecontrol> findAll() {
		return timeDao.findAll();
	}

	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@Override
	public Iterable<TsscTimecontrol> findByGameId(long gameid) {
		return timeDao.findByGameId(gameid);
	}

}
