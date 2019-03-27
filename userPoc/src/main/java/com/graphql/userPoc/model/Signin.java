package com.graphql.userPoc.model;

public class Signin {
	
	private final String token;
	private final User user;
	public Signin(String token, User user) {
		this.token = token;
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public User getUser() {
		return user;
	}
	

}
