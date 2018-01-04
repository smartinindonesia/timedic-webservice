package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicetimedic.jwt.domain.december.HomecareService;
import com.servicetimedic.jwt.domain.december.HomecareServiceAssessment;

public interface HomeCareServiceAssesmentDbRepository  extends JpaRepository  <HomecareServiceAssessment,Long>{

	public List<HomecareServiceAssessment> findByIdService(HomecareService id);
	
}
