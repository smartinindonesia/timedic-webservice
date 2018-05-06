package com.servicetimedic.jwt.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.ApiError;
import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.domain.december.HomecarePatient;
import com.servicetimedic.jwt.repository.HomeCarePatientDbRepository;
import com.servicetimedic.jwt.repository.UserDbRepository;

/**
 * All web services in this controller will be available for all the users
 */

@RestController
public class LoginCheckUsersRestController {
	
	@Autowired
	private UserDbRepository appUserRepository;
	
	@Autowired
	private HomeCarePatientDbRepository homeCarePatientDbRepository;
	
	@Value("${jwt.expires_in}")
    private int EXPIRES_IN;
	
	private static final String IV =   "dc0da04af8fee58593442bf834b30739";
    private static final String SALT = "dc0da04af8fee58593442bf834b30739";
    private static final int KEY_SIZE = 128;
    private static final int ITERATION_COUNT = 1000;
    private static final String PASSPHRASE = "timedictimedic18";

	/**
	 * This method is used for user registration. Note: user registration is not
	 * require any authentication.
	 * 
	 * @param appUser
	 * @return
	 */
    
    //@CrossOrigin
	@RequestMapping(value = "/register/user", method = RequestMethod.POST /*, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE */)
	public ResponseEntity<Object> createUser(@RequestBody AppUser appUser) {
		if (appUserRepository.findByUsername(appUser.getUsername()) != null) {
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("username already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.UNAUTHORIZED);
		}
		else if(appUserRepository.findByEmail(appUser.getEmail()) != null){
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("email is already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.UNAUTHORIZED);
		}
		else if(appUserRepository.findByPhoneNumber(appUser.getPhoneNumber()) != null){
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("phone number is already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.UNAUTHORIZED);
		}
		
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_USER");
		appUser.setRoles(roles);
		
		String generatedId = "UTMC-"+appUserRepository.getMaxId(); 
		String front = "", middle="", last="";
		appUser.setUserCode(generatedId);
		appUser.setFirstRegistrationDate(new Date());	
		HomecarePatient patients = new HomecarePatient();
		AppUser userRegistered = appUserRepository.save(appUser);
		if(appUser.getFrontName()!=null){
			front = appUser.getFrontName();
			patients.setName(front+" (Your Self)");
		}
		else if(appUser.getMiddleName()!=null){
			middle = appUser.getMiddleName();
			patients.setName(middle+" (Your Self)");
		}
		else if(appUser.getLastName()!=null){
			last = appUser.getLastName();
			patients.setName(last+" (Your Self)");
		}
		else if(appUser.getFrontName()!=null && userRegistered.getLastName()!=null){
			last = appUser.getLastName();
			front = appUser.getFrontName();
			patients.setName(front+" "+last+"(Your Self)");
		}
		else if(appUser.getFrontName()!=null && appUser.getMiddleName()!=null){
			middle = appUser.getMiddleName();
			front = appUser.getFrontName();
			patients.setName(front+" "+last+"(Your Self)");
		}
		else if(appUser.getFrontName()!=null && appUser.getMiddleName()!=null && appUser.getLastName()!=null){
			middle = appUser.getMiddleName();
			front = appUser.getFrontName();
			last = appUser.getLastName();
			patients.setName(front+" "+middle+" "+last+"(Your Self)");
		}
		patients.setIdAppUser(userRegistered);
		
		homeCarePatientDbRepository.save(patients);
		
		return new ResponseEntity<Object>(userRegistered, HttpStatus.CREATED);
	}

	/**
	 * This method will return the logged user.
	 * 
	 * @param principal
	 * @return Principal java security principal object
	 */
	//@CrossOrigin
	@RequestMapping(value = "/logged/user", method = RequestMethod.GET)
	public AppUser user(Principal principal) {
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
	
	@RequestMapping(value = "/authenticateBySocial/user", method = RequestMethod.POST)
	public ResponseEntity<Object> loginByGoogle(@RequestParam String firebaseId, @RequestParam String type ,HttpServletResponse response) throws GeneralSecurityException {
		String token = null;
		AppUser appUser = new AppUser();
		
		if(type.equals("google")){
			appUser = appUserRepository.findByFirebaseIdGoogle(firebaseId);
		}
		else if(type.equals("facebook")){
			appUser = appUserRepository.findByFirebaseIdFacebook(firebaseId);
		}
			
		Map<String, Object> tokenMap = new HashMap<String, Object>();
			
		if (appUser != null) {
				Date exp = new Date(System.currentTimeMillis() + ( 10000 * EXPIRES_IN ));
				token = Jwts.builder()
						.setSubject(appUser.getUsername())
						.setExpiration(exp)
						.claim("roles", appUser.getRoles())
						.claim("id", appUser.getId())
						.setIssuedAt(new Date())
						.signWith(SignatureAlgorithm.HS256, "secretkey")
						.compact();
				tokenMap.put("token", token);
				tokenMap.put("user", appUser);
				return new ResponseEntity<Object>(tokenMap, HttpStatus.OK);
		}
		else{
			ApiError message = new ApiError();
			message.setMessage("Your Firebase Id is not exist");
			message.setStatus(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * @param username
	 * @param password
	 * @param response
	 * @return JSON contains token and user after success authentication.
	 * @throws GeneralSecurityException 
	 * @throws IOException
	 */
	
	//@CrossOrigin
	@RequestMapping(value = "/authenticate/user", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) throws GeneralSecurityException {
		String token = null;
		String userPasswordDB = "";
		String userPasswordAPI = "";
		AppUser appUser = null;
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		
		if( username != "" || password != "" ){
			userPasswordAPI = Decrypt(password);
			appUser = appUserRepository.findByUsername(username);
			if(appUser != null){
				userPasswordDB = Decrypt(appUser.getPassword());
			}
		}
		
		if (appUser != null && userPasswordDB.equals(userPasswordAPI)) {
			Date exp = new Date(System.currentTimeMillis() + ( 10000 * EXPIRES_IN ));
			token = Jwts.builder()
					.setSubject(username)
					.setExpiration(exp)
					.claim("roles", appUser.getRoles())
					.claim("id", appUser.getId())
					.setIssuedAt(new Date())
					.signWith(SignatureAlgorithm.HS256, "secretkey")
					.compact();
			tokenMap.put("token", token);
			tokenMap.put("user", appUser);
			return new ResponseEntity<Object>(tokenMap, HttpStatus.OK);
		}
		else if(appUser == null){
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("INVALID USERNAME OR PASSWORD");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
		else{
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("UNAUTHORIZED REQUEST / INVALID USERNAME OR PASSWORD");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	public String  Decrypt(String CIPHER_TEXT) throws GeneralSecurityException {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION_COUNT);
        String decrypt = util.decrypt(SALT, IV, PASSPHRASE, CIPHER_TEXT);
        return decrypt;
    }
	
	public String Encrypt(String PLAIN_TEXT) {
        AesUtilHelper util = new AesUtilHelper(KEY_SIZE, ITERATION_COUNT);
        String encrypt = util.encrypt(SALT, IV, PASSPHRASE, PLAIN_TEXT);     
        return encrypt;
    }
	
	
}
