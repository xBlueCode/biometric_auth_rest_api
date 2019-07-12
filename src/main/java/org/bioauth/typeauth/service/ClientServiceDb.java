package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceDb implements ClientService{

	private ClientRepository clientRepository;

	@Autowired
	public ClientServiceDb(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public void save(Client client) {
		clientRepository.save(client);
	}

	@Override
	public void delete(Client client) {
		clientRepository.delete(client);
	}

	@Override
	public void update(Client client) {
		clientRepository.saveAndFlush(client);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return null;
	}
}
