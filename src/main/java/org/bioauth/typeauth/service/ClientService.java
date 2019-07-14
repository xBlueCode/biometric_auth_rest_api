package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface ClientService extends ClientDetailsService {

	Client save(Client client);
	void delete(Client client);
	Client update(Client client);
}
