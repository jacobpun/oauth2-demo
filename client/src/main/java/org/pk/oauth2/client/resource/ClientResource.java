package org.pk.oauth2.client.resource;

import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.pk.oauth2.domain.Patient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/client")
public class ClientResource {

	private String redirectUri = "http://localhost:8080/client/code";
	private String authServerUri = "http://localhost:8081/oauth";
	private String resourceUri = "http://localhost:8082/protected/patients";

	private String authCode = null;
	private String token = null;

	@GetMapping("/authCode")
	public String redirect() {
		return "redirect:"
				+ authServerUri
				+ "/authorize?scope=READ&scope=WRITE&response_type=code&client_id=myclient&redirect_uri="
				+ redirectUri;
	}

	@GetMapping("/code")
	@ResponseBody
	public String authCode(@RequestParam("code") String authCode) {
		this.authCode = authCode;
		return authCode;
	}

	@GetMapping("/token")
	@ResponseBody
	public String token() throws Exception {
		RestTemplate template = new RestTemplate();

		@SuppressWarnings("unchecked")
		Map<String, String> response = template
				.exchange(
						authServerUri
								+ "/token?grant_type={grant_type}&client_id={client_id}&code={code}&redirect_uri={redirect_uri}",
						HttpMethod.POST,
						new HttpEntity<String>(createHttpBasicAuthHeader(
								"myclient", "secret")), Map.class,
						"authorization_code", "myclient", authCode, redirectUri)
				.getBody();

		this.token = response.get("access_token");
		return this.token;
	}

	@GetMapping("/protectedResource")
	@ResponseBody
	public List<Patient> getProtectedResource() {
		
		RestTemplate template = new RestTemplate();
		List<Patient>  patients = template
				.exchange(
						resourceUri,
						HttpMethod.GET,
						new HttpEntity<String>(
								createBearerTokenAuthHeader(this.token)),
						List.class).getBody();
		
		System.out.println("****Patients: " + patients);
		return patients;
	}

	@SuppressWarnings("serial")
	private HttpHeaders createHttpBasicAuthHeader(String username,
			String password) {
		return new HttpHeaders() {
			{
				String auth = username + ":" + password;
				byte[] encodedAuth = Base64.getEncoder().encode(
						auth.getBytes(Charset.forName("US-ASCII")));
				String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

	@SuppressWarnings("serial")
	private HttpHeaders createBearerTokenAuthHeader(String token) {
		return new HttpHeaders() {
			{
				if (token != null) {
					String authHeader = "Bearer " + token;
					set("Authorization", authHeader);
				}
			}
		};
	}
}