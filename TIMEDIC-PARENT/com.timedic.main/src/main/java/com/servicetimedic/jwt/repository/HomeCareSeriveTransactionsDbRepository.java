package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.HomecareCaregiver;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;

import java.util.Date;
public interface HomeCareSeriveTransactionsDbRepository extends JpaRepository<HomecareServiceTransaction, Long> {

	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser AND u.transactionStatusId.status = 'Unpaid' OR u.transactionStatusId.status = 'Paid Down Payment' OR u.transactionStatusId.status = 'Paid'")
	public List<HomecareServiceTransaction> findOrderActiveHomecareByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u.id, u.fixedPrice, u.homecarePatientId, u.transactionStatusId from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findByHomecareByIdUserForAndroid(@Param("idUser") Long idUser);
	
	@Query( "select u from HomecareServiceTransaction u" )
	public List<HomecareServiceTransaction> findAllHomecareTrx(Pageable pageable);
	
	public List<HomecareServiceTransaction> findWithPaginationByDate(Date date, Pageable pageable);
	
	public List<HomecareServiceTransaction> findByDate(Date date);
	
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUserWithPagination(@Param("idUser") Long idUser, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUserWithPaginationGetCount(@Param("idUser") Long idUser);
	
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.username = :username")
	public List<HomecareServiceTransaction> findHomecareTrxByUsernameWithPagination(@Param("username") String username, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.username = :username")
	public List<HomecareServiceTransaction> findHomecareTrxByUsernameWithPaginationGetCount(@Param("username") String username);
	
	
}
