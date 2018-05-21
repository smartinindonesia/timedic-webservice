package com.servicetimedic.jwt.controller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
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
import com.servicetimedic.jwt.domain.december.NumberOfRows;
import com.servicetimedic.jwt.fcm.push.notification.AndroidPushNotificationsServiceForCaregiver;
import com.servicetimedic.jwt.repository.CaregiversDbRepository;
import com.servicetimedic.jwt.repository.CaregiversSchedulle;

@RestController
@RequestMapping(value = "/api")
public class CaregiverController {

	public static final Logger logger = LoggerFactory.getLogger(CaregiverController.class);
	
	@Autowired
	private CaregiversDbRepository caregiversDbRepository;
	
	@Autowired
	private CaregiversSchedulle caregiversSchedulle;
	
	@Autowired
	AndroidPushNotificationsServiceForCaregiver androidPushNotificationsServiceForCaregiver;
	
	private Pageable createPageRequest(int page, int size, String sort, String field) {
		Direction direction;
		if(sort.equals("ASC")){direction = Sort.Direction.ASC;}
		else{direction = Sort.Direction.DESC;}
		return new PageRequest(page, size, direction, field);
	}
	
	public String sendNotifToCaregiver(String fcmTOken, String title ,String message) throws JSONException {
		
		String firebaseResponse = "";
		JSONObject body = new JSONObject();
		body.put("to", fcmTOken);
		body.put("priority", "high");

		JSONObject notification = new JSONObject();
		notification.put("title", title);
		notification.put("body", message);
		notification.put("sound","default");
		
		body.put("notification", notification);
		
		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = androidPushNotificationsServiceForCaregiver.send(request);
		CompletableFuture.allOf(pushNotification).join();
			
		try {
			firebaseResponse = pushNotification.get();
		} 
		catch (InterruptedException e) {
			e.printStackTrace();
		} 
		catch (ExecutionException e) {
			e.printStackTrace();
		}
		return firebaseResponse;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiverByField", method = RequestMethod.GET)
	public HomecareCaregiver getCaregiversByField(@RequestParam("searchField") String searchField, @RequestParam("value") String value) {
		HomecareCaregiver data = null;
		if(searchField.equals("sipp")){
			data = caregiversDbRepository.findBySipp(value);
			// every nurse has only one SIPP
		}
		else if(searchField.equals("nursenumber")){
			data = caregiversDbRepository.findByRegisterNurseNumber(value);
			// every nurse has only one Nurse Number
		}
		else if(searchField.equals("username")){
			data = caregiversDbRepository.findByUsername(value);
			// every nurse has only one username
		}
		else if(searchField.equals("email")){
			data = caregiversDbRepository.findByEmail(value);
			// every nurse has only one email
		}
		else if(searchField.equals("phonenumber")){
			data = caregiversDbRepository.findByPhoneNumber(value);
			// every nurse has only one phone number
		}
		else if(searchField.equals("caregiverCode")){
			data = caregiversDbRepository.findByCaregiverCode(value);
			// every nurse has only one phone number
		}
		return data;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiversWithPaginationByField", method = RequestMethod.GET)
	public List<Object> getAllCaregiversWithPaginationByField(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("searchField") String searchField, @RequestParam("value") String value) {
		
		List<HomecareCaregiver> data = new ArrayList<>();
		HomecareCaregiver data2 = new HomecareCaregiver();
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(searchField.equals("firstname")){
			data = caregiversDbRepository.findCaregiverByfrontName(value, createPageRequest(page, size, sort, sortField));
			rowCount = caregiversDbRepository.findCaregiverByfrontNameGetCount(value).size();
		}
		else if(searchField.equals("middlename")){
			data = caregiversDbRepository.findCaregiverBymiddleName(value, createPageRequest(page, size, sort, sortField));
			rowCount = caregiversDbRepository.findCaregiverBymiddleNameGetCount(value).size();
		}
		else if(searchField.equals("lastname")){
			data = caregiversDbRepository.findCaregiverBylastName(value, createPageRequest(page, size, sort, sortField));
			rowCount = caregiversDbRepository.findCaregiverBylastNameGetCount(value).size();
		}
		else if(searchField.equals("email")){
			data2  = caregiversDbRepository.findByEmail(value);
			rowCount = 1;
			data.add(data2);
		}
		else if(searchField.equals("sipp")){
			data2  = caregiversDbRepository.findBySipp(value);
			rowCount = 1;
			data.add(data2);
		}
		else if(searchField.equals("username")){
			data2  = caregiversDbRepository.findByUsername(value);
			rowCount = 1;
			data.add(data2);
		}
		else if(searchField.equals("str")){
			data2  = caregiversDbRepository.findByRegisterNurseNumber(value);
			rowCount = 1;
			data.add(data2);
		}
		else if(searchField.equals("phoneNumber")){
			data2  = caregiversDbRepository.findByPhoneNumber(value);
			rowCount = 1;
			data.add(data2);
		}
		else if(searchField.equals("caregiverCode")){
			data2  = caregiversDbRepository.findByCaregiverCode(value);
			rowCount = 1;
			data.add(data2);
		}
		
		logger.info("Fetching caregivers with "+ searchField +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiversWithPaginationByFieldGetCount", method = RequestMethod.GET)
	public NumberOfRows getAllCaregiversWithPaginationByFieldGetCount(@RequestParam("searchField") String searchField, @RequestParam("value") String value) {
	
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(searchField.equals("fistname")){
			rowCount = caregiversDbRepository.findCaregiverByfrontNameGetCount(value).size();
		}
		else if(searchField.equals("middlename")){
			rowCount = caregiversDbRepository.findCaregiverBymiddleNameGetCount(value).size();
		}
		else if(searchField.equals("lastname")){
			rowCount = caregiversDbRepository.findCaregiverBylastNameGetCount(value).size();
		}	
		rows.setNumOfRows(rowCount);
		return rows;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiversWithPagination", method = RequestMethod.GET)
	public List<Object> getAllCaregiversWithPagination(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField) {
		List<HomecareCaregiver> data = caregiversDbRepository.findAllCaregiver(createPageRequest(page, size, sort, sortField));
		logger.info("Fetching All caregivers Details with pagination order by " + sortField + " " + sort);
		
		NumberOfRows rows = new NumberOfRows();
		rows.setNumOfRows(caregiversDbRepository.findAll().size());
		List<Object> list = new ArrayList<Object>();
		list.add(data);
		list.add(rows);
		
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiversWithPaginationGetCount", method = RequestMethod.GET)
	public NumberOfRows getAllCaregiversWithPaginationGetCount() {
	
		NumberOfRows rows = new NumberOfRows();
		rows.setNumOfRows(caregiversDbRepository.findAll().size());
	
		return rows;
	}
	
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiver/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getCaregiverById(@PathVariable Long id, @RequestHeader(value="Authorization") String token)
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		HomecareCaregiver caregiver = null;
		
		int idCaregiverFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		if(id == idCaregiverFromToken || roleUserFromToken.contains("ROLE_ADMIN") == true || roleUserFromToken.contains("ROLE_CLINIC") == true){
			caregiver = caregiversDbRepository.getOne(id);	
			
			if (caregiver != null){
				logger.info("fetching caregiver with id " + caregiver.getId());
				return new ResponseEntity<Object>(caregiver, new HttpHeaders() ,HttpStatus.OK);
			}
			else{
				logger.info("caregiver not found");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("caregiver NOT_FOUND");
				return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.NOT_FOUND);
			}
		}
		else{
			logger.info("caregiver UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("caregiver with id "+ id +" is UNAUTHORIZED");

			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER')")
	@RequestMapping(value = "/getUrlByIdcaregiver", method = RequestMethod.POST)
	public ResponseEntity<String> getUrlById(@RequestParam("idCaregiver") String idCaregiver, @RequestParam("type") String type , @RequestHeader(value="Authorization") String token)
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		String Urlcaregiver = "";
		
		int idCaregiverFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		Long id = Long.parseLong(idCaregiver);
		
		if(id == idCaregiverFromToken || roleUserFromToken.contains("ROLE_ADMIN") == true){
			
			if(type.equals("sipp")){
				Urlcaregiver = caregiversDbRepository.findSippUrlById(id);
			}	
			else if(type.equals("str")){
				Urlcaregiver = caregiversDbRepository.findStrUrlById(id);	
			}
			
			if (Urlcaregiver != null){
				logger.info("fetching url "+type+" with id caregiver = " + id);
				return new ResponseEntity<String>(Urlcaregiver, new HttpHeaders() ,HttpStatus.OK);
			}
			else{
				logger.info("fetching url "+type+" with id caregiver = " + id+" is not found");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("url "+type +"caregiver NOT FOUND");
				return new ResponseEntity<String>("url "+type +"caregiver with id "+id+" NOT FOUND",new HttpHeaders() , HttpStatus.NOT_FOUND);
			}
		}
		else{
			logger.info("caregiver UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("url "+type +"caregiver with id "+id+" is UNAUTHORIZED");

			return new ResponseEntity<String>("fetching url "+type +"caregiver with id "+id+" is UNAUTHORIZED" , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'CLINIC')")
	@RequestMapping(value = "/caregiver/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCaregiver(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
		
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		HomecareCaregiver caregiver = null;
		
		int idCaregiverFromToken = (Integer) claims.get("id");
		List<String> roleCaregiverFromToken = (List<String>) claims.get("roles");
		
		if(id == idCaregiverFromToken || roleCaregiverFromToken.contains("ROLE_ADMIN") == true || roleCaregiverFromToken.contains("ROLE_CLINIC")== true){
			caregiver = caregiversDbRepository.getOne(id);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String loggedUsername = auth.getName();
			if (caregiver == null){
				logger.info("caregiver NOT FOUND");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("caregiver NOT FOUND");
				return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.NOT_FOUND);
			}
			else if (caregiver.getUsername().equalsIgnoreCase(loggedUsername)){
				//throw new RuntimeException("You cannot delete your account");
				logger.info("You cannot delete your account");
				ApiError message = new ApiError();
				message.setStatus(HttpStatus.OK);
				message.setMessage("You cannot delete your account");
				return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.OK);
				
			}
			else{
				//userRepository.deleteById(id);
				caregiversDbRepository.delete(id);
				logger.info("caregiver sucessfully deleted");
				ApiError message = new ApiError();
				message.setStatus(HttpStatus.OK);
				message.setMessage("caregiver with id "+id+" sucessfully deleted");
				return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.OK);
			}
		}
		else{
			logger.info("caregiver UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("caregiver with id "+ id +" is UNAUTHORIZED");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CAREGIVER', 'CLINIC')")
	@RequestMapping(value = "/caregiver/{id}", method = RequestMethod.PUT )
	public ResponseEntity<Object> updateCaregiver(@PathVariable(value = "id") Long id,@RequestBody HomecareCaregiver homecareCaregiver ,  @RequestHeader(value="Authorization") String token) throws JSONException 
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		//AppUser dataUsers = null;
		
		int idCaregiverFromToken = (Integer) claims.get("id");
		List<String> roleCaregiverFromToken = (List<String>) claims.get("roles");
		
		if(id == idCaregiverFromToken || roleCaregiverFromToken.contains("ROLE_ADMIN")== true || roleCaregiverFromToken.contains("ROLE_CLINIC")== true){
					
			HomecareCaregiver findFirst = caregiversDbRepository.getOne(id);
			HomecareCaregiver cekUsername = caregiversDbRepository.findByUsername(homecareCaregiver.getUsername());
			
			String mes = null ;
			
			
			if(findFirst == null) {
				ApiError message = new ApiError();
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setMessage("caregiver with id "+ id + " NOT_FOUND");
				return new ResponseEntity<Object>(message, new HttpHeaders() ,HttpStatus.NOT_FOUND);
		    }
			
			if(homecareCaregiver.getUsername() != null && cekUsername == null) {findFirst.setUsername(homecareCaregiver.getUsername());}
			if(homecareCaregiver.getEmail() != null) {findFirst.setEmail(homecareCaregiver.getEmail());}
			if(homecareCaregiver.getPassword() != null) {findFirst.setPassword(homecareCaregiver.getPassword());}
			if(homecareCaregiver.getAddress() != null){ findFirst.setAddress(homecareCaregiver.getAddress());}
			if(homecareCaregiver.getDateOfBirth() != null) {findFirst.setDateOfBirth(homecareCaregiver.getDateOfBirth());}
			if(homecareCaregiver.getFrontName() != null) {findFirst.setFrontName(homecareCaregiver.getFrontName());}
			if(homecareCaregiver.getMiddleName() != null) {findFirst.setMiddleName(homecareCaregiver.getMiddleName());}
			if(homecareCaregiver.getLastName() != null) {findFirst.setLastName(homecareCaregiver.getLastName());}
			if(homecareCaregiver.getPhoneNumber() != null){ findFirst.setPhoneNumber(homecareCaregiver.getPhoneNumber());}
			if(homecareCaregiver.getPhotoPath() != null) {findFirst.setPhotoPath(homecareCaregiver.getPhotoPath());}
			if(homecareCaregiver.getFirstRegistrationDate() != null){ findFirst.setFirstRegistrationDate(homecareCaregiver.getFirstRegistrationDate());}
			if(homecareCaregiver.getSipp() != null) {findFirst.setSipp(homecareCaregiver.getSipp());}
			if(homecareCaregiver.getSippUrl() != null) {findFirst.setSippUrl(homecareCaregiver.getSippUrl());}
			if(homecareCaregiver.getRegisterNurseNumber() != null) {findFirst.setRegisterNurseNumber(homecareCaregiver.getRegisterNurseNumber());}
			if(homecareCaregiver.getRegisterNurseNumberUrl() != null) {findFirst.setRegisterNurseNumberUrl(homecareCaregiver.getRegisterNurseNumberUrl());}
			if(homecareCaregiver.getFirstRegistrationDate() != null) {findFirst.setFirstRegistrationDate(homecareCaregiver.getFirstRegistrationDate());}
			if(homecareCaregiver.getEmployeeIdNumber() != null) {findFirst.setEmployeeIdNumber(homecareCaregiver.getEmployeeIdNumber());}
			if(homecareCaregiver.getIdCaregiverStatus() != null) {
				findFirst.setIdCaregiverStatus(homecareCaregiver.getIdCaregiverStatus());
				if(homecareCaregiver.getIdCaregiverStatus().getId() == 1){
					sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(id), "Status Perawat" ,"Selamat Status Anda Telah Aktif");
				}
				else if(homecareCaregiver.getIdCaregiverStatus().getId() == 2){
					sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(id), "Status Perawat" ,"Maaf status anda masih pending");
				}
				else if(homecareCaregiver.getIdCaregiverStatus().getId() == 3){
					sendNotifToCaregiver(caregiversDbRepository.findFcmTokenCaregiverById(id), "Status Perawat" ,"Maaf status anda telah disuspend");
				}
			}
			if(homecareCaregiver.getReligion() != null) {findFirst.setReligion(homecareCaregiver.getReligion());}
			if(homecareCaregiver.getFirebaseIdFacebook() != null){ findFirst.setFirebaseIdFacebook(homecareCaregiver.getFirebaseIdFacebook());}
			if(homecareCaregiver.getFirebaseIdGoogle() != null){ findFirst.setFirebaseIdGoogle(homecareCaregiver.getFirebaseIdGoogle());}
			if(homecareCaregiver.getFirebaseIdByEmail() != null){ findFirst.setFirebaseIdByEmail(homecareCaregiver.getFirebaseIdByEmail());}
			if(homecareCaregiver.getFcmToken() != null){ findFirst.setFcmToken(homecareCaregiver.getFcmToken());}
			if(homecareCaregiver.getGender() != null){findFirst.setGender(homecareCaregiver.getGender());}
			if(cekUsername != null){
				mes = "Succesfully Update caregiver with id "+ findFirst.getId() + ", but username '"+ homecareCaregiver.getUsername()  +"' that you input is already exist";
			}
			else{
				mes = "Succesfully Update caregiver with id "+ findFirst.getId();
			}
			caregiversDbRepository.save(findFirst);
			
			ApiError message = new ApiError();
			message.setStatus(HttpStatus.OK);
			message.setMessage(mes);
			
			return new ResponseEntity<Object>(message, new HttpHeaders() ,HttpStatus.OK);
		}else{
			logger.info("caregiver UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("caregiver with id "+ id +" is UNAUTHORIZED");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','CLINIC')")
	@RequestMapping(value = "/caregiver", method = RequestMethod.POST)
	public ResponseEntity<Object> createCaregiver(@RequestBody HomecareCaregiver homecareCaregiver) 
	{
		if (caregiversDbRepository.findByUsername(homecareCaregiver.getUsername()) != null) {
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.FORBIDDEN);
				error.setMessage("caregiver already exist");
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
		else{
				HomecareCaregiverStatus sts = new HomecareCaregiverStatus();
				Long id = (long) 3; // suspend
				sts.setId(id);
				homecareCaregiver.setIdCaregiverStatus(sts);
				
				HomecareCaregiver process = caregiversDbRepository.save(homecareCaregiver);
				insertSchedule(process);
				return new ResponseEntity<Object>(process, new HttpHeaders() ,HttpStatus.CREATED);
		}
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
	
	
	
}
