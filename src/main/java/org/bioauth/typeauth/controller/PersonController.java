package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.config.ClientSecurityUtil;
import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.domain.Field;
import org.bioauth.typeauth.domain.Person;
import org.bioauth.typeauth.exception.ClientNotAuthenticatedException;
import org.bioauth.typeauth.exception.FieldNotFoundException;
import org.bioauth.typeauth.exception.PersonExistException;
import org.bioauth.typeauth.exception.PersonNotFoundException;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.bioauth.typeauth.service.PersonServiceDb;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

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
		Person person = getValidPersonByName(name);
		HashMap<String, Object> body = new HashMap<>();

		body.put("status", HttpStatus.FOUND);
		body.put("action", "found");
		body.put("person", person);
		return new ResponseEntity<>(body, HttpStatus.CREATED);
	}

	@PostMapping("/save")
	public ResponseEntity<?> savePerson(@RequestBody Person person)
	{
		Client authenticatedClient;
		HashMap<String, Object> body = new HashMap<>();

		authenticatedClient = getValidAuthenticatedClient();
		if (authenticatedClient.personExist(person.getName()) != null)
			throw new PersonExistException(person.getName());
		authenticatedClient.getPersons().add(person);
		clientServiceDb.update(authenticatedClient);
		person.setId(personServiceDb.findPersonByName(person.getName()).getId());

		body.put("status", HttpStatus.CREATED);
		body.put("action", "created");
		body.put("person", person);
		return new ResponseEntity<>(body, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<?> updatePerson(@RequestBody Person person)
	{
		Person oldPerson = getValidPersonByName(person.getName());
		HashMap<String, Object> body = new HashMap<>();

		oldPerson.updateFieldsDesktop((ArrayList<Field>) person.getFieldsDesktop());
		oldPerson.updateFieldsMobile((ArrayList<Field>) person.getFieldsMobile());
		personServiceDb.update(oldPerson);

		body.put("status", HttpStatus.OK);
		body.put("action", "updated");
		body.put("new", oldPerson);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deletePerson(@RequestParam("name") String name)
	{
		Person person = getValidPersonByName(name);
		HashMap<String, Object> body = new HashMap<>();
		Client authenticatedClient = clientSecurityUtil.getAuthenticatedClient();

		personServiceDb.delete(person);
		authenticatedClient.getPersons().remove(person);
		clientServiceDb.update(authenticatedClient);

		body.put("status", HttpStatus.OK);
		body.put("action", "deleted");
		body.put("person", person);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@GetMapping("/verify")
	public ResponseEntity<?> verify(@RequestBody Person newPerson)
	{
		Person person = getValidPersonByName(newPerson.getName());
		HashMap<String, Object> body = new HashMap<>();
		ArrayList<Object> scoresDesktop = new ArrayList<>();

		ArrayList<String> fieldnamesDesktop = (ArrayList<String>) person.getFieldsDesktop().stream()
				.map(Field::getName).collect(Collectors.toList());

		for (Field newField : newPerson.getFieldsDesktop())
		{
			if (!fieldnamesDesktop.contains(newField.getName()))
				throw new FieldNotFoundException(newField.getName());
			scoresDesktop.add(newField.getScore(person.getFieldsDesktop().get(fieldnamesDesktop.indexOf(newField.getName()))));
		}

		ArrayList<Object> scoresMobile = new ArrayList<>();
		ArrayList<String> fieldnamesMobile = (ArrayList<String>) person.getFieldsMobile().stream()
				.map(Field::getName).collect(Collectors.toList());

		for (Field newField : newPerson.getFieldsMobile())
		{
			if (!fieldnamesMobile.contains(newField.getName()))
				throw new FieldNotFoundException(newField.getName());
			scoresMobile.add(newField.getScore(person.getFieldsMobile().get(fieldnamesMobile.indexOf(newField.getName()))));
		}
		body.put("status", HttpStatus.OK);
		body.put("action", "verified");
		body.put("person", person);
		body.put("scores_desktop", scoresDesktop);
		body.put("scores_mobile", scoresMobile);
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	private Person getValidPersonByName(String name)
	{
		Client authenticatedClient;
		Person person;

		authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		if ((person = authenticatedClient.personExist(name)) == null)
			throw new PersonNotFoundException(name);
		return person;
	}

	private Client getValidAuthenticatedClient()
	{
		Client authenticatedClient = clientSecurityUtil.getAuthenticatedClient();
		if (authenticatedClient == null)
			throw new ClientNotAuthenticatedException("anonymous");
		return authenticatedClient;
	}
}
