package com.servicetimedic.jwt.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.repository.UserDbRepository;

@RestController
@RequestMapping(value = "/api")
public class AppUserController {
	
	public static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
	
	
	@Autowired
	private UserDbRepository userRepository;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public List<AppUser> getAllUsers() 
	{
		logger.info("Fetching All Users Details");
		return userRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<AppUser> getUserById(@PathVariable Long id)
	{
		AppUser appUser = userRepository.findOne(id);
		if (appUser == null)
		{
			logger.info("Users is null");
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("Unknown problem when fetching users");
			return new ResponseEntity<AppUser>(appUser, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<AppUser> deleteUser(@PathVariable Long id) {
		AppUser appUser = userRepository.findOne(id);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String loggedUsername = auth.getName();
		if (appUser == null) 
		{
			return new ResponseEntity<AppUser>(HttpStatus.NO_CONTENT);
		}
		else if (appUser.getUsername().equalsIgnoreCase(loggedUsername))
		{
			throw new RuntimeException("You cannot delete your account");
		}
		else
		{
			userRepository.delete(id);
			return new ResponseEntity<AppUser>(new AppUser(), HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updateUser(@PathVariable(value = "id") Long id,@RequestBody AppUser appUser) 
	{
		if (userRepository.findByUsername(appUser.getUsername()) != null && userRepository.findByUsername(appUser.getUsername()).getId() != appUser.getId()) 
		{
			throw new RuntimeException("Username already exist");
		}
		
		AppUser findFirst = userRepository.findOne(id);
		
		if(findFirst == null) {
			return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	    }
		
		findFirst.setUsername(appUser.getUsername());
	
		userRepository.save(findFirst);
		
		return new ResponseEntity<String>("Succesfully Update", HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) 
	{
		if (userRepository.findByUsername(appUser.getUsername()) != null) 
		{
			try 
			{
				throw new RuntimeException("Username already exist");
			} 
			catch (Exception e) 
			{
				//System.out.println(e.getMessage());
			}
		}
	
		AppUser process = userRepository.save(appUser);
		
		return new ResponseEntity<AppUser>(process, HttpStatus.CREATED);
	}
	
	

}
