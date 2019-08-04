package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Template;

@Repository("templateRepository")
public interface TemplateRepository extends JpaRepository<Template, Integer>{
	//Has all the database related operations CRUD already implemented in JpaREpository
	//Only write custom defined methods.
}
