package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscAdmin;

@Repository
public interface AdminRepository extends CrudRepository<TsscAdmin, Long>{

	TsscAdmin findByUser(String username);
	
}
