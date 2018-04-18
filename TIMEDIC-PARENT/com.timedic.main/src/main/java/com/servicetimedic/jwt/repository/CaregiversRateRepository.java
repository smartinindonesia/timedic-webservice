package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverRate;

public interface CaregiversRateRepository extends JpaRepository<HomecareCaregiverRate, Long>{

	@Query("select c from HomecareCaregiverRate c where c.idHomecareCaregiver = :idCaregiver")
	public List<HomecareCaregiverRate> findCaregiverRateByIdCaregiver(@Param("idCaregiver")Long idCaregiver);
	
}
