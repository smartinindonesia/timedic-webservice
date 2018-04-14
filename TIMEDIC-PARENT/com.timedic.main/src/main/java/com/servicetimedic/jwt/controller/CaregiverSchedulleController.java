package com.servicetimedic.jwt.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.repository.CaregiversSchedulle;

@RestController
@RequestMapping(value = "/api")
public class CaregiverSchedulleController {

	public static final Logger logger = LoggerFactory.getLogger(CaregiverSchedulleController.class);
	
	@Autowired
	private CaregiversSchedulle caregiversSchedulle;
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulle", method = RequestMethod.POST)
	public ResponseEntity<String> createCaregiverSchedulle(@RequestBody HomecareCaregiverSchedule homecareCaregiverSchedule) throws ParseException 
	{
		caregiversSchedulle.save(homecareCaregiverSchedule);
		logger.info("Succesfully Create one Homecare Caregiver schedule");
		return new ResponseEntity<String>("Succesfully Create one Homecare Caregiver schedule", HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulleArray", method = RequestMethod.POST)
	public ResponseEntity<String> createCaregiverSchedulleArrayList(@RequestBody List<HomecareCaregiverSchedule> homecareCaregiverSchedule) 
	{
		caregiversSchedulle.save(homecareCaregiverSchedule);
		logger.info("Succesfully Create one Homecare Caregiver schedule for a week");
		return new ResponseEntity<String>("Succesfully Create one Homecare Caregiver schedule for a week", HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
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
	
	public Date convertStringTimeToDate(String time) throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		String dateInString = time;
		Date date = formatter.parse(dateInString);
		
		return date;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulleByTime", method = RequestMethod.POST)
	public ResponseEntity<Object> SchedulleByTime(@RequestParam("time") String time , @RequestParam("day") String day) throws ParseException 
	{
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		String dateInString = time;
		Date date = formatter.parse(dateInString);
		
		List<HomecareCaregiverSchedule> schedule = caregiversSchedulle.findByTime(date, day);
		
		if (schedule == null){
			logger.info("Homecare Caregiver Schedule is null");
			return new ResponseEntity<Object>("Homecare Caregiver Schedule is null",HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Schedule by time "+time);
			return new ResponseEntity<Object>(schedule, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulleCekData", method = RequestMethod.POST)
	public ResponseEntity<Object> cekDataSchedulle(@RequestParam("idCaregiver") Long idCaregiver, @RequestParam("day") String day){
		
		//HomecareCaregiverSchedule data = caregiversSchedulle.cekDataCaregiverSchedule(idCaregiver, day);
		
		if(caregiversSchedulle.cekDataCaregiverSchedule(idCaregiver, day) != null){
			return new ResponseEntity<Object>("There is a data", HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Object>("No data available", HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulleCekStatus", method = RequestMethod.POST)
	public ResponseEntity<Object> cekStatusSchedulle(@RequestParam("idCaregiver") Long idCaregiver){
		
		List<HomecareCaregiverSchedule> data = caregiversSchedulle.findByIdHomecareCaregiverStatusAndDay(idCaregiver);
		
		if(data != null){
			return new ResponseEntity<Object>(data, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<Object>("No data available", HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
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
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
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
			return new ResponseEntity<Object>("Succesfully delete Caregiver Schedule by id "+id, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'ROLE_CLINIC' , 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulleByIdCaregiverAndDay", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSchedulleByIdCaregiverAndDay(@RequestParam("idCaregiver") Long idCaregiver, @RequestParam("day") String day, @RequestParam("status") boolean sts) 
	{
		HomecareCaregiverSchedule schedule = caregiversSchedulle.cekDataCaregiverSchedule(idCaregiver, day);
		
		if(schedule!=null){
			schedule.setStatus(sts);
			caregiversSchedulle.save(schedule);
			return new ResponseEntity<Object>("Succesfully update caregivers Schedulle with id "+idCaregiver +" and "+day, HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("Failed update caregivers Schedulle", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/updateCaregiverSchedulleForTime", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCaregiverSchedulleForTime(@RequestParam("id") Long id, @RequestParam("startTime1") String startTime1, @RequestParam("startTime2") String startTime2, @RequestParam("endTime1") String endTime1, @RequestParam("endTime2") String endTime2) throws ParseException 
	{
		HomecareCaregiverSchedule schedule = caregiversSchedulle.getOne(id);
		
		if(startTime1 != null){schedule.setStartTime(convertStringTimeToDate(startTime1));}
		if(startTime2 != null){schedule.setStartTime2(convertStringTimeToDate(startTime2));}
		if(endTime1 != null){schedule.setEndTime(convertStringTimeToDate(endTime1));}
		if(endTime2 != null){schedule.setEndTime2(convertStringTimeToDate(endTime2));}
		
		if(schedule!=null){
			caregiversSchedulle.save(schedule);
			return new ResponseEntity<Object>("Succesfully update caregivers Schedulle with id "+id, HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("Failed update caregivers Schedulle", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN', 'ROLE_CLINIC', 'CAREGIVER')")
	@RequestMapping(value = "/caregiverSchedulle/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateSchedulleById(@PathVariable Long id, @RequestBody HomecareCaregiverSchedule homecareCaregiverSchedule) 
	{
		HomecareCaregiverSchedule schedule = caregiversSchedulle.getOne(id);
		
		if(homecareCaregiverSchedule.getDate() != null){schedule.setDate(homecareCaregiverSchedule.getDate());}
		if(homecareCaregiverSchedule.getStartTime() != null){schedule.setStartTime(homecareCaregiverSchedule.getStartTime());}
		if(homecareCaregiverSchedule.getStartTime2() != null){schedule.setStartTime2(homecareCaregiverSchedule.getStartTime2());}
		if(homecareCaregiverSchedule.getEndTime() != null){schedule.setEndTime(homecareCaregiverSchedule.getEndTime());}
		if(homecareCaregiverSchedule.getEndTime2() != null){schedule.setEndTime2(homecareCaregiverSchedule.getEndTime2());}
		if(homecareCaregiverSchedule.isStatus() != null){schedule.setStatus(homecareCaregiverSchedule.isStatus());}
		//if(!homecareCaregiverSchedule.isStatus()){schedule.setStatus(!homecareCaregiverSchedule.isStatus());}
		
		if(schedule!=null){
			caregiversSchedulle.save(schedule);
			return new ResponseEntity<Object>("Succesfully update caregivers Schedulle with id "+id, HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("Failed update caregivers Schedulle", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	
}
