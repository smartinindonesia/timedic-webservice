package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.LaboratoryServicePackage;

public interface LaboratoryPackageServiceDbRepository extends JpaRepository<LaboratoryServicePackage, Long>{
	
	@Query("select u from "
			+ "LaboratoryServicePackage u where u.idLaboratoryPackage.id = :idPackage")
	public List<LaboratoryServicePackage> findServicesByPackageId(@Param("idPackage") Long idPackage);
	
}
