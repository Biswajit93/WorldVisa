package com.worldvisa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldvisa.model.Employee;
import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.EmployeeRepository;
import com.worldvisa.repository.UserInformationRepository;


@Service("commonPoolService")
public class CommonPoolServiceImpl implements CommonPoolService {
	
	@Autowired
	private UserInformationRepository informationRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public List<UserInformation> findReports() {
		return informationRepository.findAllByuserDetails_assignedToNull();
	}

	@Override
	public List<Employee> getAllEmpoyee() {
		return employeeRepository.findAll();
	}

	@Override
	public void assignReportTo(String reportId, String empEmail) {
		UserInformation uinf=informationRepository.findById(reportId).orElseThrow(null);
		uinf.getUserDetails().setAssignedTo(empEmail);
		uinf.getUserDetails().setStatus("Processing");
		informationRepository.save(uinf);
	}

	@Override
	public List<UserInformation> findActiveReports() {
		List<UserInformation> inf=new ArrayList<UserInformation>();
		for (UserInformation i: informationRepository.findAllByuserDetails_assignedToNotNull())
		{
			if(i.getUserDetails().getStatus().equals("Processing")) {
				inf.add(i);
			}
		}
		return inf;
	}

	@Override
	public void sendToPool(String id) {
		UserInformation uinf=informationRepository.findById(id).orElseThrow(null);
		uinf.getUserDetails().setAssignedTo(null);
		informationRepository.save(uinf);
	}

	@Override
	public List<UserInformation> findEmpActiveReports(String string) {
		List<UserInformation> inf=new ArrayList<UserInformation>();
		for(UserInformation i: informationRepository.findAllByuserDetails_assignedTo(string))
		{
			if(i.getUserDetails().getStatus().equals("Processing")) {
				inf.add(i);
			}
		}
		return inf;	
	}

	@Override
	public void closeReport(String id) {
		UserInformation info=informationRepository.findById(id).orElseThrow(null);
		info.getUserDetails().setStatus("Completed");
		informationRepository.save(info);
	}

	@Override
	public List<UserInformation> findDoneReports() {
		return informationRepository.findAllByuserDetails_status("Completed");
	}

	@Override
	public List<UserInformation> findEmpDoneReports(String email) {
		List<UserInformation> inf=new ArrayList<UserInformation>();
		for(UserInformation i: informationRepository.findAllByuserDetails_assignedTo(email)) {
			if(i.getUserDetails().getStatus().equals("Completed")) {
				inf.add(i);
			}
		}
		return inf;
	}
}
