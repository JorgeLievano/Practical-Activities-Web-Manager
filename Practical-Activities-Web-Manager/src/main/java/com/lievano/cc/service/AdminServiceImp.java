package com.lievano.cc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lievano.cc.dao.AdminDao;
import com.lievano.cc.model.TsscAdmin;
import com.lievano.cc.repository.AdminRepository;

@Service
public class AdminServiceImp implements AdminService {

	//private AdminRepository adminRepository;
	private AdminDao dao;
	
	@Autowired
	public  AdminServiceImp(AdminDao dao) {
		// TODO Auto-generated constructor stub
		this.dao=dao;
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public TsscAdmin saveAdmin(TsscAdmin admin) {
		// TODO Auto-generated method stub
		dao.save(admin);
		return dao.findByUser(admin.getUser()).get(0);
	}

	
	
}
