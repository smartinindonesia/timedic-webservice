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

import com.servicetimedic.jwt.domain.december.HomecareAssessmentRecord;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;
import com.servicetimedic.jwt.domain.posgresql.HomecareAssestmentRecord;
import com.servicetimedic.jwt.repository.HomeCareAssessmentRecordDbRepository;
import com.servicetimedic.jwt.repository.HomeCareSeriveTransactionsDbRepository;

@RestController
@RequestMapping(value = "/api")
public class HomeCareServiceTransactionController {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCareServiceTransactionController.class);
	
	@Autowired
	private HomeCareSeriveTransactionsDbRepository homecareSeriveTransactionsDbRepository;
	
	@Autowired
	private HomeCareAssessmentRecordDbRepository homeCareAssessmentRecordDbRepository;
	
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
		HomecareServiceTransaction homecareServiceTransaction = homecareSeriveTransactionsDbRepository.getOne(id);
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
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/transactions/homecare/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteTransactionsHomecare(@PathVariable Long id) {
		HomecareServiceTransaction homecareServiceTransaction = homecareSeriveTransactionsDbRepository.getOne(id);
		
		if (homecareServiceTransaction != null) {
			homecareSeriveTransactionsDbRepository.deleteById(id);
			return new ResponseEntity<String>("Succesfully delete transactions homecare with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed transactions homecare", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transactions/homecare/", method = RequestMethod.POST)
	public ResponseEntity<String> createTransactionsHomecare(@RequestBody HomecareServiceTransaction homecareService) 
	{
		HomecareServiceTransaction process = homecareSeriveTransactionsDbRepository.save(homecareService);
		HomecareServiceTransaction newID = new HomecareServiceTransaction();
		newID.setId(process.getId());
		List<HomecareAssessmentRecord> assessment = homecareService.getHomecareAssessmentRecordList();
		for(HomecareAssessmentRecord data : assessment)
		{
			data.setIdServiceTransaction(newID);
		}
		
		List<HomecareAssessmentRecord> data = homecareService.getHomecareAssessmentRecordList();
		
		for(HomecareAssessmentRecord x: data){
			homeCareAssessmentRecordDbRepository.save(x);
		}
		
		return new ResponseEntity<String>("Thank You, Your order has been recorded in our system", HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/orderactive/homecare/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HomecareServiceTransaction>> getOrderactiveHomecareById(@PathVariable Long id)
	{
		
		List<HomecareServiceTransaction> homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findOrderActiveHomecareByIdUser(id);
		//List<HomecareServiceTransaction> homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findByHomecareByIdUserForAndroid(id);
		if (homecareServiceTransaction == null)
		{
			logger.info("orderactive Homecare is null");
			return new ResponseEntity<List<HomecareServiceTransaction>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("fetching orderactive Homecare by id "+id);
			return new ResponseEntity<List<HomecareServiceTransaction>>(homecareServiceTransaction, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/history/homecare/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<HomecareServiceTransaction>> getHistoryTrxHomecareById(@PathVariable Long id)
	{
		
		List<HomecareServiceTransaction> homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findHomecareTrxByIdUser(id);
		//List<HomecareServiceTransaction> homecareServiceTransaction = homecareSeriveTransactionsDbRepository.findByHomecareByIdUserForAndroid(id);
		if (homecareServiceTransaction == null)
		{
			logger.info("orderactive Homecare is null");
			return new ResponseEntity<List<HomecareServiceTransaction>>(HttpStatus.NO_CONTENT);
		}
		else
		{
			logger.info("fetching orderactive Homecare by id "+id);
			return new ResponseEntity<List<HomecareServiceTransaction>>(homecareServiceTransaction, HttpStatus.OK);
		}
	}
	
	
	

}
