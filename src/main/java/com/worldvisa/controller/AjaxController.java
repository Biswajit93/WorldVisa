package com.worldvisa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.worldvisa.model.CaseStatus;
import com.worldvisa.model.EducationBackground;
import com.worldvisa.model.FamilyDetails;
import com.worldvisa.model.LanguageProficiency;
import com.worldvisa.model.PaymentTransaction;
import com.worldvisa.model.RelativesAbroad;
import com.worldvisa.model.TestScores;
import com.worldvisa.model.UserInformation;
import com.worldvisa.model.WorkExperience;
import com.worldvisa.repository.CaseStatusRepository;
import com.worldvisa.repository.UserInformationRepository;
import com.worldvisa.service.ReportService;

@RestController
public class AjaxController {
	
	@Autowired
	private CaseStatusRepository caseStatusRepository;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserInformationRepository informationRepository;
	
	@PostMapping("/api/languageProficiency")
    public void getSearchResultViaAjax(@Valid @RequestBody List<LanguageProficiency> languageProficiency
    		, Errors errors, HttpServletRequest request) {
		
		if(languageProficiency != null)
			request.getSession().setAttribute("languageProficiency",languageProficiency);
		    
        if (errors.hasErrors()) {

        }

    }
	
	@PostMapping("/api/testScores")
    public void getTestSearchResultViaAjax(@Valid @RequestBody List<TestScores> testScores, Errors errors, HttpServletRequest request) {
		
		if(testScores != null)
			request.getSession().setAttribute("testScores",testScores);
		
        if (errors.hasErrors()) {

        }
		

    }
	
	@PostMapping("/api/familyDetails")
    public void getFamilyDetailsSearchResultViaAjaxTest(@Valid @RequestBody List<FamilyDetails> familyDetails, Errors errors, HttpServletRequest request) {
		
		if(familyDetails != null)
			request.getSession().setAttribute("familyDetails",familyDetails);

        if (errors.hasErrors()) {

        }
		
    }
	
	@PostMapping("/api/relativesAbroad")
    public void getRelativesAbroadSearchResultViaAjaxTest(@Valid @RequestBody List<RelativesAbroad> relativesAbroad, Errors errors, HttpServletRequest request) {
		
		if(relativesAbroad != null)
			request.getSession().setAttribute("relativesAbroad",relativesAbroad);

        if (errors.hasErrors()) {

        }
		

    }
	
	@PostMapping("/api/workExperience")
    public void getWorkExperienceSearchResultViaAjaxTest(@Valid @RequestBody List<WorkExperience> workExperience, Errors errors, HttpServletRequest request) {
		
		if(workExperience != null)
			request.getSession().setAttribute("workExperience",workExperience);
 
        if (errors.hasErrors()) {

        }
		

    }
	
	@PostMapping("/api/educationBackground")
    public void getEducationBackgroundSearchResultViaAjaxTest(@Valid @RequestBody List<EducationBackground> educationBackground, Errors errors, HttpServletRequest request) {
		
		if(educationBackground != null)
			request.getSession().setAttribute("educationBackground",educationBackground);
 
        if (errors.hasErrors()) {

        }
		
    }
	
	@PostMapping("/api/paymentList")
    public void getPaymentListResultViaAjaxTest(@Valid @RequestBody List<PaymentTransaction> paymentTransaction, Errors errors, HttpServletRequest request) {
		
		if(paymentTransaction != null)
			request.getSession().setAttribute("paymentTransaction",paymentTransaction);
 
        if (errors.hasErrors()) {

        }
		
    }
	
	@PostMapping("/add")
    public void getbody(@Valid @RequestBody String body, Errors errors, HttpServletRequest request) {
		if(body != null)
			request.getSession().setAttribute("body",body);
   
        if (errors.hasErrors()) {

        }
		
    }
	
	@PostMapping("/api/getStatus")
	public @ResponseBody List<CaseStatus> getStatus(@RequestBody String i,Model model) {
		List<CaseStatus> caseStatusList = new ArrayList<>();
		UserInformation u=reportService.findReportById(i).orElseThrow(null);
		caseStatusList = caseStatusRepository.findAllByuserDetails(u.getUserDetails());
		return caseStatusList;
	}
	
	@PostMapping("/api/checkEmail")
	public @ResponseBody String checkEmail(@RequestBody String e) {
		if(e.equals(null))
				return "NONE";
		UserInformation user=null;
		try {
			user=informationRepository.findOneByemailAddress(e);
		}catch(Exception ex) {
			
		}
		if(user!=null) {
			return "EXISTS";
		}
		else
			return "OK";
	}
}
