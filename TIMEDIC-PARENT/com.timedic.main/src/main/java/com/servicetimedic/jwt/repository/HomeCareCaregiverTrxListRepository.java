package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlist;


public interface HomeCareCaregiverTrxListRepository extends JpaRepository <HomecareTransactionCaregiverlist, Long> {
	
	@Query("select u.idCaregiver from HomecareTransactionCaregiverlist u where u.idServiceTransaction.id = :id")
	public List<Long> findIdCaregiverByIdTrx(@Param("id")Long id);
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId.id = 8")
	public List<HomecareTransactionCaregiverlist> findOrderActiveHomecareByIdCaregiverWithPagination(@Param("idC") Long idCaregiver, Pageable pageable);
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC AND u.idServiceTransaction.transactionStatusId.id = 8")
	public List<HomecareTransactionCaregiverlist> findOrderActiveHomecareByIdCaregiverWithPaginationGetCount(@Param("idC") Long idCaregiver);
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC")
	public List<HomecareTransactionCaregiverlist> findOrderHistoryHomecareByIdCaregiverWithPagination(@Param("idC") Long idCaregiver, Pageable pageable);
	
	@Query("select u from HomecareTransactionCaregiverlist u where u.idCaregiver = :idC")
	public List<HomecareTransactionCaregiverlist> findOrderHistoryHomecareByIdCaregiverWithPaginationGetCount(@Param("idC") Long idCaregiver);
}
