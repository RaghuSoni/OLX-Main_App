package com.olx.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserDetailsService userDetailsService;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception { // Overriding Authentication
		// Database authentication
		auth.userDetailsService(userDetailsService);

		/*
		 * auth.inMemoryAuthentication()
		 * .withUser("tom").password(getPasswordEncoder().encode("tom123")).roles(
		 * "USER") .and()
		 * .withUser("jerry").password(getPasswordEncoder().encode("jerry123")).roles(
		 * "ADMIN");
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		
		.authorizeRequests()
		.antMatchers("/user/logout")
		.hasAnyRole("USER", "ADMIN")
				// .antMatchers("/user").hasAnyRole("USER", "ADMIN")
				.antMatchers("/user/authenticate")
				.permitAll()
				.antMatchers("/user")
				.permitAll()
				.and()
				.formLogin();

	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager getAuthenticationManger() throws Exception {
		return super.authenticationManager();
	}
}
