package com.servicetimedic.jwt.controller;

import java.util.List;

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

import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.domain.december.HomecarePatient;
import com.servicetimedic.jwt.domain.december.HomecareService;
import com.servicetimedic.jwt.repository.HomeCarePatientDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeCarePatientController {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCarePatientController.class);
	
	@Autowired
	private HomeCarePatientDbRepository homeCarePatientDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'ROLE_CLINIC')")
	@RequestMapping(value = "/patients", method = RequestMethod.GET)
	public List<HomecarePatient> getAllPatients() 
	{
		logger.info("Fetching all patients details");
		return homeCarePatientDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/patients/findbyiduser/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HomecarePatient>> getPatientsByIdUser(@PathVariable Long id)
	{
		AppUser app = new AppUser();
		app.setId(id);
		
		List<HomecarePatient> homecarePatient = homeCarePatientDbRepository.findByIdAppUser(app);
		if (homecarePatient == null)
		{
			logger.info("patients is null");
			return new ResponseEntity<List<HomecarePatient>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("fetching patients by id user");
			return new ResponseEntity<List<HomecarePatient>>(homecarePatient, HttpStatus.OK);
		}
	}
	
	/*
	private FirebaseToken checkAuthorization(HttpHeaders headers) {
	}
	*/
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','ROLE_CLINIC')")
	@RequestMapping(value = "/patients/{id}", method = RequestMethod.GET)
	public ResponseEntity<HomecarePatient> getPatientsByIdPatients(@PathVariable Long id,@RequestHeader HttpHeaders requestHeaders)
	{
		/*s
		FirebaseToken firebaseToken = checkAuthorization(requestHeaders);
		User user = userRepo.findByFirebaseId(firebaseToken.getUid());
		if (!user.getId().equals(id)) {
			throw AccessDeniedException(..)
		}
		*/
		
		HomecarePatient homecarePatient = homeCarePatientDbRepository.getOne(id);
		if (homecarePatient == null){
			logger.info("patients is null");
			return new ResponseEntity<HomecarePatient>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching patients with id "+id);
			return new ResponseEntity<HomecarePatient>(homecarePatient, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER','ROLE_CLINIC')")
	@RequestMapping(value = "/patients", method = RequestMethod.POST)
	public ResponseEntity<HomecarePatient> createPatients(@RequestBody HomecarePatient homePatient) 
	{
		HomecarePatient homecarePatient = homeCarePatientDbRepository.save(homePatient);	
		return new ResponseEntity<HomecarePatient>(homecarePatient, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/patients/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deletePatient(@PathVariable Long id) {
		HomecarePatient homecarePatient = homeCarePatientDbRepository.getOne(id);
		if (homecarePatient != null) {
			//homeCarePatientDbRepository.deleteById(id);
			homeCarePatientDbRepository.delete(id);
			return new ResponseEntity<String>("Succesfully delete patients with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed delete patients", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER','ROLE_CLINIC')")
	@RequestMapping(value = "/patients/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updatePatients(@PathVariable(value = "id") Long id,@RequestBody HomecarePatient homecarePatient) 
	{	
		HomecarePatient data = homeCarePatientDbRepository.getOne(id);	
		if(data == null) {
			return new ResponseEntity<String>("Not Found patients", HttpStatus.NOT_FOUND);
	    }
		else{
			data.setName(homecarePatient.getName());
			data.setReligion(homecarePatient.getReligion());
			data.setDateOfBirth(homecarePatient.getDateOfBirth());
			data.setPlaceOfBirth(homecarePatient.getPlaceOfBirth());
			data.setHeight(homecarePatient.getHeight());
			data.setWeight(homecarePatient.getWeight());
			homeCarePatientDbRepository.save(data);
			return new ResponseEntity<String>("Succesfully Update patients with id "+id, HttpStatus.OK);
		}
	}
	
}
