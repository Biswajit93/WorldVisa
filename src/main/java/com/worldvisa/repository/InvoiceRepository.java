package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Invoice;

@Repository("invoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Integer>{
}
