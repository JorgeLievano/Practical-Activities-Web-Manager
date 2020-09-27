package com.lievano.cc.delegate;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lievano.cc.model.TsscTopic;

@Component
public class TopicDelagate {

	private RestTemplate rest = new RestTemplate();
	
	
	public Iterable<TsscTopic> findAll() {
		List<TsscTopic> topics;
		TsscTopic[] response= rest.getForObject(DelegateContants.SERVER_URI + "topics", TsscTopic[].class);
		topics=Arrays.asList(response);
		return topics;
	}
	
	
	public TsscTopic findById(long id) {
		TsscTopic topic = rest.getForObject(DelegateContants.SERVER_URI+"topics/"+id, TsscTopic.class);
		return topic;
	}
	
	
	public void add(TsscTopic tsscTopic) {
		
			ResponseEntity<TsscTopic> response=rest.postForEntity(DelegateContants.SERVER_URI+"topics", tsscTopic, TsscTopic.class);
			if(response.getStatusCode() ==HttpStatus.OK) {
				System.out.println("add topic ok");
			}else {
				System.out.println("add topic fail");
				System.out.println(response.getStatusCode().toString());
			}
		
	}
	
	public void update(TsscTopic tsscTopic) {
		rest.put(DelegateContants.SERVER_URI + "topics", tsscTopic);
	
	}
}
