package com.lievano.cc.delegate;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.lievano.cc.model.TsscGame;

@Component
public class GameDelegate {
	
	private RestTemplate rest= new RestTemplate();
	
	
	public Iterable<TsscGame> findAll(){
		List<TsscGame> games;
		TsscGame[] response= rest.getForObject(DelegateContants.SERVER_URI+"games", TsscGame[].class);
		games=Arrays.asList(response);
		return games;
		
	}
	
	public TsscGame findById(long id) {
		TsscGame game = rest.getForObject(DelegateContants.SERVER_URI+"games/"+id, TsscGame.class);
		return game;
	}
	
	
	public TsscGame save(TsscGame tsscGame) {
		
		 ResponseEntity<TsscGame> response= rest.postForEntity(DelegateContants.SERVER_URI+"games", tsscGame, TsscGame.class);
		 if(response.getStatusCode() ==HttpStatus.OK) {
				System.out.println("add game ok");
			}else {
				System.out.println("add game fail");
				System.out.println(response.getStatusCode().toString());
			}
		return response.getBody();
	}
	
	
	public void update(TsscGame tsscGame) {
			rest.put(DelegateContants.SERVER_URI+"games", tsscGame);
	}
}
