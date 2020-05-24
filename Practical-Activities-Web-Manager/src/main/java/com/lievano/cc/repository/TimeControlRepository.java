package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscTimecontrol;

@Repository
public interface TimeControlRepository extends CrudRepository<TsscTimecontrol, Long>{

}
