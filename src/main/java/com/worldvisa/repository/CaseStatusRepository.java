package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.CaseStatus;
import com.worldvisa.model.UserDetails;


@Repository("caseStatusRepository")
public interface CaseStatusRepository extends JpaRepository<CaseStatus, Integer>{
	//Has all the database related operations CRUD already implemented in JpaREpository
			//Only write custom defined methods.

	List<CaseStatus> findAllByuserDetails(UserDetails userDetails);


}
