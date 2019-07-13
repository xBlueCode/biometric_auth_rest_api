package org.bioauth.typeauth.config;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtilities {

	private ClientServiceDb clientServiceDb;

	@Autowired
	public SecurityUtilities(ClientServiceDb clientServiceDb) {
		this.clientServiceDb = clientServiceDb;
	}

	public Client getAuthenticatedClient()
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated())
			return null;
		Client client = (Client) clientServiceDb.loadClientByClientId(auth.getName());
		return client;
	}
}
