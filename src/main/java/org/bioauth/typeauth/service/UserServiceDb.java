package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Person;
import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

	@Override
	public void update(User user) {
		userRepository.saveAndFlush(user);
	}

	@Override
	public Optional<User> findUserByUsername(String username) {
		return userRepository.findUserByUsername(username);
	}

	/*
	@Override
	public void addClientToUser(String username, Person person) {

		Optional<User> opUser = userRepository.findUserByUsername(username);
		if (!opUser.isPresent())
			return;
		User user = opUser.get();
		user.getPeople().add(person);
		userRepository.saveAndFlush(user);
	}
	*/

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opUser = userRepository.findUserByUsername(username);
		if (!opUser.isPresent())
			throw new UsernameNotFoundException(username);
		else
			return opUser.get();
	}
}
