package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.SocialFollowUp;

@Repository("followUpRepository")
public interface FollowUpRepository extends JpaRepository<SocialFollowUp,Integer>{

	
	//Has all the database related operations CRUD already implemented in JpaREpository
	//Only write custom defined methods.
	List<SocialFollowUp> findAllBycontactName(String name);
}
