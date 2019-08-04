package com.worldvisa.service;

import java.util.Optional;

import com.worldvisa.model.UserInformation;

public interface ReportService {

	Optional<UserInformation> findReportById(String id);

}
