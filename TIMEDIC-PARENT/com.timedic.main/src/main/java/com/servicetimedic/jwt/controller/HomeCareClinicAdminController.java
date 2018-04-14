package com.servicetimedic.jwt.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.ApiError;
import com.servicetimedic.jwt.domain.december.HomecareCaregiver;
import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.domain.december.HomecareCaregiverStatus;
import com.servicetimedic.jwt.domain.december.HomecareHomecareClinic;
import com.servicetimedic.jwt.domain.december.HomecareHomecareClinicAdmin;
import com.servicetimedic.jwt.domain.december.NumberOfRows;
import com.servicetimedic.jwt.repository.CaregiversDbRepository;
import com.servicetimedic.jwt.repository.CaregiversSchedulle;
import com.servicetimedic.jwt.repository.HomeCareClinicDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeCareClinicAdminController {

	public static final Logger logger = LoggerFactory.getLogger(HomeCareClinicAdminController.class);
	
	@Autowired
	private HomeCareClinicDbRepository homeCareClinicDbRepository;
		

	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','ROLE_CLINIC')")
	@RequestMapping(value = "/clinicAdmin/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCaregiverById(@PathVariable Long id, @RequestHeader(value="Authorization") String token)
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		HomecareHomecareClinicAdmin admin = null;
		
		int idAdminFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		if(id == idAdminFromToken || roleUserFromToken.contains("ROLE_CLINIC") == true){
			admin = homeCareClinicDbRepository.getOne(id);	
			
			if (admin != null){
				logger.info("fetching clinic Admin with id " + admin.getId());
				return new ResponseEntity<Object>(admin, new HttpHeaders() ,HttpStatus.OK);
			}
			else{
				logger.info("clinic Admin not found");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("clinic Admin NOT_FOUND");
				return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.NOT_FOUND);
			}
		}
		else{
			logger.info("clinic Admin UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("clinic Admin with id "+ id +" is UNAUTHORIZED");

			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
		
	}
	

	
}
