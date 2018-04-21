package com.servicetimedic.jwt.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.servicetimedic.jwt.domain.december.HomecareCaregiver;
import com.servicetimedic.jwt.domain.december.HomecareCaregiverRate;
import com.servicetimedic.jwt.domain.december.OrderRespons;
import com.servicetimedic.jwt.domain.december.RatingRespon;
import com.servicetimedic.jwt.repository.CaregiversRateRepository;


@RestController
@RequestMapping(value = "/api")
public class CaregiverRateController {
	
	public static final Logger logger = LoggerFactory.getLogger(CaregiverRateController.class);
	
	@Autowired
	private CaregiversRateRepository caregiversRateRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN' , 'SUPERADMIN' , 'USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverRate", method = RequestMethod.POST)
	public ResponseEntity<Object> createCaregiverRate(@RequestBody HomecareCaregiverRate homecareCaregiverRate) 
	{
		caregiversRateRepository.save(homecareCaregiverRate);
		String message = "You rate has been recorded in system, Thank You for your rating";
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		OrderRespons respon = new OrderRespons();
		respon.setMessage(message);
		respon.setHttpStatus(HttpStatus.CREATED);
		respon.setDate(dateFormat.format(date));
		
		return new ResponseEntity<Object>(respon, new HttpHeaders() ,HttpStatus.CREATED);
	}
	
	public  double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'ROLE_CLINIC', 'USER')")
	@RequestMapping(value = "/getCaregiverRateByIdCaregiver/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCaregiverRateByIdCaregiver(@PathVariable Long id)
	{
		int sumOfData = caregiversRateRepository.findCaregiverRateByIdCaregiver(id).size();
		long numberData = caregiversRateRepository.findCaregiverRateByIdCaregiverGetSum(id);
		double x = new Double(sumOfData);
		double avg = new Double(numberData) / x;
		//System.out.println(sumOfData + " " +x);
		
		RatingRespon respon = new RatingRespon();
		respon.setAverageRating(round(avg, 3));
		respon.setIdCaregiver(id);
		
		return new ResponseEntity<Object>(respon, new HttpHeaders() ,HttpStatus.OK);
	}

}
