package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.HomecareHomecareClinicAdmin;

public interface HomeCareClinicDbRepository extends JpaRepository<HomecareHomecareClinicAdmin, Long>{

	public HomecareHomecareClinicAdmin findByUsername(String username);
	
}
