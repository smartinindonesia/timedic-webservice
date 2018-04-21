package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.AppUser;

public interface UserDbRepository extends JpaRepository<AppUser, Long>{

	public AppUser findByUsername(String username);
	
	public AppUser findByEmail(String email);
	
	public AppUser findByPhoneNumber(String phone);
	
	public AppUser findByFirebaseIdFacebook(String idFirebase);
	
	public AppUser findByFirebaseIdGoogle(String idFirebase);
	
	public AppUser findByUserCode (String code);
	
	@Query( "select u from AppUser u" )
	public List<AppUser> findAllUsers(Pageable pageable);
	
	@Query("select u from AppUser u where LOWER (u.frontName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<AppUser> findUserByfrontName(@Param("value")String value, Pageable pageable);
	
	@Query("select u from AppUser u where LOWER (u.frontName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<AppUser> findUserByfrontNameGetCount(@Param("value")String value);
	
	@Query("select u from AppUser u where LOWER (u.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<AppUser> findUserBymiddleName(@Param("value")String value, Pageable pageable);
	
	@Query("select u from AppUser u where LOWER (u.middleName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<AppUser> findUserBymiddleNameCount(@Param("value")String value);
	
	@Query("select u from AppUser u where LOWER (u.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<AppUser> findUserBylastName(@Param("value")String value, Pageable pageable);
	
	@Query("select u from AppUser u where LOWER (u.lastName) LIKE LOWER( CONCAT('%',:value,'%'))")
	public List<AppUser> findUserBylastNameCount(@Param("value")String value);
	
	@Query("SELECT coalesce(max(ch.id), 0) FROM AppUser ch")
	Long getMaxId();
	
	
}
