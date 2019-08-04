package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.PaymentTransaction;

@Repository("paymentTransactionRepository")
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Integer>{

}
