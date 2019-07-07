package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.domain.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {


	void save(User user);
	//void delete(User user);
	void update(User user);
	Optional<User> findUserByUsername(String username);
	void addClientToUser(String username, Client client);
}
