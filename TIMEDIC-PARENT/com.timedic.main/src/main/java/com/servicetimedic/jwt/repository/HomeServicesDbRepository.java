package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.HomecareService;

public interface HomeServicesDbRepository extends JpaRepository<HomecareService, Long>{

}
