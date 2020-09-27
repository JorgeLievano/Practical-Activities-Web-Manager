package com.lievano.cc.delegate;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lievano.cc.model.TsscStory;

@Component
public class StoryDelegate {

	private RestTemplate rest= new RestTemplate();
	
	
	public Iterable<TsscStory> findAll(){
		List<TsscStory> stories;
		TsscStory[] response= rest.getForObject(DelegateContants.SERVER_URI+"stories", TsscStory[].class);
		stories= Arrays.asList(response);
		return stories;
	}
	
	public TsscStory findById(long id) {
		TsscStory story= rest.getForObject(DelegateContants.SERVER_URI+"stories/"+id, TsscStory.class);
		return story;
	}
	
	public Iterable<TsscStory> findByGameid(long gameid){
		List<TsscStory> stories;
		TsscStory[] response = rest.getForObject(DelegateContants.SERVER_URI+"stories/game/"+gameid, TsscStory[].class);
		stories=Arrays.asList(response);
		return stories;
	}
	
	public TsscStory save(TsscStory tsscStory) {
		ResponseEntity<TsscStory> response= rest.postForEntity(DelegateContants.SERVER_URI+"stories", tsscStory, TsscStory.class);
		return response.getBody();
	}
	
	public void update(TsscStory tsscStory) {
		rest.put(DelegateContants.SERVER_URI+"stories", tsscStory);
	}
	
}
