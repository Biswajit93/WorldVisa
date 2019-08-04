package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.UserPaymentDates;

@Repository("userNotificationRepository")
public interface UserPaymentDatesRepository extends JpaRepository<UserPaymentDates, Integer>{
	


}
