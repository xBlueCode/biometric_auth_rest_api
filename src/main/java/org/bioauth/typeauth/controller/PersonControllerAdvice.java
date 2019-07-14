package org.bioauth.typeauth.controller;

import javassist.NotFoundException;
import org.bioauth.typeauth.exception.ClientNotAuthenticatedException;
import org.bioauth.typeauth.exception.PersonExistException;
import org.bioauth.typeauth.exception.PersonNotFoundException;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;


@ControllerAdvice
@RequestMapping(produces = "application/vnd.error+json")
public class PersonControllerAdvice {

	@ExceptionHandler(PersonNotFoundException.class)
	public ResponseEntity<?> handlePersonNotFoundException(final PersonNotFoundException excep)
	{
		return error(excep, HttpStatus.NOT_FOUND, excep.getName());
	}

	@ExceptionHandler(PersonExistException.class)
	public ResponseEntity<?> handlePersonExistException(final PersonExistException excep)
	{
		return error(excep, HttpStatus.FORBIDDEN, excep.getName());
	}

	@ExceptionHandler(ClientNotAuthenticatedException.class)
	public ResponseEntity<?> handleClientNotAuthenticatedException(final ClientNotAuthenticatedException excep)
	{
		return error(excep, HttpStatus.UNAUTHORIZED, excep.getClientId());
	}

	private ResponseEntity<?> error
			(final Exception excep, final HttpStatus httpStatus, final String logRef)
	{
		HashMap<String, Object> body = new HashMap<>();
		body.put("status", httpStatus);
		body.put("error", new VndErrors.VndError(logRef, excep.getMessage()));
		return new ResponseEntity<>(body, httpStatus);
	}
}
