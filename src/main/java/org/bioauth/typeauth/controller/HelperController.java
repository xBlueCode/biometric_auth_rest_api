package org.bioauth.typeauth.controller;

import org.bioauth.typeauth.Helper;
import org.bioauth.typeauth.domain.*;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/help")
public class HelperController {

	//@Autowired
	//private Helper helper;

	private ClientServiceDb clientServiceDb;

	@Autowired
	public HelperController(ClientServiceDb clientServiceDb) {
		this.clientServiceDb = clientServiceDb;
	}

	@GetMapping
	@ResponseBody
	public String help()
	{
		Client client = new Client();
		client.setClientId("client_1");
		client.setClientSecret("cpass1");
		client.setAccessTokenValiditySeconds(250);
		client.getAuthGrantTypes().add(new AuthGrantType("client_credentials"));
		client.getGrantedAuthorities().add(new GrantedAuthorityClient("authority_1"));
		client.getScopes().add(new Scope("scope_1"));
		client.getResIds().add(new ResourceId("res_1"));
		//if (clientServiceDb.loadClientByClientId(client.getClientId()) == null)
		clientServiceDb.save(client);
		return "Created !";
	}
}
