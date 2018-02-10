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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.servicetimedic.jwt.domain.december.LaboratoryPackage;
import com.servicetimedic.jwt.repository.HomeCarePatientDbRepository;
import com.servicetimedic.jwt.repository.LaboratoryPackageDbRepository;

@RestController
@RequestMapping(value = "/api/lab")
public class LaboratoryPackageController {
	
	public static final Logger logger = LoggerFactory.getLogger(LaboratoryPackageController.class);
	
	@Autowired
	private LaboratoryPackageDbRepository laboratoryPackageDbRepository;
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/package", method = RequestMethod.GET)
	public List<LaboratoryPackage> getAllPackage() {
		logger.info("Fetching all Lab Package");
		return laboratoryPackageDbRepository.findAll();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/package/findByname", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryPackage>> getLabPackageByName(@RequestParam("name") String name) {
		logger.info("Fetching Lab Package by name");
		return new ResponseEntity<List<LaboratoryPackage>>(laboratoryPackageDbRepository.findByPackageName(name), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN','USER')")
	@RequestMapping(value = "/package/findAllBetween", method = RequestMethod.GET)
	public ResponseEntity<List<LaboratoryPackage>> getLabAllPackageBetween(@RequestParam("start") Long start, @RequestParam("end") Long end) {
		logger.info("Fetching All Lab Package between");
		return new ResponseEntity<List<LaboratoryPackage>>(laboratoryPackageDbRepository.findByIdBetween(start, end), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/package", method = RequestMethod.POST)
	public ResponseEntity<LaboratoryPackage> createLaboratoryPackage(@RequestBody LaboratoryPackage laboratoryPackage) {
		LaboratoryPackage labPackage = laboratoryPackageDbRepository.save(laboratoryPackage);
		logger.info("Create one Lab Package");
		return new ResponseEntity<LaboratoryPackage>(labPackage, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/package/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteLaboratoryPackage(@PathVariable Long id) {
		LaboratoryPackage labPackage = laboratoryPackageDbRepository.getOne(id);
		if (labPackage != null) {
			laboratoryPackageDbRepository.deleteById(id);
			return new ResponseEntity<String>("Succesfully delete Laboratory Package with id "+id, HttpStatus.OK);
		}
		else{
			return new ResponseEntity<String>("Failed delete Laboratory Package", HttpStatus.FAILED_DEPENDENCY);
		}
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','SUPERADMIN')")
	@RequestMapping(value = "/package/{id}", method = RequestMethod.PUT )
	public ResponseEntity<String> updateLaboratoryPackage(@PathVariable(value = "id") Long id,@RequestBody LaboratoryPackage laboratoryPackage) {	
		LaboratoryPackage labPackage = laboratoryPackageDbRepository.getOne(id);	
		if(labPackage == null) {
			return new ResponseEntity<String>("Not Found Laboratory Package", HttpStatus.NOT_FOUND);
	    }
		else{
			labPackage.setPackageDescription(laboratoryPackage.getPackageDescription());
			labPackage.setPrice(laboratoryPackage.getPrice());
			labPackage.setPackageName(laboratoryPackage.getPackageName());
			labPackage.setPackageCode(laboratoryPackage.getPackageCode());
			labPackage.setUriPackageIcon(laboratoryPackage.getUriPackageIcon());
			
			laboratoryPackageDbRepository.save(labPackage);
			return new ResponseEntity<String>("Succesfully Update Laboratory Package with id "+id, HttpStatus.OK);
		}
	}
	
	
	
}
