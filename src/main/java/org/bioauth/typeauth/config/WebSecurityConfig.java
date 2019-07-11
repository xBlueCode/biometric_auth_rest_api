package org.bioauth.typeauth.config;

import org.bioauth.typeauth.service.UserServiceDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.security.AuthProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserServiceDb userDetailsService;

	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	DaoAuthenticationProvider daoAuthenticationProvider()
	{
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthenticationProvider;
	}

	@Bean
	SimpleUrlAuthenticationSuccessHandler successHandler()
	{
		SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
		successHandler.setDefaultTargetUrl("/dashboard");
		return successHandler;
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//super.configure(auth);
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//super.configure(http);
		//http
				//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				//.and()
				//.authorizeRequests()
				//.anyRequest().permitAll();
				//.antMatchers("/register", "/login").not().authenticated()
				//.antMatchers("/register").anonymous()
				//.antMatchers("/register").permitAll()
				//.antMatchers("/api/**").permitAll()
				//.anyRequest().authenticated()
				//.and()
				//.authorizeRequests().anyRequest().authenticated()
				//.and()
				//.formLogin()
				//.defaultSuccessUrl("/dashboard", true)
				//.successHandler(successHandler()
				//.antMatchers("/api").fullyAuthenticated();
		;
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//super.configure(web);
		//web.ignoring()
		//		.antMatchers("/**");
	}
}
