package org.bioauth.typeauth.exception;

import lombok.Getter;

@Getter
public class ClientNotAuthenticatedException extends RuntimeException{

	private String clientId;

	public ClientNotAuthenticatedException(String clientId) {
		super("Client not authenticated -> "+ clientId);
		this.clientId = clientId;
	}
}
