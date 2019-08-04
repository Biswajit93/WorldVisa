package com.worldvisa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Contacts;



@Repository("contactRepository")
public interface ContactRepository extends JpaRepository<Contacts, Integer>{

	//Has all the database related operations CRUD already implemented in JpaREpository
	//Only write custom defined methods.
	
	List<Contacts> findAllBycontactFileId(int contactsFileId);
}
