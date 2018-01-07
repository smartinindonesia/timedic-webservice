package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicetimedic.jwt.domain.december.HomecareServiceTransaction;

public interface HomeCareSeriveTransactionsDbRepository extends JpaRepository<HomecareServiceTransaction, Long> {

}
