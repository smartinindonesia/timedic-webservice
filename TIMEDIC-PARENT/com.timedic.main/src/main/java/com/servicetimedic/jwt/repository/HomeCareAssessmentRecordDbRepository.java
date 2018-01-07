package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicetimedic.jwt.domain.december.HomecareAssessmentRecord;

public interface HomeCareAssessmentRecordDbRepository extends JpaRepository<HomecareAssessmentRecord, Long> {

}
