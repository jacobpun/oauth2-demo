package org.pk.oauth2.authserver.contoller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ResourceController {

	@GetMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
}