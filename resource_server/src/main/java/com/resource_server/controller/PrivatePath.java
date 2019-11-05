package com.resource_server.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrivatePath {

	@PreAuthorize("#oauth2.hasScope('read')")
	@GetMapping("/private")
	public String privatePath() {
		return "Private";
	}
	
	@GetMapping("/user")
	public Principal getUser() {
		var user =  SecurityContextHolder.getContext().getAuthentication();
		return user;
	}
	
	@PreAuthorize("permitAll()")
	@GetMapping("/public")
	public String publicPath() {
		return "Public";
	}
}
