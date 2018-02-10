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

import com.servicetimedic.jwt.domain.december.LaboratorySelectedPackageTransaction;
import com.servicetimedic.jwt.domain.december.LaboratorySelectedServiceTransaction;
import com.servicetimedic.jwt.domain.december.LaboratoryServiceTransaction;
import com.servicetimedic.jwt.repository.LaboratoryPackageSelectedDbRepository;
import com.servicetimedic.jwt.repository.LaboratoryServiceSelectedDbRepository;
import com.servicetimedic.jwt.repository.LaboratoryTransactionDbRepository;


@RestController
@RequestMapping(value = "/api/lab")
public class LaboratoryTransactionController {

	public static final Logger logger = LoggerFactory.getLogger(LaboratoryServiceController.class);
	
	@Autowired
	private LaboratoryTransactionDbRepository laboratoryTransactionDbRepository;
	
	@Autowired
	private LaboratoryPackageSelectedDbRepository laboratoryPackageSelectedDbRepository;
	
	@Autowired
	private LaboratoryServiceSelectedDbRepository laboratoryServiceSelectedDbRepository;
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/transaction", method = RequestMethod.GET)
	public List<LaboratoryServiceTransaction> getAllLabTransaction() {
		logger.info("Fetching all Lab transaction");
		return laboratoryTransactionDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transaction/{id}", method = RequestMethod.GET)
	public ResponseEntity<LaboratoryServiceTransaction> getTransactionsLaboratoryById(@PathVariable Long id)
	{
		LaboratoryServiceTransaction laboratoryServiceTransaction = laboratoryTransactionDbRepository.getOne(id);
		if (laboratoryServiceTransaction == null){
			logger.info("Transactions laboratory is null");
			return new ResponseEntity<LaboratoryServiceTransaction>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching Transactions laboratory by id "+id);
			return new ResponseEntity<LaboratoryServiceTransaction>(laboratoryServiceTransaction, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/transaction", method = RequestMethod.POST)
	public ResponseEntity<String> createLabTransaction(@RequestBody LaboratoryServiceTransaction laboratoryServiceTrx) {
		
		LaboratoryServiceTransaction laboratoryServiceTransaction = laboratoryTransactionDbRepository.save(laboratoryServiceTrx);
		
		List<LaboratorySelectedPackageTransaction> listPackages;
		
		List<LaboratorySelectedServiceTransaction> listServices;
		
		listPackages = (List<LaboratorySelectedPackageTransaction>) laboratoryServiceTransaction.getLaboratorySelectedPackageTransactionCollection();

		listServices = (List<LaboratorySelectedServiceTransaction>) laboratoryServiceTransaction.getLaboratorySelectedServiceTransactionCollection();
		
		LaboratorySelectedPackageTransaction packageTrx;
		
		LaboratorySelectedServiceTransaction serviceTrx;
		
		if(listPackages != null){
			for(LaboratorySelectedPackageTransaction data : listPackages){
				packageTrx = new LaboratorySelectedPackageTransaction();
				packageTrx.setIdLaboratoryPackage(data.getIdLaboratoryPackage());
				packageTrx.setIdLaboratoryServiceTransaction(laboratoryServiceTransaction);
				laboratoryPackageSelectedDbRepository.save(packageTrx);
			}
		}
		
		if(listServices != null){
			for (LaboratorySelectedServiceTransaction data : listServices){
				serviceTrx = new LaboratorySelectedServiceTransaction();
				serviceTrx.setIdLaboratoryService(data.getIdLaboratoryService());
				serviceTrx.setIdLaboratoryServiceTransaction(laboratoryServiceTransaction);
				laboratoryServiceSelectedDbRepository.save(serviceTrx);
			}
		}
		
		logger.info("Create one Lab transaction with id " + laboratoryServiceTransaction.getId());
		return new ResponseEntity<String>("Thank You, Your laboratory order has been recorded in timedic system with id "+laboratoryServiceTransaction.getId(), HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/transaction/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLaboratoryServicesTransaction(@PathVariable Long id) {
		LaboratoryServiceTransaction labservicesTrx = laboratoryTransactionDbRepository.getOne(id);
		if (labservicesTrx != null) {
			laboratoryTransactionDbRepository.deleteById(id);
			return new ResponseEntity<String>("Succesfully delete Laboratory Services Transaction with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed delete Laboratory Services Transaction", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/orderactive/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryServiceTransaction>> getOrderactiveLaboratoryById(@PathVariable Long id)
	{
		List<LaboratoryServiceTransaction> laboratoryServiceTransaction = laboratoryTransactionDbRepository.findOrderActiveLaboratoryByIdUser(id);
		if (laboratoryServiceTransaction == null){
			logger.info("orderactive laboratory is null");
			return new ResponseEntity<List<LaboratoryServiceTransaction>>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching orderactive laboratory by id "+id);
			return new ResponseEntity<List<LaboratoryServiceTransaction>>(laboratoryServiceTransaction, HttpStatus.OK);
		}
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/history/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryServiceTransaction>> getHistoryTrxLaboratoryById(@PathVariable Long id)
	{
		List<LaboratoryServiceTransaction> laboratoryServiceTransaction = laboratoryTransactionDbRepository.findLaboratoryTrxByIdUser(id);
		if (laboratoryServiceTransaction == null){
			logger.info("history Laboratory is null");
			return new ResponseEntity<List<LaboratoryServiceTransaction>>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching history Laboratory by id "+id);
			return new ResponseEntity<List<LaboratoryServiceTransaction>>(laboratoryServiceTransaction, HttpStatus.OK);
		}
	}
	
}
