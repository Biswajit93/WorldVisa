package com.worldvisa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldvisa.model.Employee;
import com.worldvisa.model.Notification;
import com.worldvisa.repository.EmployeeRepository;
import com.worldvisa.repository.NotificationRepository;

@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private NotificationRepository notifyRepository;
	
	@Override
	public Employee findEmployeeByEmail(String email) {
		return employeeRepository.findByEmail(email);
	}
	
	@Override
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
		
	}

	/*@Override
	public List<String> findManagers() {
		return employeeRepository.findByRole("Manager");
	}*/

	@Override
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}
	
	@Override
	public void saveNotification(Notification notification) {
		notifyRepository.save(notification);		
	}

	@Override
	public List<Notification> getNotifications() {
		return notifyRepository.findAll();
	}

	@Override
	public List<Notification> getEmpNotifications(String string) {
		return notifyRepository.findAllBytoWhom(string);
	}

}
