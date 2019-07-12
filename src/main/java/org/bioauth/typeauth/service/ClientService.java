package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;

public interface ClientService extends ClientDetailsService {

	void save(Client client);
	void delete(Client client);
	void update(Client client);
}
