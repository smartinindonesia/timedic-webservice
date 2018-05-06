package com.servicetimedic.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.servicetimedic.jwt.domain.december.ContactUs;

public interface ContactUsDbRepository extends JpaRepository<ContactUs, Long>{

}
