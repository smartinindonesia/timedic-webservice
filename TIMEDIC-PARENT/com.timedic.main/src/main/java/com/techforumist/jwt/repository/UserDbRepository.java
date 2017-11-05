package com.techforumist.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techforumist.jwt.domain.posgresql.AppUser;

public interface UserDbRepository extends JpaRepository<AppUser, Long>{

	public AppUser findByUsername(String username);
	
}
