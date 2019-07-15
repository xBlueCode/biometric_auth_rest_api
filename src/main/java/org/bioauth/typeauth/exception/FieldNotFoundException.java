package org.bioauth.typeauth.exception;

import lombok.Getter;

@Getter
public class FieldNotFoundException extends RuntimeException{

	private String name;

	public FieldNotFoundException(String name) {
		super("Field not found -> " + name);
		this.name = name;
	}
}
