package org.bioauth.typeauth.exception;

import lombok.Getter;

@Getter
public class PersonException extends RuntimeException{

	private String name;

	public PersonException(String name, String message) {
		super(message + " -> " + name);
		this.name = name;
	}
}
