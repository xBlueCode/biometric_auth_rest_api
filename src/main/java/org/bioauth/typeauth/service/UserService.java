package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Person;
import org.bioauth.typeauth.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {


	void save(User user);
	//void delete(User user);
	void update(User user);
	Optional<User> findUserByUsername(String username);
	void addClientToUser(String username, Person person);
}
