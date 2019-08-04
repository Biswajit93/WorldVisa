package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.ContactFiles;

@Repository("contactFileRepository")
public interface ContactFileRepository extends JpaRepository<ContactFiles, Integer>{

	//Has all the database related operations CRUD already implemented in JpaREpository
		//Only write custom defined methods.
	
	List<ContactFiles> findAllBydataSource(String string);
}
