package org.bioauth.typeauth.controller;

import javassist.NotFoundException;
import org.bioauth.typeauth.config.ClientSecurityUtil;
import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.domain.Person;
import org.bioauth.typeauth.exception.ClientNotAuthenticatedException;
import org.bioauth.typeauth.exception.PersonExistException;
import org.bioauth.typeauth.exception.PersonNotFoundException;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.bioauth.typeauth.service.PersonServiceDb;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@Controller
@RequestMapping("/api")
public class PersonController {

	private PersonServiceDb personServiceDb;
	private ClientServiceDb clientServiceDb;

	private ClientSecurityUtil clientSecurityUtil;

	public PersonController(PersonServiceDb personServiceDb, ClientServiceDb clientServiceDb, ClientSecurityUtil clientSecurityUtil) {
		this.personServiceDb = personServiceDb;
		this.clientServiceDb = clientServiceDb;
		this.clientSecurityUtil = clientSecurityUtil;
	}

	@GetMapping("/check")
	public ResponseEntity<?> checkPerson(@RequestParam("name") String name)
	{
		Client authenticatedClient;
		Person person;
		HashMap<String, Object> body = new HashMap<>();

		authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		if ((person = authenticatedClient.personExist(name)) == null)
			throw new PersonNotFoundException(name);

		body.put("status", HttpStatus.FOUND);
		body.put("person", person);
		return new ResponseEntity<>(body, HttpStatus.CREATED);
	}

	@PostMapping("/save")
	public ResponseEntity<?> savePerson(@RequestBody Person person)
	{
		Client authenticatedClient;
		HashMap<String, Object> body = new HashMap<>();

		authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		if (authenticatedClient.personExist(person.getName()) != null)
			throw new PersonExistException(person.getName());
		authenticatedClient.getPersons().add(person);
		clientServiceDb.update(authenticatedClient);
		person.setId(personServiceDb.findPersonByName(person.getName()).getId());

		body.put("status", HttpStatus.CREATED);
		body.put("person", person);
		return new ResponseEntity<>(body, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updatePerson(@RequestBody Person person)
	{
		Client authenticatedClient;
		Person oldPerson;
		HashMap<String, Object> body = new HashMap<>();

		authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		if ((oldPerson = authenticatedClient.personExist(person.getName())) == null)
			throw  new PersonNotFoundException(person.getName());

		person.setId(oldPerson.getId());
		oldPerson = oldPerson.copy();
		personServiceDb.update(person);

		body.put("status", HttpStatus.OK);
		body.put("old", oldPerson);
		body.put("new", person);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deletePerson(@RequestParam("name") String name)
	{
		Client authenticatedClient;
		Person person;
		HashMap<String, Object> body = new HashMap<>();

		authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		if ((person = authenticatedClient.personExist(name)) == null)
			throw new PersonNotFoundException(name);

		authenticatedClient.getPersons().remove(person);
		personServiceDb.delete(person);
		clientServiceDb.update(authenticatedClient);

		body.put("status", HttpStatus.OK);
		body.put("action", "deleted");
		body.put("person", person);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@GetMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody Person newPerson)
	{
		Client authenticatedClient;
		Person person;
		HashMap<String, Object> body = new HashMap<>();

		authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		if ((person = authenticatedClient.personExist(newPerson.getName())) == null)
			throw new PersonNotFoundException(newPerson.getName());

		Double score1 = 100
				* Math.min(person.getTotalElapsedTime(), newPerson.getTotalElapsedTime())
				/ Math.max(person.getTotalElapsedTime(), newPerson.getTotalElapsedTime());
		Double score2 = 100
				* Math.min(person.getTotalPressTime(), newPerson.getTotalPressTime())
				/ Math.max(person.getTotalPressTime(), newPerson.getTotalPressTime());
		body.put("status", HttpStatus.OK);
		body.put("person", person);
		body.put("score_tet", score1.intValue());
		body.put("score_tpt", score2.intValue());

		return new ResponseEntity<>(body, HttpStatus.OK);
	}
}
