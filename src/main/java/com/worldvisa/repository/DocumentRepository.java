package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Documents;
import com.worldvisa.model.UserDetails;

@Repository("documentRepository")
public interface DocumentRepository extends JpaRepository<Documents, Integer>{
	
	List<Documents> findByUserDetails(UserDetails userDetails);

}
