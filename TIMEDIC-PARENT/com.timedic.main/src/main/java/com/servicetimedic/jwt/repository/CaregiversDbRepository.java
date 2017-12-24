package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.HomecareCaregiver;

public interface CaregiversDbRepository extends JpaRepository<HomecareCaregiver, Long>{

	public HomecareCaregiver findByUsername(String username);
	
}
