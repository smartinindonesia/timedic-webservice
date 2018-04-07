package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicetimedic.jwt.domain.december.HomecareTransactionCaregiverlist;


public interface HomeCareCaregiverTrxListRepository extends JpaRepository <HomecareTransactionCaregiverlist, Long> {

}
