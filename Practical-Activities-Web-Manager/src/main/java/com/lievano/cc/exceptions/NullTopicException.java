package com.lievano.cc.exceptions;

public class NullTopicException extends Exception{

	public NullTopicException() {
		super("Topic values can´t be null");
	}
}
