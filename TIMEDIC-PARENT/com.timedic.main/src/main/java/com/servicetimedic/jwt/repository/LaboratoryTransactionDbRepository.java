package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.LaboratoryServiceTransaction;

public interface LaboratoryTransactionDbRepository extends JpaRepository<LaboratoryServiceTransaction, Long>{
	
}
