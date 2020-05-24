package com.lievano.cc.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.lievano.cc.model.TsscGame;

@Repository
public interface GameRepository extends CrudRepository<TsscGame, Long> {

}
