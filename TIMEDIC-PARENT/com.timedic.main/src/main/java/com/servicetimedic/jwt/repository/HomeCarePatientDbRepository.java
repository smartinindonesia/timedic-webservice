package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicetimedic.jwt.domain.december.AppUser;
import com.servicetimedic.jwt.domain.december.HomecarePatient;

public interface HomeCarePatientDbRepository extends JpaRepository <HomecarePatient, Long>{
	
	public List<HomecarePatient> findByIdAppUser(AppUser idUser);

}
