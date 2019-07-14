package org.bioauth.typeauth.exception;

import lombok.Getter;

@Getter
public class PersonNotFoundException extends PersonException{

	public PersonNotFoundException(String name) {
		super(name, "Person not found");
	}
}
