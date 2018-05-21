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
	
	public HomecareCaregiver findByFirebaseIdByEmail(String idFirebase);
	
	public HomecareCaregiver findByCaregiverCode(String code);
	
	@Query( "select c from HomecareCaregiver c" )
	public List<HomecareCaregiver> findAllCaregiver(Pageable pageable);
	
	@Query("select c from HomecareCaregiver c where LOWER (c.frontName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverByfrontName(@Param("value")String value, Pageable pageable);
	
	@Query("select c from HomecareCaregiver c where LOWER (c.frontName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverByfrontNameGetCount(@Param("value")String value);
	
	@Query("select c from HomecareCaregiver c where LOWER (c.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBymiddleName(@Param("value")String value, Pageable pageable);
	
	@Query("select c from HomecareCaregiver c where LOWER (c.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBymiddleNameGetCount(@Param("value")String value);
	
	@Query("select c from HomecareCaregiver c where LOWER (c.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBylastName(@Param("value")String value, Pageable pageable);
	
	@Query("select c from HomecareCaregiver c where LOWER (c.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareCaregiver> findCaregiverBylastNameGetCount(@Param("value")String value);
	
	@Query("SELECT coalesce(max(ch.id), 0) FROM HomecareCaregiver ch")
	Long getMaxId();
	
	@Query("select c.fcmToken from HomecareCaregiver c where c.id = :id")
	public String findFcmTokenCaregiverById(@Param("id")Long id);
	
	@Query("select c.registerNurseNumberUrl from HomecareCaregiver c where c.id = :id")
	public String findStrUrlById(@Param("id")Long id);
	
	@Query("select c.sippUrl from HomecareCaregiver c where c.id = :id")
	public String findSippUrlById(@Param("id")Long id);
	
	@Query("select c.password from HomecareCaregiver c where c.email = :email")
	public String checkPasswordIsNullOrNot(@Param("email")String email);
	
}
