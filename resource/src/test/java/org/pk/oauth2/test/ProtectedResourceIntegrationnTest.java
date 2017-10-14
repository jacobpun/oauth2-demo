package org.pk.oauth2.test;

import java.util.Arrays;




import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProtectedResourceIntegrationnTest {

	private static final String LOCAL_HOST = "http://localhost:";
	@LocalServerPort
	private int port;

	@Test
	public void testPasswordGrantType() {
		ResourceOwnerPasswordResourceDetails resource = new ResourceOwnerPasswordResourceDetails();
		resource.setUsername("pk");
		resource.setPassword("pkpassword");
		resource.setAccessTokenUri(createUrl("/oauth/token", 8081));
		resource.setClientId("myclient");
		resource.setClientSecret("secret");
		resource.setGrantType("password");
		resource.setScope(Arrays.asList("READ"));
		OAuth2RestTemplate oauthTemplate = new OAuth2RestTemplate(resource,
				new DefaultOAuth2ClientContext());
		
		ResponseEntity<String> response = 
			      oauthTemplate.getForEntity(createUrl("/protected/patients"), String.class);
		assertThat(response.getStatusCode(), equalTo((HttpStatus.OK)));
	}

	
	@Test
	public void testAuthorizationCodeGrantType() {
		AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
/*		resource.setUsername("pk");
		resource.setPassword("pkpassword");*/
		resource.setUserAuthorizationUri(createUrl("/oauth/authorize", 8081));
		resource.setAccessTokenUri(createUrl("/oauth/token", 8081));
		resource.setPreEstablishedRedirectUri(createUrl("/client/code", 8080));
		resource.setClientId("myclient");
		resource.setClientSecret("secret");
		resource.setGrantType("authorization_code");
		resource.setScope(Arrays.asList("READ"));
		OAuth2RestTemplate oauthTemplate = new OAuth2RestTemplate(resource,
				new DefaultOAuth2ClientContext());
		
		ResponseEntity<String> response = 
			      oauthTemplate.getForEntity(createUrl("/protected/patients"), String.class);
		assertThat(response.getStatusCode(), equalTo((HttpStatus.OK)));
	}
	
	private String createUrl(String uri) {
		return LOCAL_HOST + port + uri;
	}


	private String createUrl(String uri, int port) {
		return LOCAL_HOST + port + uri;
	}
}