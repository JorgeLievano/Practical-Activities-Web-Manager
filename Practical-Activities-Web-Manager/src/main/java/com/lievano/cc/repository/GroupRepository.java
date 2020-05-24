package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscGroup;

@Repository
public interface GroupRepository extends CrudRepository<TsscGroup, Long>  {

}
