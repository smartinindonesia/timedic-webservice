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
import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.domain.december.NumberOfRows;
import com.servicetimedic.jwt.repository.UserDbRepository;

@RestController
@RequestMapping(value = "/api")
public class AppUserController {
	
	public static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
	
	@Autowired
	private UserDbRepository userRepository;
	
	
	private Pageable createPageRequest(int page, int size, String sort, String field) {
		Direction direction;
		if(sort.equals("ASC")){direction = Sort.Direction.ASC;}
		else{direction = Sort.Direction.DESC;}
		return new PageRequest(page, size, direction, field);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/userByField", method = RequestMethod.GET)
	public AppUser getUserByField(@RequestParam("searchField") String searchField, @RequestParam("value") String value) {
		AppUser data = null;
		if(searchField.equals("username")){
			data = userRepository.findByUsername(value);
		}
		else if(searchField.equals("email")){
			data = userRepository.findByEmail(value);
		}
		else if(searchField.equals("phonenumber")){
			data = userRepository.findByPhoneNumber(value);
		}
		return data;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/usersWithPaginationByField", method = RequestMethod.GET)
	public List<Object> getAllUsersWithPaginationByField(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField, @RequestParam("searchField") String searchField, @RequestParam("value") String value) {
		
		List<AppUser> data = null;
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(searchField.equals("fistname")){
			data = userRepository.findUserByfrontName(value, createPageRequest(page, size, sort, sortField));
			rowCount = userRepository.findUserByfrontNameGetCount(value).size();
		}
		else if(searchField.equals("middlename")){
			data = userRepository.findUserBymiddleName(value, createPageRequest(page, size, sort, sortField));
			rowCount = userRepository.findUserBymiddleNameCount(value).size();
		}
		else if(searchField.equals("lastname")){
			data = userRepository.findUserBylastName(value, createPageRequest(page, size, sort, sortField));
			rowCount = userRepository.findUserBylastNameCount(value).size();
		}
		
		logger.info("Fetching User with "+ searchField +" order by " + sortField + " " + sort);
		
		List<Object> list = new ArrayList<Object>();
		rows.setNumOfRows(rowCount);
		list.add(data);
		list.add(rows);
		
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/usersWithPaginationByFieldGetCount", method = RequestMethod.GET)
	public NumberOfRows getAllUsersWithPaginationByFieldGetCount(@RequestParam("searchField") String searchField, @RequestParam("value") String value) {
	
		NumberOfRows rows = new NumberOfRows();
		int rowCount = 0 ;
		
		if(searchField.equals("fistname")){
			rowCount = userRepository.findUserByfrontNameGetCount(value).size();
		}
		else if(searchField.equals("middlename")){
			rowCount = userRepository.findUserBymiddleNameCount(value).size();
		}
		else if(searchField.equals("lastname")){
			rowCount = userRepository.findUserBylastNameCount(value).size();
		}	
		rows.setNumOfRows(rowCount);
		return rows;
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/userswithpagination", method = RequestMethod.GET)
	public List<Object> getAllUsersWithPagination(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("sort") String sort, @RequestParam("sortField") String sortField) {
		
		List<AppUser> data = userRepository.findAllUsers(createPageRequest(page, size, sort, sortField));
		logger.info("Fetching All Users Details with pagination order by " + sortField + " " + sort);
		
		NumberOfRows rows = new NumberOfRows();
		rows.setNumOfRows(userRepository.findAll().size());
		List<Object> list = new ArrayList<Object>();
		list.add(data);
		list.add(rows);
		
		return list;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'SUPERADMIN', 'USER')")
	@RequestMapping(value = "/userswithpaginationGetCount", method = RequestMethod.GET)
	public NumberOfRows getAllUsersWithPaginationGetCount() {
	
		NumberOfRows rows = new NumberOfRows();
		rows.setNumOfRows(userRepository.findAll().size());
	
		return rows;
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserById(@PathVariable Long id, @RequestHeader(value="Authorization") String token)
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		AppUser appUser = null;
		
		int idUserFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		if(id == idUserFromToken || roleUserFromToken.contains("ROLE_ADMIN") == true){
			appUser = userRepository.getOne(id);	
			
			if (appUser != null){
				logger.info("fetching user with id " + appUser.getId());
				return new ResponseEntity<Object>(appUser, new HttpHeaders() ,HttpStatus.OK);
			}
			else{
				logger.info("user not found");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("users NOT_FOUND");
				return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.NOT_FOUND);
			}
		}
		else{
			logger.info("users UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("user with id "+ id +" is UNAUTHORIZED");

			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
		
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
		
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		AppUser appUser = null;
		
		int idUserFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		if(id == idUserFromToken || roleUserFromToken.contains("ROLE_ADMIN") == true){
			appUser = userRepository.getOne(id);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String loggedUsername = auth.getName();
			if (appUser == null){
				logger.info("user NOT FOUND");
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.NOT_FOUND);
				error.setMessage("user NOT FOUND");
				return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.NOT_FOUND);
			}
			else if (appUser.getUsername().equalsIgnoreCase(loggedUsername)){
				//throw new RuntimeException("You cannot delete your account");
				logger.info("You cannot delete your account");
				ApiError message = new ApiError();
				message.setStatus(HttpStatus.OK);
				message.setMessage("You cannot delete your account");
				return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.OK);
				
			}
			else{
				//userRepository.deleteById(id);
				userRepository.delete(id);
				logger.info("user sucessfully deleted");
				ApiError message = new ApiError();
				message.setStatus(HttpStatus.OK);
				message.setMessage("user with id "+id+" sucessfully deleted");
				return new ResponseEntity<Object>(message , new HttpHeaders() ,HttpStatus.OK);
			}
		}
		else{
			logger.info("user UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("user with id "+ id +" is UNAUTHORIZED");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER')")
	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT )
	public ResponseEntity<Object> updateUser(@PathVariable(value = "id") Long id,@RequestBody AppUser appUser ,  @RequestHeader(value="Authorization") String token) 
	{
		Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
		//AppUser dataUsers = null;
		
		int idUserFromToken = (Integer) claims.get("id");
		List<String> roleUserFromToken = (List<String>) claims.get("roles");
		
		if(id == idUserFromToken || roleUserFromToken.contains("ROLE_ADMIN") == true){
					
			AppUser findFirst = userRepository.getOne(id);
			AppUser cekUsername = userRepository.findByUsername(appUser.getUsername());
			
			String mes = null ;
			
			
			if(findFirst == null) {
				ApiError message = new ApiError();
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setMessage("user with id "+ id + " NOT_FOUND");
				return new ResponseEntity<Object>(message, new HttpHeaders() ,HttpStatus.NOT_FOUND);
		    }
			
			if(appUser.getUsername() != null && cekUsername == null) findFirst.setUsername(appUser.getUsername());
			if(appUser.getEmail() != null) findFirst.setEmail(appUser.getEmail());
			if(appUser.getPassword() != null) findFirst.setPassword(appUser.getPassword());
			if(appUser.getAddress() != null) findFirst.setAddress(appUser.getAddress());
			if(appUser.getDateBirth() != null) findFirst.setDateBirth(appUser.getDateBirth());
			if(appUser.getFrontName() != null) findFirst.setFrontName(appUser.getFrontName());
			if(appUser.getMiddleName() != null) findFirst.setMiddleName(appUser.getMiddleName());
			if(appUser.getLastName() != null) findFirst.setLastName(appUser.getLastName());
			if(appUser.getPhoneNumber() != null) findFirst.setPhoneNumber(appUser.getPhoneNumber());
			if(appUser.getPhotoPath() != null) findFirst.setPhotoPath(appUser.getPhotoPath());
			if(appUser.getFirstRegistrationDate() != null) findFirst.setFirstRegistrationDate(appUser.getFirstRegistrationDate());
			if(appUser.getLatitude() != null) findFirst.setLatitude(appUser.getLatitude());
			if(appUser.getLongitude() != null) findFirst.setLongitude(appUser.getLongitude());
			
			if(cekUsername != null){
				mes = "Succesfully Update use with id "+ findFirst.getId() + ", but username '"+ appUser.getUsername()  +"' that you input is already exist";
			}
			else{
				mes = "Succesfully Update user with id "+ findFirst.getId();
			}
			userRepository.save(findFirst);
			
			ApiError message = new ApiError();
			message.setStatus(HttpStatus.OK);
			message.setMessage(mes);
			
			return new ResponseEntity<Object>(message, new HttpHeaders() ,HttpStatus.OK);
		}else{
			logger.info("user UNAUTHORIZED");
			ApiError error = new ApiError();
			error.setStatus(HttpStatus.UNAUTHORIZED);
			error.setMessage("user with id "+ id +" is UNAUTHORIZED");
			return new ResponseEntity<Object>(error , new HttpHeaders() ,HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public ResponseEntity<Object> createUser(@RequestBody AppUser appUser) 
	{
		if (userRepository.findByUsername(appUser.getUsername()) != null) {
				ApiError error = new ApiError();
				error.setStatus(HttpStatus.OK);
				error.setMessage("user already exist");
				return new ResponseEntity<Object>(error ,new HttpHeaders() , HttpStatus.OK);
		}
		else{
			AppUser process = userRepository.save(appUser);		
			return new ResponseEntity<Object>(process, new HttpHeaders() ,HttpStatus.CREATED);
		}
	}
	
}
