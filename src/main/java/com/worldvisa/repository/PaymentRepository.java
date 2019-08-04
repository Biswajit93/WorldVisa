package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Payment;

@Repository("paymentRepository")
public interface PaymentRepository extends JpaRepository<Payment, Integer>{

	Payment findOneByuserDetails(String id);

}
