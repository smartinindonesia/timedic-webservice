package com.servicetimedic.jwt.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User; 
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import com.servicetimedic.jwt.domain.december.ApiError;
import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.domain.december.HomecareCaregiver;
import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.domain.december.HomecareCaregiverStatus;
import com.servicetimedic.jwt.repository.CaregiversDbRepository;
import com.servicetimedic.jwt.repository.CaregiversSchedulle;

/**
 * All web services in this controller will be available for all the users
 */

@RestController
public class LoginCheckCaregiversRestController {
	
	@Autowired
	private CaregiversDbRepository caregiversDbRepository;
	
	@Autowired
	private CaregiversSchedulle caregiversSchedulle;

	
	@Value("${jwt.expires_in}")
    private int EXPIRES_IN;
	
	private static final String IV =   "dc0da04af8fee58593442bf834b30739";
    private static final String SALT = "dc0da04af8fee58593442bf834b30739";
    private static final int KEY_SIZE = 128;
    private static final int ITERATION_COUNT = 1000;
    private static final String PASSPHRASE = "timedictimedic18";
    
    FirebaseOptions options;
    ResourceLoader resourceLoader;
	
	public LoginCheckCaregiversRestController(ResourceLoader resourceLoader) throws IOException {
		super();
		this.resourceLoader = resourceLoader;
		Resource resource = resourceLoader.getResource("classpath:TimedicCaregiver-58cb1eb440fd.json");
        File dbAsFile = resource.getFile();
        FileInputStream serviceAccount = new FileInputStream(dbAsFile);
		
        options = new FirebaseOptions.Builder()
	    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
	    .build();
		FirebaseApp.initializeApp(options);
	}

	/**
	 * This method is used for user registration. Note: user registration is not
	 * require any authentication.
	 * 
	 * @param appUser
	 * @return
	 */
    
