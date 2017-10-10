package org.pk.oauth2.controller;

import java.util.Arrays;
import java.util.List;

import org.pk.oauth2.domain.Patient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protected")
public class PatientController {

	@GetMapping("/patients")
	public List<Patient> getAll () {
		return Arrays.asList(new Patient("John", 89), new Patient("Matt", 100));
	}
}