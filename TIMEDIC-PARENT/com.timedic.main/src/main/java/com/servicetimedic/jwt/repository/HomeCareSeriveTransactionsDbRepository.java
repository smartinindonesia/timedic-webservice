package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;

public interface HomeCareSeriveTransactionsDbRepository extends JpaRepository<HomecareServiceTransaction, Long> {

	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser AND u.transactionStatusId.status = 'Unpaid' OR u.transactionStatusId.status = 'Paid Down Payment' OR u.transactionStatusId.status = 'Paid'")
	public List<HomecareServiceTransaction> findOrderActiveHomecareByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findHomecareTrxByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u.id, u.fixedPrice, u.homecarePatientId, u.transactionStatusId from HomecareServiceTransaction u where u.homecarePatientId.idAppUser.id = :idUser")
	public List<HomecareServiceTransaction> findByHomecareByIdUserForAndroid(@Param("idUser") Long idUser);
	
}
