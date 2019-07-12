package org.bioauth.typeauth.Utility;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.bioauth.typeauth.domain.*;
import org.bioauth.typeauth.repository.*;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitializerDb implements CommandLineRunner {

	//@Autowired
	//private ClientServiceDb clientServiceDb;

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private AuthGrantTypeRepository authGrantTypeRepository;
	@Autowired
	private GrantedAuthorityClientRepository grantedAuthorityClientRepository;
	@Autowired
	private ResourceIdRepository resourceIdRepository;
	@Autowired
	private ScopeRepository scopeRepository;

	@Override
	public void run(String... args) throws Exception {

		Log logger = LogFactory.getLog(InitializerDb.class);

		Client client1;

		AuthGrantType authGrantType;// = new AuthGrantType("client_credentials");
		GrantedAuthorityClient grantedAuthorityClient;// = new GrantedAuthorityClient("grant_auth_1");
		ResourceId resourceId;// = new ResourceId("Res_1");
		Scope scope;// = new Scope("scope_1");

		if ((client1 = (Client) clientRepository.findClientByClientId("client1")) != null)
			return;
		//client1 = clientRepository.findClientByClientId("client1");

		logger.info("Creating Client 1 !");

		if ((authGrantType = authGrantTypeRepository.findAuthGrantTypeByType("client_credentials")) == null)
		{
			authGrantType = new AuthGrantType("client_credentials");
			authGrantType = authGrantTypeRepository.save(authGrantType);
		}

		if ((grantedAuthorityClient
				= grantedAuthorityClientRepository.findGrantedAuthorityClientByAuthority("grant_auth_1")) == null)
		{
			grantedAuthorityClient = new GrantedAuthorityClient("grant_auth_1");
			grantedAuthorityClient =  grantedAuthorityClientRepository.save(grantedAuthorityClient);
		}

		if ((resourceId = resourceIdRepository.findResourceIdByResourceId("res_1")) == null)
		{
			resourceId = new ResourceId("res_1");
			resourceId = resourceIdRepository.save(resourceId);
		}

		if ((scope = scopeRepository.findScopeByScope("scope_1")) == null)
		{
			scope = new Scope("scope_1");
			scope = scopeRepository.save(scope);
		}

		client1.setClientId("client1");
		client1.setClientSecret((new BCryptPasswordEncoder()).encode("cpass1"));
		client1.setAccessTokenValiditySeconds(250);
		//client1 = clientRepository.findClientByClientId("client_1");

		client1.getScopes().add(scope);
		client1.getAuthGrantTypes().add(authGrantType);
		client1.getGrantedAuthorities().add(grantedAuthorityClient);
		client1.getResIds().add(resourceId);
		clientRepository.saveAndFlush(client1);
	}
}
