package com.lievano.cc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lievano.cc.model.TsscAdmin;
import com.lievano.cc.service.AdminService;

@RestController
public class AdminRestController {

	private AdminService adminService;
	
	@Autowired
	public AdminRestController(AdminService adminService) {
		this.adminService=adminService;
	}
	
	
	
	
	
}
