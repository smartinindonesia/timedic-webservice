package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.LaboratorySelectedServiceTransaction;

public interface LaboratoryServiceSelectedDbRepository extends JpaRepository<LaboratorySelectedServiceTransaction, Long>{

}
