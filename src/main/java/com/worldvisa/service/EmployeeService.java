package com.worldvisa.service;

import java.util.List;

import com.worldvisa.model.Employee;
import com.worldvisa.model.Notification;

public interface EmployeeService {
	public Employee findEmployeeByEmail(String email);
	public void saveEmployee(Employee employee);
	//public List<String> findManagers();
	public List<Employee> findAll();
	public void saveNotification(Notification notification);
	public List<Notification> getNotifications();
	public List<Notification> getEmpNotifications(String string);
}
