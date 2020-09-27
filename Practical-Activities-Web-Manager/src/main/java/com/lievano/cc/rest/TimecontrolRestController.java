package com.lievano.cc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lievano.cc.model.TsscTimecontrol;
import com.lievano.cc.service.TimecontrolService;

@RestController
public class TimecontrolRestController {

	private TimecontrolService timeService;
	
	@Autowired
	public TimecontrolRestController(TimecontrolService timeService) {
		this.timeService=timeService;
	}
	
	
	@GetMapping("/api/timecontrols")
	public Iterable<TsscTimecontrol> findAll(){
		return timeService.findAll();
	}
	
	@GetMapping("/api/timecontrols/{id}")
	public TsscTimecontrol findById(@PathVariable("id")long id) {
		return timeService.findById(id);
	}
	
	@PostMapping("/api/timecontrols")
	public TsscTimecontrol save(@RequestBody TsscTimecontrol tsscTimecontrol) {
		return timeService.save(tsscTimecontrol);
	}
	
	@PutMapping("/api/timecontrols")
	public TsscTimecontrol update(@RequestBody TsscTimecontrol tsscTimecontrol) {
		return timeService.update(tsscTimecontrol);
	}
	
	@DeleteMapping("/api/timecontrols/{id}")
	public void delete(@PathVariable("id")long id) {
		timeService.delete(id);
	}
}
