package com.worldvisa.service;

import java.util.List;

import com.worldvisa.model.Employee;
import com.worldvisa.model.UserInformation;

public interface CommonPoolService {
	public List<UserInformation> findReports();

	public List<Employee> getAllEmpoyee();

	public void assignReportTo(String reortId, String empName);

	public List<UserInformation> findActiveReports();

	public void sendToPool(String id);

	public List<UserInformation> findEmpActiveReports(String string);

	public void closeReport(String id);

	public List<UserInformation> findDoneReports();

	public List<UserInformation> findEmpDoneReports(String email);
}
