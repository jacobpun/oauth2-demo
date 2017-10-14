package org.pk.oauth2.authserver.dao;

import java.util.HashMap;
import java.util.Map;

import org.pk.oauth2.authserver.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

	// Fake users
	private Map<String, UserDetails> userDetailsMap = new HashMap<>();
	
	 {
		UserDetails pk = new User("pk", "pkpassword", "ROLE_USER", "ROLE_ADMIN");
		UserDetails dony = new User("dony", "donypassword", "ROLE_USER", "ROLE_ADMIN");
		UserDetails nitya = new User("nitya", "nityapassword", "ROLE_CHILD");
		UserDetails shena = new User("sneha", "snehapassword", "ROLE_CHILD");	
		
		userDetailsMap.put(pk.getUsername(), pk);
		userDetailsMap.put(dony.getUsername(), dony);
		userDetailsMap.put(nitya.getUsername(), nitya);
		userDetailsMap.put(shena.getUsername(), shena);
	}
	
	public UserDetails findByName(String name) {
		UserDetails user =  userDetailsMap.get(name);
		
		System.out.println("FOUND: " + user);
		return user;
	}
}