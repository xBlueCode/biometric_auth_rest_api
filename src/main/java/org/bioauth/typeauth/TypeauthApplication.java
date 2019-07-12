package org.bioauth.typeauth;

import org.bioauth.typeauth.domain.*;
import org.bioauth.typeauth.service.ClientServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import javax.mail.search.SearchTerm;

@SpringBootApplication
//@EnableAuthorizationServer
public class TypeauthApplication {
	public static void main(String[] args)
	{
		SpringApplication.run(TypeauthApplication.class, args);
	}
}
