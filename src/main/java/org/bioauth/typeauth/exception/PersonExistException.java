package org.bioauth.typeauth.exception;

public class PersonExistException extends PersonException{

	public PersonExistException(String name) {
		super(name, "Person already exist");
	}
}
