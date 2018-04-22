package com.servicetimedic.jwt.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.HomecareCaregiverSchedule;

public interface CaregiversSchedulle extends JpaRepository<HomecareCaregiverSchedule, Long> {
	
	@Query("select u.day, u.status, u.id from HomecareCaregiverSchedule u where u.idHomecareCaregiver.id = :idCaregiver")
	public List<HomecareCaregiverSchedule> findByIdHomecareCaregiverStatusAndDay(@Param("idCaregiver") Long idCaregiver);
	
	@Query("select u from HomecareCaregiverSchedule u where u.status = true and u.day like LOWER( CONCAT('%',:day,'%')) and ((:time between u.startTime and u.endTime) or (:time between u.startTime2 and u.endTime2))")
	public List<HomecareCaregiverSchedule> findByTime(@Param("time") Date time, @Param("day") String day);
	
	@Query("select u from HomecareCaregiverSchedule u where u.idHomecareCaregiver.id = :idCaregiver and u.day = :day")
	public HomecareCaregiverSchedule cekDataCaregiverSchedule(@Param("idCaregiver") Long idCaregiver, @Param("day") String day);
	
	@Query("select u from HomecareCaregiverSchedule u where u.idHomecareCaregiver.id = :idCaregiver")
	public List<HomecareCaregiverSchedule> findByIdHomecareCaregiver(@Param("idCaregiver") Long id);
	
	@Query("update HomecareCaregiverSchedule u set u.status = :sts  where u.idHomecareCaregiver.id = :idCaregiver and u.day = :day")
	public void updateCaregiverSchedule(@Param("sts") boolean sts , @Param("idCaregiver") Long idCaregiver , @Param("day") String day);
	
	
}
