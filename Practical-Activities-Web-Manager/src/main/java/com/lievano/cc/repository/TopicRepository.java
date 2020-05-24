package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscTopic;

@Repository
public interface TopicRepository extends CrudRepository<TsscTopic, Long> {

}
