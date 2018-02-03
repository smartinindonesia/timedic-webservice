package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.LaboratoryService;

public interface LaboratoryServiceDbRepository extends JpaRepository<LaboratoryService, Long>{

	@Query("select u from LaboratoryService u where LOWER (u.serviceName) LIKE LOWER( CONCAT('%',:name,'%'))")
	public List<LaboratoryService> findByServiceName(@Param("name") String name);
	
	@Query("select u from LaboratoryService u where u.id BETWEEN :x AND :y")
	public List<LaboratoryService> findByIdBetween(@Param("x")Long x, @Param("y")Long y);
	
}
