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

import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;
import com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlist;
import com.servicetimedic.jwt.repository.HomeCareCaregiverTrxListRepository;


@RestController
@RequestMapping(value = "/api")
public class HomeCareCaregiverTransactionList {
	
	public static final Logger logger = LoggerFactory.getLogger(HomeCareCaregiverTransactionList.class);

	
	@Autowired
	private HomeCareCaregiverTrxListRepository homeCareCaregiverTrxListRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList", method = RequestMethod.POST)
	public ResponseEntity<Object> creatCaregiverTrxList(@RequestBody HomecareTransactionCaregiverlist homecareCaregiverTransactionList) 
	{
		HomecareServiceTransaction idTrx = new HomecareServiceTransaction();
		HomecareTransactionCaregiverlist tempData = new HomecareTransactionCaregiverlist();
		tempData = homecareCaregiverTransactionList;
		idTrx.setId(homecareCaregiverTransactionList.getIdTransaction());
		tempData.setIdServiceTransaction(idTrx);
		//System.out.println(homecareCaregiverTransactionList.getIdServiceTransaction());
		
		HomecareTransactionCaregiverlist data = homeCareCaregiverTrxListRepository.save(tempData);
		logger.info("Succesfully Create one Homecare Caregiver trx List");
		
		return new ResponseEntity<Object>(data, HttpStatus.CREATED);
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList/{id}", method = RequestMethod.GET)
	public ResponseEntity<Object> createCaregiverTrxListById(@PathVariable Long id) 
	{
		HomecareTransactionCaregiverlist trx = homeCareCaregiverTrxListRepository.getOne(id);
		if (trx == null){
			logger.info("Homecare Caregiver trx List is null");
			return new ResponseEntity<Object>("Homecare Caregiver trx List is null",HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Caregiver trx List by id "+id);
			return new ResponseEntity<Object>(trx, HttpStatus.OK);
		}
	}
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCaregiverTrxListById(@PathVariable Long id) 
	{
		HomecareTransactionCaregiverlist trx = homeCareCaregiverTrxListRepository.getOne(id);
		if (trx == null){
			logger.info("Homecare Caregiver trx List is null");
			return new ResponseEntity<Object>("Homecare Caregiver trx List is null",HttpStatus.NO_CONTENT);
		}
		else{
			homeCareCaregiverTrxListRepository.delete(id);
			logger.info("Succesfully delete Caregiver trx List by id "+id);
			return new ResponseEntity<Object>("Succesfully delete Caregiver trx List by id "+id, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN', 'USER', 'ROLE_CLINIC')")
	@RequestMapping(value = "/caregiverTrxList/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> updateCaregiverTrxListById(@PathVariable Long id, @RequestBody HomecareTransactionCaregiverlist homecareTransactionCaregiverlist) 
	{
		HomecareTransactionCaregiverlist trx = homeCareCaregiverTrxListRepository.getOne(id);
		HomecareServiceTransaction idTrx = new HomecareServiceTransaction();
		
		if(homecareTransactionCaregiverlist.getCaregiverName() != null){trx.setCaregiverName(homecareTransactionCaregiverlist.getCaregiverName());}
		if(homecareTransactionCaregiverlist.getRegisterNurseNumber() != null){trx.setRegisterNurseNumber(homecareTransactionCaregiverlist.getRegisterNurseNumber());}
		if(homecareTransactionCaregiverlist.getIdCaregiver() != null){trx.setIdCaregiver(homecareTransactionCaregiverlist.getIdCaregiver());}
		if(homecareTransactionCaregiverlist.getIdServiceTransaction() != null){trx.setIdServiceTransaction(homecareTransactionCaregiverlist.getIdServiceTransaction());}
		if(homecareTransactionCaregiverlist.getIdHomecareClinic() != null){trx.setIdHomecareClinic(homecareTransactionCaregiverlist.getIdHomecareClinic());}
		if(homecareTransactionCaregiverlist.getRateStatus() != null){
			System.out.println(homecareTransactionCaregiverlist.getRateStatus());
			trx.setRateStatus(homecareTransactionCaregiverlist.getRateStatus());}
		if(homecareTransactionCaregiverlist.getIdTransaction() != null ){
			trx.setIdTransaction(homecareTransactionCaregiverlist.getIdTransaction());
			idTrx.setId(homecareTransactionCaregiverlist.getIdTransaction());
			trx.setIdServiceTransaction(idTrx);
		}
		
		if(trx!=null){
			homeCareCaregiverTrxListRepository.save(trx);
			return new ResponseEntity<Object>("Succesfully update Caregiver trx List by id "+id, HttpStatus.OK);
		}else{
			return new ResponseEntity<Object>("Failed update Caregiver trx List", HttpStatus.NOT_FOUND);
		}
	}
	
}
