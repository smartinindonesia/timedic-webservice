package com.servicetimedic.jwt.controller;

import java.util.ArrayList;
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

import com.servicetimedic.jwt.domain.december.LaboratoryPackage;
import com.servicetimedic.jwt.domain.december.LaboratoryServicePackage;
import com.servicetimedic.jwt.domain.december.LaboratoryService;
import com.servicetimedic.jwt.repository.LaboratoryPackageServiceDbRepository;


@RestController
@RequestMapping(value = "/api/lab")
public class LaboratoryPackageServiceController {
	
	public static final Logger logger = LoggerFactory.getLogger(LaboratoryPackageController.class);
	
	@Autowired
	private LaboratoryPackageServiceDbRepository laboratoryPackageServiceDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/packageservice", method = RequestMethod.GET)
	public List<LaboratoryServicePackage> getAllPackage() {
		logger.info("Fetching all Lab PackageService Tabel");
		return laboratoryPackageServiceDbRepository.findAll();
	}
	
	@SuppressWarnings("unused")
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/findServiceByPackageId/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryService>> getServicesByPackageId(@PathVariable Long id)
	{
		List<LaboratoryServicePackage> laboratoryServicePackage = laboratoryPackageServiceDbRepository.findServicesByPackageId(id);
		List<LaboratoryService> laboratoryService = new ArrayList<LaboratoryService>();
		
		for(LaboratoryServicePackage k : laboratoryServicePackage){
			laboratoryService.add(k.getIdLaboratoryService());
		}
		
		if (laboratoryService == null){
			logger.info("laboratoryServicePackage is null");
			return new ResponseEntity<List<LaboratoryService>>(HttpStatus.NO_CONTENT);
		}
		else{
			logger.info("fetching laboratoryServicePackage by id "+id);
			return new ResponseEntity<List<LaboratoryService>>(laboratoryService, HttpStatus.OK);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/packageservice", method = RequestMethod.POST)
	public ResponseEntity<LaboratoryServicePackage> createLaboratoryPackageService(@RequestBody LaboratoryServicePackage laboratoryServicePackage) {
		LaboratoryServicePackage labPackage = laboratoryPackageServiceDbRepository.save(laboratoryServicePackage);
		logger.info("Create one Lab PackageService");
		return new ResponseEntity<LaboratoryServicePackage>(labPackage, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/packageservice/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLaboratoryPackageService(@PathVariable Long id) {
		LaboratoryServicePackage labPackageService = laboratoryPackageServiceDbRepository.getOne(id);
		if (labPackageService != null) {
			//laboratoryPackageServiceDbRepository.deleteById(id);
			laboratoryPackageServiceDbRepository.delete(id);
			return new ResponseEntity<String>("Succesfully delete Laboratory PackageService with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed delete Laboratory PackageService", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/packageservice/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updateLaboratoryPackageService(@PathVariable(value = "id") Long id,@RequestBody LaboratoryServicePackage laboratoryServicePackage) {	
		LaboratoryServicePackage labPackageService = laboratoryPackageServiceDbRepository.getOne(id);	
		if(labPackageService == null) {
			return new ResponseEntity<String>("Not Found Laboratory PackageService", HttpStatus.NOT_FOUND);
	    }
		else{
			labPackageService.setIdLaboratoryPackage(laboratoryServicePackage.getIdLaboratoryPackage());
			labPackageService.setIdLaboratoryService(laboratoryServicePackage.getIdLaboratoryService());
			
			laboratoryPackageServiceDbRepository.save(labPackageService);
			
			return new ResponseEntity<String>("Succesfully Update Laboratory PackageService with id "+id, HttpStatus.OK);
		}
	}
	
}
