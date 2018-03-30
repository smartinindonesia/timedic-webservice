package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.HomecareCaregiver;

public interface CaregiversDbRepository extends JpaRepository <HomecareCaregiver, Long>{

	public HomecareCaregiver findByUsername(String username);
	
	public HomecareCaregiver findBySipp(String sipp);
	
	public HomecareCaregiver findByRegisterNurseNumber(String no);
	
	public HomecareCaregiver findByEmail(String email);
	
	public HomecareCaregiver findByPhoneNumber(String phone);
	
	public HomecareCaregiver findByFirebaseIdFacebook(String idFirebase);
	
	public HomecareCaregiver findByFirebaseIdGoogle(String idFirebase);
	
	@Query( "select f from HomecareCaregiver f" )
	public List<HomecareCaregiver> findAllCaregiver(Pageable pageable);
	
	@Query("select f from HomecareCaregiver f where LOWER (f.frontName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverByfrontName(@Param("value")String value, Pageable pageable);
	
	@Query("select f from HomecareCaregiver f where LOWER (f.frontName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverByfrontNameGetCount(@Param("value")String value);
	
	@Query("select f from HomecareCaregiver f where LOWER (f.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBymiddleName(@Param("value")String value, Pageable pageable);
	
	@Query("select f from HomecareCaregiver f where LOWER (f.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBymiddleNameGetCount(@Param("value")String value);
	
	@Query("select f from HomecareCaregiver f where LOWER (f.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBylastName(@Param("value")String value, Pageable pageable);
	
	@Query("select f from HomecareCaregiver f where LOWER (f.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBylastNameGetCount(@Param("value")String value);
	
}
