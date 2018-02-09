package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.LaboratorySelectedPackageTransaction;

public interface LaboratoryPackageSelectedDbRepository extends JpaRepository<LaboratorySelectedPackageTransaction, Long>{

}
