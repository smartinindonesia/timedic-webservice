package com.servicetimedic.jwt.controller;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
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

import com.servicetimedic.jwt.domain.december.HomecareServiceAssessment;
import com.servicetimedic.jwt.domain.december.HomecareService;
import com.servicetimedic.jwt.repository.HomeCareServiceAssesmentDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeCareAssessmentController {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCareAssessmentController.class);
	
	@Autowired
	private HomeCareServiceAssesmentDbRepository homeCareServiceAssesmentDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/assessments", method = RequestMethod.GET)
	public List<HomecareServiceAssessment> getAllHomeCareServiceAssessment() 
	{
		logger.info("Fetching All HomecareServiceAssessment Details");
		return homeCareServiceAssesmentDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/assessments/findbyidservices/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HomecareServiceAssessment>> getAssessmentsByIdServices(@PathVariable Long id)
	{
		HomecareService data = new HomecareService();
		data.setId(id);
		
		List<HomecareServiceAssessment> result = homeCareServiceAssesmentDbRepository.findByIdService(data);
		if (result == null)
		{
			logger.info("patients is null");
			return new ResponseEntity<List<HomecareServiceAssessment>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("fetching patients by id user");
			return new ResponseEntity<List<HomecareServiceAssessment>>(result, HttpStatus.OK);
		}
	}
	
	

}
