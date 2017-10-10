package org.pk.oauth2.authserver.config;

import org.pk.oauth2.authserver.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthenticationManagerConfiguration extends
		GlobalAuthenticationConfigurerAdapter {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void init(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDao::findByName);
	}
}