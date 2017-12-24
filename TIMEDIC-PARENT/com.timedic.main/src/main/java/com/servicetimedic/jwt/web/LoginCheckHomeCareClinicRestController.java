package com.servicetimedic.jwt.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.servicetimedic.jwt.domain.december.HomecareHomecareClinicAdmin;
import com.servicetimedic.jwt.repository.HomeCareClinicDbRepository;
//import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetails;

/**
 * All web services in this controller will be available for all the users
 */

@RestController
public class LoginCheckHomeCareClinicRestController {
	@Autowired
	private HomeCareClinicDbRepository homeCareClinicDbRepository;

	/**
	 * This method is used for user registration. Note: user registration is not
	 * require any authentication.
	 * 
	 * @param appUser
	 * @return
	 */
	@RequestMapping(value = "/register/clinic", method = RequestMethod.POST /*, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE */)
	public ResponseEntity<HomecareHomecareClinicAdmin> createUser(@RequestBody HomecareHomecareClinicAdmin homecareClinicAdmin) {
		if (homeCareClinicDbRepository.findByUsername(homecareClinicAdmin.getUsername()) != null) {
			throw new RuntimeException("Username already exist");
		}
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_CAREGIVER");
		homecareClinicAdmin.setRoles(roles);
		return new ResponseEntity<HomecareHomecareClinicAdmin>(homeCareClinicDbRepository.save(homecareClinicAdmin), HttpStatus.CREATED);
	}

	/**
	 * This method will return the logged user.
	 * 
	 * @param principal
	 * @return Principal java security principal object
	 */
	@RequestMapping(value = "/logged/clinic", method = RequestMethod.GET)
	public HomecareHomecareClinicAdmin user(Principal principal) {
		//Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//String loggedUsername = auth.getName();
		System.out.println(getPrincipal());
		
		return null;//appUserRepository.findByUsername(loggedUsername);
	}
	
	private String getPrincipal(){
        String userName = null;
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        userName = userDetails.getUsername();
        
        return userName;
    }

	/**
	 * @param username
	 * @param password
	 * @param response
	 * @return JSON contains token and user after success authentication.
	 * @throws IOException
	 */
	
	@RequestMapping(value = "/authenticate/clinic", method = RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password,
			HttpServletResponse response) throws IOException {
		String token = null;
		HomecareHomecareClinicAdmin homecareClinicAdmin = homeCareClinicDbRepository.findByUsername(username);
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		
		if (homecareClinicAdmin != null && homecareClinicAdmin.getPassword().equals(password)) 
		{
			//Date expiration = Date.from(LocalDateTime.now().plusMinutes(1).toInstant(UTC));
			Date exp = new Date(System.currentTimeMillis() + 600000);
			//System.out.println("Date "+exp);
			token = Jwts.builder()
					.setSubject(username)
					.setExpiration(exp)
					.claim("roles", homecareClinicAdmin.getRoles())
					.setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey")
					.compact();
			tokenMap.put("token", token);
			tokenMap.put("user", homecareClinicAdmin);
			//System.out.println("Token "+token);
			//System.out.println(appUser.getRoles());
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.OK);
		}
		else if(homecareClinicAdmin == null){
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.NOT_FOUND);
		}
		else{
			//tokenMap.put("token", null);
			return new ResponseEntity<Map<String, Object>>(tokenMap, HttpStatus.UNAUTHORIZED);
		}

	}
}
