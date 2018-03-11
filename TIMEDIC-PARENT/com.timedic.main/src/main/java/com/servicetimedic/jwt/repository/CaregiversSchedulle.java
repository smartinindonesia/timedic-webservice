package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;

public interface CaregiversSchedulle extends JpaRepository<HomecareCaregiverSchedule, Long> {
	

	@Query("select u from HomecareCaregiverSchedule u where u.idHomecareCaregiver.id = :idCaregiver")
	public List<HomecareCaregiverSchedule> findByIdHomecareCaregiver(@Param("idCaregiver") Long idCaregiver);
	
}
