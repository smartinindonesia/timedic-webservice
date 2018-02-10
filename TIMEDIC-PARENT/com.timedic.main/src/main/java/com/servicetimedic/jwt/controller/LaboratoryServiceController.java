package com.servicetimedic.jwt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.HomecarePatient;
import com.servicetimedic.jwt.domain.december.LaboratoryService;
import com.servicetimedic.jwt.repository.LaboratoryServiceDbRepository;

@RestController
@RequestMapping(value = "/api/lab")
public class LaboratoryServiceController {
	
	public static final Logger logger = LoggerFactory.getLogger(LaboratoryServiceController.class);
	
	@Autowired
	private LaboratoryServiceDbRepository laboratoryServiceDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/services", method = RequestMethod.GET)
	public List<LaboratoryService> getAllLabService() {
		logger.info("Fetching all Lab Service");
		return laboratoryServiceDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/services/findByname", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryService>> getLabServiceByName(@RequestParam("name") String name) {
		logger.info("Fetching Lab Service by name");
		return new ResponseEntity<List<LaboratoryService>>(laboratoryServiceDbRepository.findByServiceName(name), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/services/findAllBetween", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryService>> getLabAllServiceBetween(@RequestParam("start") Long start, @RequestParam("end") Long end) {
		logger.info("Fetching All Lab Service between");
		return new ResponseEntity<List<LaboratoryService>>(laboratoryServiceDbRepository.findByIdBetween(start, end), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/services", method = RequestMethod.POST)
	public ResponseEntity<LaboratoryService> createLaboratoryServices(@RequestBody LaboratoryService laboratoryService) {
		LaboratoryService labservices = laboratoryServiceDbRepository.save(laboratoryService);
		logger.info("Create one Lab Service");
		return new ResponseEntity<LaboratoryService>(labservices, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/services/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLaboratoryServices(@PathVariable Long id) {
		LaboratoryService labservices = laboratoryServiceDbRepository.getOne(id);
		if (labservices != null) {
			laboratoryServiceDbRepository.deleteById(id);
			return new ResponseEntity<String>("Succesfully delete Laboratory Services with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed delete Laboratory Services", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/services/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updateLaboratoryServices(@PathVariable(value = "id") Long id,@RequestBody LaboratoryService laboratoryService) 
	{	
		LaboratoryService labservices = laboratoryServiceDbRepository.getOne(id);	
		if(labservices == null) {
			return new ResponseEntity<String>("Not Found Laboratory Service", HttpStatus.NOT_FOUND);
	    }
		else{
			labservices.setDescription(laboratoryService.getDescription());
			labservices.setPrice(laboratoryService.getPrice());
			labservices.setServiceName(laboratoryService.getServiceName());
			labservices.setServiceCode(laboratoryService.getServiceCode());
			labservices.setUriServiceIcon(laboratoryService.getUriServiceIcon());
			
			laboratoryServiceDbRepository.save(labservices);
			return new ResponseEntity<String>("Succesfully Update Laboratory Service with id "+id, HttpStatus.OK);
		}
	}
	
	
}
