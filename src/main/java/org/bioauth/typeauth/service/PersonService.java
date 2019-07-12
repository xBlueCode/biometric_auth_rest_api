package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Person;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PersonService {

	Optional<Person> findPersonByName(String name);

	void save(Person person);
}
