package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceDb implements UserService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceDb(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void save(User user)
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
/*
	@Override
	public void delete(User user) {
		repoUser.delete(user);
	}
*/
	@Override
	public void update(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	@Override
	public void addClientToUser(String username, Client client) {

		Optional<User> opUser = userRepository.findUserByUsername(username);
		if (!opUser.isPresent())
			return;
		User user = opUser.get();
		user.getClients().add(client);
		userRepository.saveAndFlush(user);
	}
}
