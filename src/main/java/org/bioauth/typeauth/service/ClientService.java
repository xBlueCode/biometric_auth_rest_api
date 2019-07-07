package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ClientService {

	Optional<Client> findClientByName(String name);

	void save(Client client);
}
