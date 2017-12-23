package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

//import com.servicetimedic.jwt.domain.posgresql.AppUser;
import com.servicetimedic.jwt.domain.december.AppUser;

public interface UserDbRepository extends JpaRepository<AppUser, Long>{

	public AppUser findByUsername(String username);
	
}
