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
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.repository.CaregiversSchedulle;


@RestController
@RequestMapping(value = "/api")
public class CaregiverSchedulleController {

	public static final Logger logger = LoggerFactory.getLogger(CaregiverSchedulleController.class);
	
	@Autowired
	private CaregiversSchedulle caregiversSchedulle;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER')")
	@RequestMapping(value = "/caregiverSchedulle", method = RequestMethod.POST)
	public ResponseEntity<String> createCaregiverSchedulle(@RequestBody HomecareCaregiverSchedule homecareCaregiverSchedule) 
	{
		//System.out.println(homecareCaregiverSchedule.getIdHomecareCaregiver().getId());
		caregiversSchedulle.save(homecareCaregiverSchedule);
		logger.info("Succesfully Create one Homecare Caregiver schedule");
		return new ResponseEntity<String>("Succesfully Create one Homecare Caregiver schedule", HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER')")
	@RequestMapping(value = "/SchedulleByIdCaregiver/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSchedulleByIdCaregiver(@PathVariable Long id) 
	{
		List<HomecareCaregiverSchedule> schedule = caregiversSchedulle.findByIdHomecareCaregiver(id);
		if (schedule == null){
			logger.info("Homecare Caregiver Schedule is null");
			return new ResponseEntity<Object>("Homecare Caregiver Schedule is null",HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Schedule by id Caregiver "+id);
			return new ResponseEntity<Object>(schedule, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER')")
	@RequestMapping(value = "/caregiverSchedulle/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getSchedulleById(@PathVariable Long id) 
	{
		HomecareCaregiverSchedule schedule = caregiversSchedulle.getOne(id);
		if (schedule == null){
			logger.info("Homecare Caregiver Schedule is null");
			return new ResponseEntity<Object>("Homecare Caregiver Schedule is null",HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Schedule by id Caregiver "+id);
			return new ResponseEntity<Object>(schedule, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER')")
	@RequestMapping(value = "/caregiverSchedulle/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteSchedulleById(@PathVariable Long id) 
	{
		HomecareCaregiverSchedule schedule = caregiversSchedulle.getOne(id);
		if (schedule == null){
			logger.info("Homecare Caregiver Schedule is null");
			return new ResponseEntity<Object>("Failed delete Caregiver Schedule",HttpStatus.NO_CONTENT);
		}
		else{
			caregiversSchedulle.delete(id);
			logger.info("Succesfully delete Caregiver Schedule by id "+id);
			return new ResponseEntity<Object>(schedule, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER')")
	@RequestMapping(value = "/caregiverSchedulle/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSchedulleById(@PathVariable Long id, @RequestBody HomecareCaregiverSchedule homecareCaregiverSchedule) 
	{
		HomecareCaregiverSchedule schedule = caregiversSchedulle.getOne(id);
		
		if(homecareCaregiverSchedule.getDate() != null){schedule.setDate(homecareCaregiverSchedule.getDate());}
		if(homecareCaregiverSchedule.getTime() != null){schedule.setTime(homecareCaregiverSchedule.getTime());}
		
		
		if(schedule!=null){
			caregiversSchedulle.save(schedule);
			return new ResponseEntity<Object>("Succesfully update caregivers Schedulle with id "+id, HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("Failed update caregivers Schedulle", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	
}
