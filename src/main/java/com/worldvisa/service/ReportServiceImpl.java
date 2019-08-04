package com.worldvisa.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.worldvisa.model.UserInformation;
import com.worldvisa.repository.UserInformationRepository;

@Service("reportService")
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private UserInformationRepository report;

	@Override
	public Optional<UserInformation> findReportById(String id) {
		return report.findById(id);
	}

}
