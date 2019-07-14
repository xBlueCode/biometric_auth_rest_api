package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceDb implements ClientService {

	private ClientRepository clientRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public ClientServiceDb(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
		this.clientRepository = clientRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Client save(Client client) {
		client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
		return clientRepository.save(client);
	}

	@Override
	public void delete(Client client) {
		clientRepository.delete(client);
	}

	@Override
	public Client update(Client client) {
		return clientRepository.saveAndFlush(client);
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientRepository.findClientByClientId(clientId);
	}
}
