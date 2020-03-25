//test change
//New branch for merge request
package com.example.demo.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.demo.repositories.LoginRepository;
import com.example.demo.services.LoginService;
import com.example.demo.models.Login;

import java.util.*;

@RestController
public class LoginController {

	@Autowired
	private LoginRepository loginRepo;
	private LoginService loginService;

	private final Logger logger = LoggerFactory.getLogger(LoginController.class);

//	@GetMapping("/login")
//	List<Login> getAllUsers(){
//		List<Login> results = loginRepo.findAll();
//		return results;
//	}

	@GetMapping("/login/{id}")
	Optional<Login> getId(@PathVariable Integer id) {
		return loginRepo.findById(id);
	}
	
	@PostMapping(path = "/test")
	public String test(@RequestBody String str) {
		
		if(str.equals("testing")) {
			return "ok";
		}
		else return "fail";
	}

	@PostMapping(path = "/login/register")
	public Map registration(@RequestBody Login login) {
		
		Map<String, String> exists = Collections.singletonMap("Status","Username Exists");
		Map<String, String> success = Collections.singletonMap("Status","Successful");
		
		if(loginRepo.findByUsername(login.getUsername())==null) {
			loginRepo.save(login);
			
			return success;
		}
		
		else return exists;
		
		
		
		
	}

	@SuppressWarnings("rawtypes")
	@PostMapping(path = "/login")
	public Map authenticate(@RequestBody Login login) {
		
		Map<String, String> success = Collections.singletonMap("Status","Login Success");
		Map<String, String> fail = Collections.singletonMap("Status","Login Fail");
		Map<String, String> name_fail = Collections.singletonMap("Status","Username not found!");
		
		if(loginRepo.findByUsername(login.getUsername())==null) {
			return name_fail;
		}
		
		String pass=login.getPassword();
		String correctPass=loginRepo.findByUsername(login.getUsername()).getPassword();
		
		
		
			//id = loginRepo.findByUsername(login.getUsername()).getId();
			
			
			if(pass.equals(correctPass)) {
				return success;
			}
		
			else return fail;


	}



}
