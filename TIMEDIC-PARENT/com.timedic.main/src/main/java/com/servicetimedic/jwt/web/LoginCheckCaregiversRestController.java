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

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.ApiError;
import com.servicetimedic.jwt.domain.december.HomecareCaregiver;
import com.servicetimedic.jwt.repository.CaregiversDbRepository;






//import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetails;

/**
 * All web services in this controller will be available for all the users
 */

@RestController
public class LoginCheckCaregiversRestController {
	
	@Autowired
	private CaregiversDbRepository caregiversDbRepository;

	
	@Value("${jwt.expires_in}")
    private int EXPIRES_IN;
	
	
	/**
	 * This method is used for user registration. Note: user registration is not
	 * require any authentication.
	 * 
	 * @param appUser
	 * @return
	 */
	@RequestMapping(value = "/register/caregiver", method = RequestMethod.POST /*, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE */)
	public ResponseEntity<Object> createUser(@RequestBody HomecareCaregiver homecareCaregiver) {
		if (caregiversDbRepository.findByUsername(homecareCaregiver.getUsername()) != null) {
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.OK);
			error.setMessage("user already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.OK);
		}
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_CAREGIVER");
		homecareCaregiver.setRoles(roles);
		return new ResponseEntity<Object>(caregiversDbRepository.save(homecareCaregiver), HttpStatus.CREATED);
	}

	/**
	 * This method will return the logged user.
	 * 
	 * @param principal
	 * @return Principal java security principal object
	 */
	@RequestMapping(value = "/logged/caregiver", method = RequestMethod.GET)
	public HomecareCaregiver user(Principal principal) {
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
	
	@RequestMapping(value = "/authenticate/caregiver", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws IOException {
		String token = null;
		
		String userPasswordAPI = Decrypt(password, "timedictimedic18");
		
		HomecareCaregiver homecareCaregiver = caregiversDbRepository.findByUsername(username);
		
		String userPasswordDB = Decrypt(homecareCaregiver.getPassword(), "timedictimedic18");
		
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		
		//if (homecareCaregiver != null && homecareCaregiver.getPassword().equals(password)){
		if (homecareCaregiver != null && userPasswordDB.equals(userPasswordAPI)) {	
			Date exp = new Date(System.currentTimeMillis() + ( 10000 * EXPIRES_IN ));
			token = Jwts.builder()
					.setSubject(username)
					.setExpiration(exp)
					.claim("roles", homecareCaregiver.getRoles())
					.claim("id", homecareCaregiver.getId())
					.setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey")
					.compact();
			tokenMap.put("token", token);
			tokenMap.put("user", homecareCaregiver);
			return new ResponseEntity<Object>(tokenMap, HttpStatus.OK);
		}
		else if(homecareCaregiver == null){
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.NOT_FOUND);
			error.setMessage("INVALID USERNAME OR PASSWORD");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.NOT_FOUND);
		}
		else{
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("UNAUTHORIZED REQUEST / INVALID USERNAME OR PASSWORD");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	public String Decrypt(String source, String key){
	    byte[] raw = key.getBytes();
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	    String decrypted = null;
	    try
	    {
	      Cipher cipher = Cipher.getInstance("AES");
	      cipher.init(2, skeySpec);
	      decrypted = new String(cipher.doFinal(Base64.decodeBase64(source)));
	    }
	    catch (Exception e)
	    {
	      System.out.println(e.getMessage());
	    }
	    return decrypted;
	}
}
