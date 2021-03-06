package com.servicetimedic.jwt.web;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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
    
    FirebaseOptions options2;
    ResourceLoader resourceLoader;
    FirebaseApp otherApp;
    
    public LoginCheckUsersRestController(ResourceLoader resourceLoader) throws IOException {
		super();
		//InputStream dataFile = this.getClass().getClassLoader().getResourceAsStream("TimedicApps-82b5c0f87b1a.json");
		//FileInputStream serviceAccount2 = new FileInputStream(dataFile.toString());
		this.resourceLoader = resourceLoader;
		
		Resource resource = resourceLoader.getResource("classpath:timedic_user-fe23e9588298.json");
        File dbAsFile = resource.getFile();
        FileInputStream serviceAccount2 = new FileInputStream(dbAsFile);
		
		options2 = new FirebaseOptions.Builder()
	    .setCredentials(GoogleCredentials.fromStream(serviceAccount2))
	    .build();
		otherApp = FirebaseApp.initializeApp(options2,"other");
	}

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
	
	@RequestMapping(value = "/checkUserPasswordIsNullOrNot", method = RequestMethod.POST)
	public ResponseEntity<Boolean> checkUserPasswordIsNullOrNot(@RequestParam("email") String email)
	{
		String pswd = appUserRepository.checkPasswordIsNullOrNot(email);
		boolean status;
		
		if(pswd == null || pswd.equals("")){
			status = false;
		}
		else{
			status = true;
		}
		
		return new ResponseEntity<Boolean>(status , new HttpHeaders() ,HttpStatus.OK);
	}
	

	@RequestMapping(value = "/authenticateBySocial/user", method = RequestMethod.POST)
	public ResponseEntity<Object> loginBySocial(@RequestParam String firebaseId, @RequestParam String type ,HttpServletResponse response) throws GeneralSecurityException {
		String token = null;
		AppUser appUser = new AppUser();
		
		if(type.equals("google")){
			appUser = appUserRepository.findByFirebaseIdGoogle(firebaseId);
		}
		else if(type.equals("facebook")){
			appUser = appUserRepository.findByFirebaseIdFacebook(firebaseId);
		}
		else if(type.equals("email")){
			appUser = appUserRepository.findByFirebaseIdByEmail(firebaseId);
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
	
	@RequestMapping(value = "/authenticateBySocialToken/user", method = RequestMethod.POST)
	public ResponseEntity<Object> loginBySocialToken(@RequestParam String firebaseToken, @RequestParam String type ,HttpServletResponse response) throws GeneralSecurityException, InterruptedException, ExecutionException {
		String token = null;
		String firebaseId = null;
		AppUser appUser = new AppUser();
		
		FirebaseToken decodedToken = FirebaseAuth.getInstance(otherApp).verifyIdTokenAsync(firebaseToken).get();
		firebaseId = decodedToken.getUid();
		
		if(type.equals("google")){
			appUser = appUserRepository.findByFirebaseIdGoogle(firebaseId);
		}
		else if(type.equals("facebook")){
			appUser = appUserRepository.findByFirebaseIdFacebook(firebaseId);
		}
		else if(type.equals("email")){
			appUser = appUserRepository.findByFirebaseIdByEmail(firebaseId);
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
	
	@RequestMapping(value = "/passwordUser", method = RequestMethod.POST)
	public ResponseEntity<Boolean> resetPasswordUser(@RequestParam("mode") String mode, @RequestParam("oobCode") String oobCode, @RequestParam("apiKey") String apiKey, @RequestParam("type") String type, @RequestParam("newPassword") String newPassword) throws ClientProtocolException, IOException
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
