package com.lievano.cc.delegate;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lievano.cc.model.TsscTimecontrol;

@Component
public class TimecontrolDelegate {

	private RestTemplate rest= new RestTemplate();
	
	
	
	public Iterable<TsscTimecontrol> findall(){
		List<TsscTimecontrol> list;
		TsscTimecontrol[] response= rest.getForObject(DelegateContants.SERVER_URI+"timecontrols", TsscTimecontrol[].class);
		list=Arrays.asList(response);
		return list;
	}
	
	public TsscTimecontrol findById(long id) {
		TsscTimecontrol ti= rest.getForObject(DelegateContants.SERVER_URI+"timecontrols/"+id, TsscTimecontrol.class);
		return ti;
	}
	
	public TsscTimecontrol save(TsscTimecontrol tsscTimecontrol) {
		ResponseEntity<TsscTimecontrol> ti=rest.postForEntity(DelegateContants.SERVER_URI+"timecontrols", tsscTimecontrol, TsscTimecontrol.class);
		return ti.getBody();
	}
	
	public void update(TsscTimecontrol tsscTimecontrol) {
		rest.put(DelegateContants.SERVER_URI+"timecontrols", tsscTimecontrol);
	}
	
	public void delete(long id) {
		rest.delete(DelegateContants.SERVER_URI+"timecontrols/"+id);
	}
	
}
