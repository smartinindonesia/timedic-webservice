package com.servicetimedic.jwt.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverRate;
import com.servicetimedic.jwt.repository.CaregiversRateRepository;


@RestController
@RequestMapping(value = "/api")
public class CaregiverRateController {
	
	public static final Logger logger = LoggerFactory.getLogger(CaregiverRateController.class);
	
	@Autowired
	private CaregiversRateRepository caregiversRateRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'CAREGIVER')")
	@RequestMapping(value = "/caregiverRate", method = RequestMethod.POST)
	public ResponseEntity<Object> createCaregiverRate(@RequestBody HomecareCaregiverRate homecareCaregiverRate) 
	{
		caregiversRateRepository.save(homecareCaregiverRate);
		String message = "Thanks for your rating";
		return new ResponseEntity<Object>(message, new HttpHeaders() ,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'ROLE_CLINIC', 'USER')")
	@RequestMapping(value = "/getCaregiverRateByIdCaregiver/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCaregiverById(@PathVariable Long id)
	{
		int sumOfData = caregiversRateRepository.findCaregiverRateByIdCaregiver(id).size();
		
		return new ResponseEntity<Object>(null , new HttpHeaders() ,HttpStatus.OK);
	}

}
