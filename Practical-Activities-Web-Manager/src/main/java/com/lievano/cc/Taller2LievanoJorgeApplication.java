package com.lievano.cc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import com.lievano.cc.model.TsscAdmin;
import com.lievano.cc.model.TsscTopic;
import com.lievano.cc.repository.AdminRepository;
import com.lievano.cc.service.AdminService;
import com.lievano.cc.service.AdminServiceImp;
import com.lievano.cc.service.TopicService;
import com.lievano.cc.service.TopicServiceImp;

@SpringBootApplication
public class Taller2LievanoJorgeApplication {

	@Bean
	public Java8TimeDialect java8TimeDialect() {
		return new Java8TimeDialect();
	}
	
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context= SpringApplication.run(Taller2LievanoJorgeApplication.class, args);
		
		AdminService adminService= context.getBean(AdminServiceImp.class);
		TopicService topicService=context.getBean(TopicServiceImp.class);
		
		TsscAdmin superAdmin= new TsscAdmin();
		superAdmin.setUser("superadmin");
		superAdmin.setPassword("{noop}superadmin");
		superAdmin.setSuperAdmin("superadmin");
		adminService.saveAdmin(superAdmin);
		
		TsscAdmin admin=new TsscAdmin();
		admin.setUser("admin");
		admin.setPassword("{noop}admin");
		admin.setSuperAdmin("admin");
		adminService.saveAdmin(admin);
		
		TsscTopic topic=new TsscTopic();
		topic.setDefaultGroups(4);
		topic.setDefaultSprints(4);
		topic.setDescription("Topic de prueba");
		topic.setGroupPrefix("group");
		topic.setName("Trial");
		try {
			topicService.saveTopic(topic);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
