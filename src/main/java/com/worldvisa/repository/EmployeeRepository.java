package com.worldvisa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.worldvisa.model.Employee;

@Repository("employeeRepository")
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
	//Has all the database related operations CRUD already implemented in JpaREpository
			//Only write custom defined methods.
	Employee findByEmail(String email);

	//List<String> findByRole(String role);
}
