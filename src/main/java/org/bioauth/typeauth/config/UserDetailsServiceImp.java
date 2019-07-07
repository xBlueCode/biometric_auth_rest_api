package org.bioauth.typeauth.config;

import org.bioauth.typeauth.domain.User;
import org.bioauth.typeauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

	private UserRepository userRepository;

	@Autowired
	public UserDetailsServiceImp(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> oUser = userRepository.findUserByUsername(username);
		if (!oUser.isPresent())
			throw new UsernameNotFoundException(username);
		else
			return new UserDetailsImp(oUser.get());

	}

}
