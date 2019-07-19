package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Person;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface PersonService {

	Person findPersonByName(String name);

	void save(Person person);
	void delete(Person person);
	void update(Person person);
	List<Person> findAll();
}
