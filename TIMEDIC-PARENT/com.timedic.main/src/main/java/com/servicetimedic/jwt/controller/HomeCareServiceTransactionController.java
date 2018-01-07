package com.servicetimedic.jwt.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;
import com.servicetimedic.jwt.repository.HomecareSeriveTransactionsDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeCareServiceTransactionController {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCareServiceTransactionController.class);
	
	@Autowired
	private HomecareSeriveTransactionsDbRepository homecareSeriveTransactionsDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transactions/homecare", method = RequestMethod.GET)
	public List<HomecareServiceTransaction> getAllTransactionsHomecare() 
	{
		logger.info("Fetching All Transactions Homecare  Details");
		return homecareSeriveTransactionsDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transactions/homecare/{id}", method = RequestMethod.GET)
	public ResponseEntity<HomecareServiceTransaction> getTransactionsHomecareById(@PathVariable Long id)
	{
		HomecareServiceTransaction homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findOne(id);
		if (homecareServiceTransaction == null)
		{
			logger.info("Transactions Homecare is null");
			return new ResponseEntity<HomecareServiceTransaction>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("fetching Transactions Homecare by id "+id);
			return new ResponseEntity<HomecareServiceTransaction>(homecareServiceTransaction, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transactions/homecare/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTransactionsHomecare(@PathVariable Long id) {
		HomecareServiceTransaction homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findOne(id);
		
		if (homecareServiceTransaction != null) {
			homecareSeriveTransactionsDbRepository.delete(id);
			return new ResponseEntity<String>("Succesfully delete transactions homecare with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed transactions homecare", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transactions/homecare/", method = RequestMethod.POST)
	public ResponseEntity<HomecareServiceTransaction> createTransactionsHomecare(@RequestBody HomecareServiceTransaction homecareService) 
	{
		HomecareServiceTransaction process = homecareSeriveTransactionsDbRepository.save(homecareService);		
		return new ResponseEntity<HomecareServiceTransaction>(process, HttpStatus.CREATED);
	}
	

}
