package org.bioauth.typeauth.service;

import org.bioauth.typeauth.domain.Person;
import org.bioauth.typeauth.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonServiceDb implements PersonService {

	private PersonRepository personRepository;

	@Autowired
	public PersonServiceDb(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	@Override
	public Optional<Person> findPersonByName(String name) {
		return personRepository.findPersonByName(name);
	}

	@Override
	public void save(Person person) {
		personRepository.save(person);
	}
}
