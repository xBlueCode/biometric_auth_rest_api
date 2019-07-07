package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.domain.Client;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.bioauth.typeauth.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/client")
public class ClientController {

	private ClientServiceDb clientServiceDb;
	private UserServiceDb userServiceDb;

	@Autowired
	public ClientController(ClientServiceDb clientServiceDb, UserServiceDb userServiceDb) {
		this.clientServiceDb = clientServiceDb;
		this.userServiceDb = userServiceDb;
	}

	@GetMapping()
	public @ResponseBody String checkClient(@RequestParam("name") String name)
	{
		Optional<Client> optionalClient = clientServiceDb.findClientByName(name);
		if (optionalClient.isPresent())
			return optionalClient.get().toString();
		else
			return "Client Not found";
	}

	/*
	@PostMapping("/save")
	public @ResponseBody String saveClient(@PathParam("name") String name)
	{
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated())
			return "User not Authenticated !";
		String username = auth.getPrincipal().toString();
		Client client = new Client();
		client.setName(name);
		client.setTotalElapsedTime(10.0);
		client.setTotalPressTime(4.0);
		userServiceDb.addClientToUser(username, client);
		return "Client saved !";
	}
	*/

	@GetMapping("/save")
	public String showSaveClient(Model model)
	{
		//if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken))
		//	return "index";
		model.addAttribute("client", new Client());
		return "saveclient";
	}

	@PostMapping("/save")
	public String saveClient(@ModelAttribute("client") Client client, BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
			return "index";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!auth.isAuthenticated())
			return "User not Authenticated !";
		clientServiceDb.save(client);
		String username = ((UserDetails)auth.getPrincipal()).getUsername();
		userServiceDb.addClientToUser(username, client);
		return "dashboard";
	}
}
