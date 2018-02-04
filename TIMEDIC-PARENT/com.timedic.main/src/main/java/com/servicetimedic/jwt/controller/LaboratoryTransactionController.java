package com.servicetimedic.jwt.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.servicetimedic.jwt.domain.december.LaboratoryServiceTransaction;
import com.servicetimedic.jwt.repository.LaboratoryTransactionDbRepository;


@RestController
@RequestMapping(value = "/api/lab")
public class LaboratoryTransactionController {

	public static final Logger logger = LoggerFactory.getLogger(LaboratoryServiceController.class);
	
	@Autowired
	private LaboratoryTransactionDbRepository laboratoryTransactionDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public List<LaboratoryServiceTransaction> getAllLabTransaction() {
		logger.info("Fetching all Lab transaction");
		return laboratoryTransactionDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<LaboratoryServiceTransaction> createLabTransaction(@RequestBody LaboratoryServiceTransaction laboratoryServiceTrx) {
		LaboratoryServiceTransaction laboratoryServiceTransaction = laboratoryTransactionDbRepository.save(laboratoryServiceTrx);
		logger.info("Create one Lab transaction");
		return new ResponseEntity<LaboratoryServiceTransaction>(laboratoryServiceTransaction, HttpStatus.CREATED);
	}
}
