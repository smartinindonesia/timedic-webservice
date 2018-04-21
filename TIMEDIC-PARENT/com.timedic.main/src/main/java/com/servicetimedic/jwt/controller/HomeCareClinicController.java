package com.servicetimedic.jwt.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.ApiError;
import com.servicetimedic.jwt.domain.december.HomecareHomecareClinic;
import com.servicetimedic.jwt.repository.HomeCareClinicDetailsDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeCareClinicController {

	public static final Logger logger = LoggerFactory.getLogger(HomeCareClinicController.class);
	
	@Autowired
	private HomeCareClinicDetailsDbRepository homeCareClinicDetailsDbRepository ;
		
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','ROLE_CLINIC')")
	@RequestMapping(value = "/getClinicDetails/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getClinicDetailsById(@PathVariable Long id, @RequestHeader(value="Authorization") String token)
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		HomecareHomecareClinic clinic = null;
		
		int idAdminFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		if(id == idAdminFromToken || roleUserFromToken.contains("ROLE_CLINIC") == true){
			clinic = homeCareClinicDetailsDbRepository.getOne(id);	
			
			if (clinic != null){
				logger.info("fetching clinic details with id " + clinic.getId());
				return new ResponseEntity<Object>(clinic, new HttpHeaders() ,HttpStatus.OK);
			}
			else{
				logger.info("clinic details not found");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("clinic details NOT_FOUND");
				return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.NOT_FOUND);
			}
		}
		else{
			logger.info("clinic details UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("clinic details with id "+ id +" is UNAUTHORIZED");

			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
		
	}
	

	
}
