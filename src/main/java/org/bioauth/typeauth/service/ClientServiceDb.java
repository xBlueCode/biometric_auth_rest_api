package org.bioauth.typeauth.service;

import lombok.NoArgsConstructor;
import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientServiceDb implements ClientService{

	private ClientRepository clientRepository;

	@Autowired
	public ClientServiceDb(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public Optional<Client> findClientByName(String name) {
		return clientRepository.findClientByName(name);
	}

	@Override
	public void save(Client client) {
		clientRepository.save(client);
	}
}
