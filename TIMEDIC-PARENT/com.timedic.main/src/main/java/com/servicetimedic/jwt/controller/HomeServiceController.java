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

import com.servicetimedic.jwt.domain.december.HomecareService;
import com.servicetimedic.jwt.repository.HomeServicesDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeServiceController {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeServiceController.class);
	
	@Autowired
	private HomeServicesDbRepository homeServicesDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/homecareservices", method = RequestMethod.GET)
	public List<HomecareService> getAllServices() 
	{
		logger.info("Fetching All homecareservices Details");
		return homeServicesDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/homecareservices/{id}", method = RequestMethod.GET)
	public ResponseEntity<HomecareService> getServicesById(@PathVariable Long id)
	{
		HomecareService homecareService = homeServicesDbRepository.getOne(id);
		if (homecareService == null)
		{
			logger.info("homecareservices is null");
			return new ResponseEntity<HomecareService>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("fetching homecareservices by id "+id);
			return new ResponseEntity<HomecareService>(homecareService, HttpStatus.OK);
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/homecareservices/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteServices(@PathVariable Long id) {
		HomecareService homecareService = homeServicesDbRepository.getOne(id);
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//String loggedUsername = auth.getName();
		if (homecareService != null) {
			//homeServicesDbRepository.deleteById(id);
			homeServicesDbRepository.delete(id);
			return new ResponseEntity<String>("Succesfully delete Services with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed delete Services", HttpStatus.FAILED_DEPENDENCY);
		}

	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/homecareservices/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updateServices(@PathVariable(value = "id") Long id,@RequestBody HomecareService homecareService) 
	{	
		HomecareService findFirst = homeServicesDbRepository.getOne(id);	
		if(findFirst == null) {
			return new ResponseEntity<String>("Not Found", HttpStatus.NOT_FOUND);
	    }
		else{
			findFirst.setServiceCaterogry(homecareService.getServiceCaterogry());
			findFirst.setServiceCode(homecareService.getServiceCode());
			findFirst.setServiceName(homecareService.getServiceName());
			findFirst.setServiceUrlIcon(homecareService.getServiceUrlIcon());
			homeServicesDbRepository.save(findFirst);
			
			return new ResponseEntity<String>("Succesfully Update Services with id "+id, HttpStatus.OK);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/homecareservices", method = RequestMethod.POST)
	public ResponseEntity<HomecareService> createServices(@RequestBody HomecareService homecareService) 
	{
		HomecareService process = homeServicesDbRepository.save(homecareService);
		
		return new ResponseEntity<HomecareService>(process, HttpStatus.CREATED);
	}
	
	

}
