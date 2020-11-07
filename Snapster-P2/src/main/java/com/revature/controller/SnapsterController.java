package com.revature.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.service.SnapsterService;

@RestController(value = "SnapsterController")

@CrossOrigin(origins = {"http://localhost:4200"}, allowedHeaders="*")

public class SnapsterController {
	
	SnapsterService snapsterService;

	@GetMapping(path = "/login")
	public String validateLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		System.out.println("SnapsterController Received Username " + username +", pwd: " + password);
		return ("<html><body>Hello returned from Login</body></html>");
	}

	@GetMapping(path = "/loginaaa")
	public void validateLogin() {
		System.out.println("SnapsterController Received Username ");
		//return ("<html><body>Hello returned from Login</body></html>");
	}
	@GetMapping(path = "/loginn", produces =MediaType.APPLICATION_JSON_VALUE)
	public void validateLoginn() {
		System.out.println("SnapsterController Received Username ");
		
	}
	

}

