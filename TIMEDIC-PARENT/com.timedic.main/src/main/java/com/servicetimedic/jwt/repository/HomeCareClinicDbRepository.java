package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.servicetimedic.jwt.domain.december.HomecareHomecareClinicAdmin;

public interface HomeCareClinicDbRepository extends JpaRepository<HomecareHomecareClinicAdmin, Long>{

	public HomecareHomecareClinicAdmin findByUsername(String username);
	
	public HomecareHomecareClinicAdmin findByEmail(String email);
	
	public HomecareHomecareClinicAdmin findByPhoneNumber(String phone);
	
	@Query( "select f from HomecareHomecareClinicAdmin f" )
	public List<HomecareHomecareClinicAdmin> findAllHomecareClinicAdmin(Pageable pageable);
	
	@Query("select f from HomecareHomecareClinicAdmin f where LOWER (f.firstName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareHomecareClinicAdmin> findHomecareClinicAdminByfrontName(@Param("value")String value, Pageable pageable);
	
	@Query("select f from HomecareHomecareClinicAdmin f where LOWER (f.firstName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareHomecareClinicAdmin> findHomecareClinicAdminByfrontNameGetCount(@Param("value")String value);
	
	@Query("select f from HomecareHomecareClinicAdmin f where LOWER (f.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareHomecareClinicAdmin> findHomecareClinicAdminBymiddleName(@Param("value")String value, Pageable pageable);
	
	@Query("select f from HomecareHomecareClinicAdmin f where LOWER (f.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareHomecareClinicAdmin> findHomecareClinicAdminBymiddleNameGetCount(@Param("value")String value);
	
	@Query("select f from HomecareHomecareClinicAdmin f where LOWER (f.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareHomecareClinicAdmin> findHomecareClinicAdminBylastName(@Param("value")String value, Pageable pageable);
	
	@Query("select f from HomecareHomecareClinicAdmin f where LOWER (f.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecareHomecareClinicAdmin> findHomecareClinicAdminBylastNameGetCount(@Param("value")String value);
	
}
