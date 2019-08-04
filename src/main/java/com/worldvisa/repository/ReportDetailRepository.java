package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.UserDetails;

@Repository("reportDetailsRepository")
public interface ReportDetailRepository  extends JpaRepository<UserDetails, String>{

	UserDetails findByreportId(String i);
	List<UserDetails> findByExpiry(int i);

}
