package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscSprint;

@Repository
public interface SprintRepository extends CrudRepository<TsscSprint, Long> {

}
