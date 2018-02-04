package com.servicetimedic.jwt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.servicetimedic.jwt.domain.december.HomecareAssessmentRecord;

//@Repository
public interface HomeCareAssessmentRecordDbRepository extends JpaRepository<HomecareAssessmentRecord, Long> {

}
