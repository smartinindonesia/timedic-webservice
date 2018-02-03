package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.LaboratoryPackage;

public interface LaboratoryPackageDbRepository extends JpaRepository<LaboratoryPackage, Long>{

	@Query("select u from LaboratoryPackage u where LOWER (u.packageName) LIKE LOWER( CONCAT('%',:name,'%'))")
	public List<LaboratoryPackage> findByPackageName(@Param("name") String name);
	
	@Query("select u from LaboratoryPackage u where u.id BETWEEN :x AND :y")
	public List<LaboratoryPackage> findByIdBetween(@Param("x")Long x, @Param("y")Long y);
	
	
}
