package org.pk.oauth2.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@Order(1)
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
		    .requestMatchers()
		    	.antMatchers("/login", "/oauth/authorize", "/oauth/confirm_access")
		    .and()	
		    	.formLogin().permitAll()
		    .and()
		    	.httpBasic()
		    .and()
		    	.authorizeRequests()
		    	.antMatchers("/webjars/**").permitAll()
			    .anyRequest().authenticated();
	}
}