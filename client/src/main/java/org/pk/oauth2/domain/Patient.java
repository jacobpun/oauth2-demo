package org.pk.oauth2.domain;

import java.io.Serializable;

public class Patient implements Serializable {
	private String name;
	private int age;
	
	public Patient() {}
	
	public Patient (String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
}