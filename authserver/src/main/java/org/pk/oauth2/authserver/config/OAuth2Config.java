package org.pk.oauth2.authserver.config;

import org.pk.oauth2.authserver.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

	//@Autowired
	//private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDao userDao;

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.userDetailsService(userDao::findByName);
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
			.withClient("myclient")
			.secret("secret")
			.authorizedGrantTypes("authorization_code", "refresh_token", "password")
			.scopes("READ");
	}
}