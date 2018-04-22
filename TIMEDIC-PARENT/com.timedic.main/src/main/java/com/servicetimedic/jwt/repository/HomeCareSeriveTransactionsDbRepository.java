package com.servicetimedic.jwt.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;
//import java.sql.Date;
import java.util.Date;
import java.lang.String;
public interface HomeCareSeriveTransactionsDbRepository extends JpaRepository<HomecareServiceTransaction, Long> {
	
	public List<HomecareServiceTransaction> findByOrderNumber(String ordernumber, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser AND u.transactionStatusId.status = 'Unpaid' OR u.transactionStatusId.status = 'Paid Down Payment' OR u.transactionStatusId.status = 'Paid'")
	public List<HomecareServiceTransaction> findOrderActiveHomecareByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u.id, u.fixedPrice, u.homecarePatientId, u.transactionStatusId from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findByHomecareByIdUserForAndroid(@Param("idUser") Long idUser);
	
	@Query( "select u from HomecareServiceTransaction u" )
	public List<HomecareServiceTransaction> findAllHomecareTrx(Pageable pageable);
		
	@Query("SELECT coalesce(max(ch.id), 0) FROM HomecareServiceTransaction ch")
	Long getMaxId();
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.userCode = :userCode")
	public List<HomecareServiceTransaction> findHomecareTrxByCodeUserWithPagination(@Param("userCode") String userCode, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.userCode = :userCode")
	public List<HomecareServiceTransaction> findHomecareTrxByCodeUserWithPaginationGetCount(@Param("userCode") String userCode);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUserWithPagination(@Param("idUser") Long idUser, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUserWithPaginationGetCount(@Param("idUser") Long idUser);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.username = :username")
	public List<HomecareServiceTransaction> findHomecareTrxByUsernameWithPagination(@Param("username") String username, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.username = :username")
	public List<HomecareServiceTransaction> findHomecareTrxByUsernameWithPaginationGetCount(@Param("username") String username);

	
	@Query("SELECT b FROM HomecareServiceTransaction b " + 
	        "WHERE EXTRACT (day FROM b.date) = :day AND EXTRACT (month FROM b.date) = :month AND EXTRACT (year FROM b.date) = :year")
	public List<HomecareServiceTransaction> findHomecareTrxByDateInWithPagination(@Param("day") int dayOfMonth, @Param("month") int month,  @Param("year") int year, Pageable pageable);
	
	@Query("SELECT b FROM HomecareServiceTransaction b " + 
	        "WHERE EXTRACT (day FROM b.date) = :day AND EXTRACT (month FROM b.date) = :month AND EXTRACT (year FROM b.date) = :year")
	public List<HomecareServiceTransaction> findHomecareTrxByDateInWithPaginationGetCount(@Param("day") int dayOfMonth, @Param("month") int month,  @Param("year") int year);
	
	@Query("SELECT b FROM HomecareServiceTransaction b " + 
	        "WHERE EXTRACT (day FROM b.dateOrderIn) = :day AND EXTRACT (month FROM b.dateOrderIn) = :month AND EXTRACT (year FROM b.dateOrderIn) = :year")
	public List<HomecareServiceTransaction> findHomecareTrxByDateOrderInWithPagination(@Param("day") int dayOfMonth, @Param("month") int month,  @Param("year") int year, Pageable pageable);
	
	@Query("SELECT b FROM HomecareServiceTransaction b " + 
	        "WHERE EXTRACT (day FROM b.dateOrderIn) = :day AND EXTRACT (month FROM b.dateOrderIn) = :month AND EXTRACT (year FROM b.dateOrderIn) = :year")
	public List<HomecareServiceTransaction> findHomecareTrxByDateOrderInWithPaginationGetCount(@Param("day") int dayOfMonth, @Param("month") int month,  @Param("year") int year);
	
	@Query("select u from HomecareServiceTransaction u where u.transactionStatusId.id = :id")
	public List<HomecareServiceTransaction> findHomecareTrxBySistemTrxStatusWithPagination(@Param("id") Integer id, Pageable pageable);
	
	@Query("select u from HomecareServiceTransaction u where u.transactionStatusId.id = :id")
	public List<HomecareServiceTransaction> findHomecareTrxBySistemTrxStatusWithPaginationGetCount(@Param("id") Integer id);
	
}