    //@CrossOrigin
	@RequestMapping(value = "/register/caregiver", method = RequestMethod.POST /*, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE */)
	public ResponseEntity<Object> createUser(@RequestBody HomecareCaregiver homecareCaregiver) {
		if (caregiversDbRepository.findByUsername(homecareCaregiver.getUsername()) != null) {
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("username already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.UNAUTHORIZED);
		}
		else if(caregiversDbRepository.findByEmail(homecareCaregiver.getEmail()) != null){
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("email is already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.UNAUTHORIZED);
		}
		else if(caregiversDbRepository.findByPhoneNumber(homecareCaregiver.getPhoneNumber()) != null){
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("phone number is already exist");
			return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.UNAUTHORIZED);
		}
		
		
		List<String> roles = new ArrayList<>();
		roles.add("ROLE_CAREGIVER");
		homecareCaregiver.setRoles(roles);
		
		HomecareCaregiverStatus sts = new HomecareCaregiverStatus();
		Long id = (long) 3; //suspend
		sts.setId(id);
		homecareCaregiver.setIdCaregiverStatus(sts);
		
		String generatedId = "CTMC-"+caregiversDbRepository.getMaxId();
		homecareCaregiver.setCaregiverCode(generatedId);
		homecareCaregiver.setFirstRegistrationDate(new Date());

		HomecareCaregiver data = caregiversDbRepository.save(homecareCaregiver);
		insertSchedule(data);
		return new ResponseEntity<Object>(data, HttpStatus.CREATED);
	}
	
	public void insertSchedule(HomecareCaregiver data){
		HomecareCaregiverSchedule schedule ;
		for(int i=0; i<7; i++){
			schedule = new HomecareCaregiverSchedule();
			schedule.setIdHomecareCaregiver(data);
			schedule.setStatus(false);
			schedule.setStartTime(null);
			schedule.setStartTime2(null);
			schedule.setEndTime(null);
			schedule.setEndTime2(null);
			if(i==0){schedule.setDay("monday");}
			else if(i==1){schedule.setDay("tuesday");}
			else if(i==2){schedule.setDay("wednesday");}
			else if(i==3){schedule.setDay("thursday");}
			else if(i==4){schedule.setDay("friday");}
			else if(i==5){schedule.setDay("saturday");}
			else if(i==6){schedule.setDay("sunday");}
			schedule.setDate(null);
			caregiversSchedulle.save(schedule);
		}
	}

	/**
	 * This method will return the logged user.
	 * 
	 * @param principal
	 * @return Principal java security principal object
	 */
	
	@CrossOrigin
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
	
	@RequestMapping(value = "/authenticateBySocial/caregiver", method = RequestMethod.POST)
	public ResponseEntity<Object> loginByGoogle(@RequestParam String firebaseId, @RequestParam String type ,HttpServletResponse response) throws GeneralSecurityException {
		String token = null;
		HomecareCaregiver homecareCaregiver = new HomecareCaregiver();
		
		if(type.equals("google")){
			homecareCaregiver = caregiversDbRepository.findByFirebaseIdGoogle(firebaseId);
		}
		else if(type.equals("facebook")){
			homecareCaregiver = caregiversDbRepository.findByFirebaseIdFacebook(firebaseId);
		}
		else if(type.equals("email")){
			homecareCaregiver = caregiversDbRepository.findByFirebaseIdByEmail(firebaseId);
		}
			
		Map<String, Object> tokenMap = new HashMap<String, Object>();
			
		if (homecareCaregiver != null) {
				Date exp = new Date(System.currentTimeMillis() + ( 10000 * EXPIRES_IN ));
				token = Jwts.builder()
						.setSubject(homecareCaregiver.getUsername())
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
		else{
			ApiError message = new ApiError();
			message.setMessage("Your Firebase Id is not exist");
			message.setStatus(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	
	@RequestMapping(value = "/authenticateBySocialToken/caregiver", method = RequestMethod.POST)
	public ResponseEntity<Object> loginBySocial(@RequestParam String firebaseToken, @RequestParam String type ,HttpServletResponse response) throws GeneralSecurityException, InterruptedException, ExecutionException, IOException {
		String token = null;
		String firebaseId = null;
		HomecareCaregiver homecareCaregiver = new HomecareCaregiver();
				
		
		FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdTokenAsync(firebaseToken).get();
		firebaseId = decodedToken.getUid();
		
		if(type.equals("google")){
			homecareCaregiver = caregiversDbRepository.findByFirebaseIdGoogle(firebaseId);
		}
		else if(type.equals("facebook")){
			homecareCaregiver = caregiversDbRepository.findByFirebaseIdFacebook(firebaseId);
		}
		else if(type.equals("email")){
			homecareCaregiver = caregiversDbRepository.findByFirebaseIdByEmail(firebaseId);
		}
			
		Map<String, Object> tokenMap = new HashMap<String, Object>();
			
		if (homecareCaregiver != null) {
				Date exp = new Date(System.currentTimeMillis() + ( 10000 * EXPIRES_IN ));
				token = Jwts.builder()
						.setSubject(homecareCaregiver.getUsername())
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
		else{
			ApiError message = new ApiError();
			message.setMessage("Your Firebase Id is not exist");
			message.setStatus(HttpStatus.UNAUTHORIZED);
			return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@RequestMapping(value = "/checkCaregiverPasswordIsNullOrNot", method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkCaregiverPasswordIsNullOrNot(@RequestParam("email") String email)
	{
		String pswd = caregiversDbRepository.checkPasswordIsNullOrNot(email);
		boolean status;
		
		if(pswd == null || pswd.equals("")){
			status = false;
		}
		else{
			status = true;
		}
		
		return new ResponseEntity<Boolean>(status , new HttpHeaders() ,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/passwordNurse", method = RequestMethod.POST)
	public ResponseEntity<Boolean> resetPasswordCaregiver(@RequestParam("mode") String mode, @RequestParam("oobCode") String oobCode, @RequestParam("apiKey") String apiKey, @RequestParam("type") String type, @RequestParam("newPassword") String newPassword) throws ClientProtocolException, IOException
	{
		Boolean status = false;
		String url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/resetPassword?key="+apiKey;
		
		if(type.equals("fr")){
			
			String payload = "{" +
	                "\"newPassword\": \"" + newPassword.trim() + "\", " +
	                "\"oobCode\": \"" + oobCode + "\"" +
	                "}";
	        StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_JSON);
	        HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpPost request = new HttpPost(url);
	        request.setEntity(entity);

	        HttpResponse response = httpClient.execute(request);
	        
	        if(response.getStatusLine().getStatusCode()==200){
	        	status = true;
	        }
	        else{
	        	status = false;
	        }
	        //System.out.println(response.getStatusLine().getStatusCode());
	        //System.out.println(payload);
		}
		else{
			//another method here !
		}
		
		return new ResponseEntity<Boolean>(status , new HttpHeaders() ,HttpStatus.OK);
	}

	/**
	 * @param username
	 * @param password
	 * @param response
	 * @return JSON contains token and user after success authentication.
	 * @throws IOException
	 * @throws GeneralSecurityException 
	 */
	
	//@CrossOrigin
	@RequestMapping(value = "/authenticate/caregiver", method = RequestMethod.POST)
	public ResponseEntity<Object> login(@RequestParam String email, @RequestParam String password, HttpServletResponse response) throws GeneralSecurityException {
		String token = "";
		String userPasswordAPI = "";
		String userPasswordDB = "";
		HomecareCaregiver homecareCaregiver = null; 
		Map<String, Object> tokenMap = new HashMap<String, Object>();
		
		if( email != "" || password != "" ){
			userPasswordAPI = Decrypt(password);
			//homecareCaregiver = caregiversDbRepository.findByUsername(username);
			homecareCaregiver = caregiversDbRepository.findByEmail(email);
			if(homecareCaregiver != null){
				userPasswordDB = Decrypt(homecareCaregiver.getPassword());
			}
		}
		
		if (homecareCaregiver != null && userPasswordDB.equals(userPasswordAPI)) {	
			Date exp = new Date(System.currentTimeMillis() + ( 10000 * EXPIRES_IN ));
			token = Jwts.builder()
					.setSubject(email)
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
