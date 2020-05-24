package com.lievano.cc.exceptions;

public class DuplicatedEntityException extends Exception {

	
	public DuplicatedEntityException(String entityName) {
		super("Entity "+entityName+" had been instanciate with the same value Id");
	}
	
}
