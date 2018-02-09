package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.servicetimedic.jwt.domain.december.LaboratoryServiceTransaction;

public interface LaboratoryTransactionDbRepository extends JpaRepository<LaboratoryServiceTransaction, Long>{
	
	@Query("select u from LaboratoryServiceTransaction u where u.idPatient.idAppUser.id = :idUser AND u.transactionStatus.status = 'Unpaid' OR u.transactionStatus.status = 'Paid Down Payment' OR u.transactionStatus.status = 'Paid'")
	public List<LaboratoryServiceTransaction> findOrderActiveLaboratoryByIdUser(@Param("idUser") Long idUser);
	
	@Query("select u from LaboratoryServiceTransaction u where u.idPatient.idAppUser.id = :idUser")
	public List<LaboratoryServiceTransaction> findLaboratoryTrxByIdUser(@Param("idUser") Long idUser);
	
}
