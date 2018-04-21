package com.servicetimedic.jwt.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.domain.december.HomecarePatient;

public interface HomeCarePatientDbRepository extends JpaRepository <HomecarePatient, Long>{
	
	
	@Query( "select u from HomecarePatient u")
	public List<HomecarePatient> findHomecarePatientWithPagination(Pageable pageable);
	
	public List<HomecarePatient> findByIdAppUser(AppUser idUser);
	
	@Query("select c from HomecarePatient c where c.idAppUser.userCode = :codeUser")
	public List<HomecarePatient> findHomecarePatientByCodeUser(@Param("codeUser") String codeUser, Pageable pageable);
	
	@Query("select c from HomecarePatient c where c.idAppUser.userCode = :codeUser")
	public List<HomecarePatient> findHomecarePatientByCodeUserGetCount(@Param("codeUser") String codeUser);
	
	@Query("select c from HomecarePatient c where c.idAppUser.id = :idUser")
	public List<HomecarePatient> findHomecarePatientByIdUser(@Param("idUser") Long idUser, Pageable pageable);
	
	@Query("select c from HomecarePatient c where c.idAppUser.id = :idUser")
	public List<HomecarePatient> findHomecarePatientByIdUserGetCount(@Param("idUser") Long idUser);
	
	@Query("select c from HomecarePatient c where LOWER (c.name) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecarePatient> findPatientByName(@Param("value")String value, Pageable pageable);
	
	@Query("select c from HomecarePatient c where LOWER (c.name) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<HomecarePatient> findPatientByNameGetCount(@Param("value")String value);
	
	

}
