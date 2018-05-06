package com.servicetimedic.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.ApiError;
import com.servicetimedic.jwt.domain.december.ContactUs;
import com.servicetimedic.jwt.repository.ContactUsDbRepository;

@RestController
@RequestMapping(value = "/api")
public class ContactUsController {

	public static final Logger logger = LoggerFactory.getLogger(ContactUsController.class);
	
	@Autowired
	private ContactUsDbRepository contactUsDbRepository ;
		
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER')")
	@RequestMapping(value = "/getContactUsForNurse/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getContactUsForNurseById(@PathVariable Long id)
	{
			ContactUs contactUs = contactUsDbRepository.getOne(id);	
		
			if (contactUs != null){
				logger.info("fetching contact details with id " + contactUs.getId());
				return new ResponseEntity<Object>(contactUs, new HttpHeaders() ,HttpStatus.OK);
			}
			else{
				logger.info("contact details not found");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("contact details NOT_FOUND");
				return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.NOT_FOUND);
			}	
	}
	

	
}
