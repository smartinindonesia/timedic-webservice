package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlist;
import com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlistRespon;


public interface HomeCareCaregiverTrxListRepository extends JpaRepository <HomecareTransactionCaregiverlist, Long> {
	
	@Query("select u.idCaregiver from HomecareTransactionCaregiverlist u where u.idServiceTransaction.id = :id")
	public List<Long> findIdCaregiverByIdTrx(@Param("id")Long id);
	
	
	@Query(value = "SELECT NEW com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlistRespon(u.idCaregiver, u.idServiceTransaction.orderNumber, u.idServiceTransaction.homecarePatientId.idAppUser.frontName ,u.idTransaction, u.idServiceTransaction.selectedService) FROM HomecareTransactionCaregiverlist u WHERE u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId = 8 GROUP BY u.idCaregiver, u.idServiceTransaction.orderNumber, u.idServiceTransaction.homecarePatientId.idAppUser.frontName, u.idTransaction, u.idServiceTransaction.selectedService")
	public List<HomecareTransactionCaregiverlistRespon> findOrderActiveHomecareByIdCaregiverWithPaginationGroupBy(@Param("idC") Long idCaregiver, Pageable pageable);
	
	@Query("SELECT  u.idCaregiver, u.idTransaction FROM HomecareTransactionCaregiverlist u WHERE u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId = 8 GROUP BY u.idCaregiver, u.idTransaction")
	public List<HomecareTransactionCaregiverlist> findOrderActiveHomecareByIdCaregiverWithPaginationGroupByGetCount(@Param("idC") Long idCaregiver);
	
	
	@Query(value = "SELECT NEW com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlistRespon(u.idCaregiver, u.idServiceTransaction.orderNumber, u.idServiceTransaction.homecarePatientId.idAppUser.frontName ,u.idTransaction, u.idServiceTransaction.selectedService) FROM HomecareTransactionCaregiverlist u WHERE u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId != 8 GROUP BY u.idCaregiver, u.idServiceTransaction.orderNumber, u.idServiceTransaction.homecarePatientId.idAppUser.frontName, u.idTransaction, u.idServiceTransaction.selectedService")
	public List<HomecareTransactionCaregiverlistRespon> findOrderHistoryHomecareByIdCaregiverWithPaginationGroupBy(@Param("idC") Long idCaregiver, Pageable pageable);
	
	@Query("SELECT  u.idCaregiver, u.idTransaction FROM HomecareTransactionCaregiverlist u WHERE u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId != 8 GROUP BY u.idCaregiver, u.idTransaction")
	public List<HomecareTransactionCaregiverlist> findOrderHistoryHomecareByIdCaregiverWithPaginationGroupByGetCount(@Param("idC") Long idCaregiver);
	
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId.id = 8")
	public List<HomecareTransactionCaregiverlist> findOrderActiveHomecareByIdCaregiverWithPagination(@Param("idC") Long idCaregiver, Pageable pageable);
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId.id = 8")
	public List<HomecareTransactionCaregiverlist> findOrderActiveHomecareByIdCaregiverWithPaginationGetCount(@Param("idC") Long idCaregiver);
	
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId.id != 8")
	public List<HomecareTransactionCaregiverlist> findOrderHistoryHomecareByIdCaregiverWithPagination(@Param("idC") Long idCaregiver, Pageable pageable);
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId.id != 8")
	public List<HomecareTransactionCaregiverlist> findOrderHistoryHomecareByIdCaregiverWithPaginationGetCount(@Param("idC") Long idCaregiver);
	
	public List<HomecareTransactionCaregiverlist> findByIdCaregiverAndIdTransaction(@Param("idCaregiver") Long idCaregiver, @Param("idTrx") Long idTrx);
}
