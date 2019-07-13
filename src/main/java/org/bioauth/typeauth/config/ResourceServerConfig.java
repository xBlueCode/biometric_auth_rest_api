package org.bioauth.typeauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	//private final String SIGN_KEY = "bio";

	@Autowired
	private DefaultTokenServices tokenServices;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
	//	super.configure(resources);
		resources.tokenServices(tokenServices);
		resources.resourceId("res_1");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		//super.configure(http);

		http.authorizeRequests().antMatchers("/login", "/register").permitAll()
		.antMatchers("/help").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		//http
				//.authorizeRequests()
				//.antMatchers("/oauth/*").permitAll()
		;
	}
}
