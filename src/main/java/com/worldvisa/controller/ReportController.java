package com.worldvisa.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.worldvisa.model.CountryPreferences;
import com.worldvisa.model.EducationBackground;
import com.worldvisa.model.FamilyDetails;
import com.worldvisa.model.LanguageProficiency;
import com.worldvisa.model.RelativesAbroad;
import com.worldvisa.model.TestScores;
import com.worldvisa.model.User;
import com.worldvisa.model.UserDetails;
import com.worldvisa.model.UserInformation;
import com.worldvisa.model.WorkExperience;
import com.worldvisa.service.ReportService;
import com.worldvisa.service.UserDetailsService;

@Controller
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@GetMapping("/report/find/{id}")
	public String viewReport(@PathVariable("id") String id,Model model, HttpServletRequest request) {
		UserInformation report=new UserInformation();
		UserInformation userInformation=new UserInformation();
		UserDetails userDetails=new UserDetails();
		report=reportService.findReportById(id).orElseThrow(null);
		
		request.getSession().setAttribute("reportID",id);
		request.getSession().setAttribute("report",report);
		User user = (User) request.getSession().getAttribute("user");
		
		List<CountryPreferences> countries = report.getUserDetails().getCountryPreferencesList();
		List<String> selectedCountries=new ArrayList<String>();
		for(CountryPreferences c:countries) {
			selectedCountries.add(c.getCountry());
		}
		
		List<String> countryList = new ArrayList<String>();
		countryList.add("Australia");
		countryList.add("Canada");
		countryList.add("United Kingdom");
		countryList.add("United States of America");
		countryList.add("Germany");
		countryList.add("Singapore");
		countryList.add("New Zealand");
		countryList.add("Hongkong");
		countryList.add("Denmark");
		countryList.add("Ireland"); 
		
		List<String> areaOfInterestList = new ArrayList<String>();
		areaOfInterestList.add("Immigration");
		areaOfInterestList.add("Visit Visa");
		areaOfInterestList.add("Migration Reviews");
		areaOfInterestList.add("Dependant Visa");
		areaOfInterestList.add("Domestic Worker Visa");
		areaOfInterestList.add("Post Study Visa");
		areaOfInterestList.add("Work Holiday Pass");
		areaOfInterestList.add("Epec");
		areaOfInterestList.add("Overseas Education Admission and Visa");
		areaOfInterestList.add("Overseas Education Visa Only");
		areaOfInterestList.add("Visa Reviews");
		areaOfInterestList.add("Business Visa");
		areaOfInterestList.add("Work Permit");
		areaOfInterestList.add("Others");
		
		List<String> editableByClientList = new ArrayList<String>();
		editableByClientList.add("No");
		editableByClientList.add("Yes");
		
		List<String> requestModeList = new ArrayList<String>();
		requestModeList.add("Online");
		requestModeList.add("Telephone");
		requestModeList.add("Client Walk In");
		
		List<String> maritalStatusList = new ArrayList<String>();
		maritalStatusList.add("Single");
		maritalStatusList.add("Married");
		maritalStatusList.add("Divorced");
		
		List<String> hearAboutUsList = new ArrayList<String>();
		hearAboutUsList.add("Social Media");
		hearAboutUsList.add("Advertisements");
		hearAboutUsList.add("Friends");
		hearAboutUsList.add("World Visa Alumni");
		hearAboutUsList.add("Others");
		
		
		model.addAttribute("report", report);
		model.addAttribute("userdetails", userDetails);
		model.addAttribute("userInformation", userInformation);
		model.addAttribute("areaOfInterestList", areaOfInterestList);
		model.addAttribute("editableByClientList", editableByClientList);
		model.addAttribute("requestModeList", requestModeList);
		model.addAttribute("maritalStatusList", maritalStatusList);
		model.addAttribute("hearAboutUsList", hearAboutUsList);
		model.addAttribute("countryList", countryList);
		model.addAttribute("selectedCountries", selectedCountries);
		model.addAttribute("user", user);
		model.addAttribute("id", id);
		
		
		return "report";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/update_form", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userdetails") UserDetails userDetails,
			@ModelAttribute("userInformation") UserInformation userInformation, 
			@ModelAttribute("report") UserInformation report,@RequestParam List<String> expertise,
			HttpServletRequest request,
			BindingResult bindingResult){
				
		
		List<LanguageProficiency> languageProficiencyList = (List<LanguageProficiency>) request.getSession().getAttribute("languageProficiency");
		List<TestScores> testScoresList = (List<TestScores>) request.getSession().getAttribute("testScores");
		List<RelativesAbroad> relativesAbroadList = (List<RelativesAbroad>) request.getSession().getAttribute("relativesAbroad");
		List<WorkExperience> workExperienceList = (List<WorkExperience>) request.getSession().getAttribute("workExperience");
		List<EducationBackground> educationBackgroundList = (List<EducationBackground>) request.getSession().getAttribute("educationBackground");
		List<FamilyDetails> familyDetailsList = (List<FamilyDetails>) request.getSession().getAttribute("familyDetails");
		
		String reportID = (String) request.getSession().getAttribute("reportID");
		userDetails.setReportId(reportID);
		userInformation.setId(reportID);
		report=reportService.findReportById(reportID).orElseThrow(null);
		
		if(languageProficiencyList != null) {
			for(LanguageProficiency languageProficiency : languageProficiencyList)
			{
				languageProficiency.setUserDetails(userDetails);
			}
		}
		
		if(testScoresList != null) {
			for(TestScores testScores : testScoresList)
			{
				testScores.setUserDetails(userDetails);
			}
		}
		
		if(relativesAbroadList != null) {
			for(RelativesAbroad relativesAbroad : relativesAbroadList)
			{
				relativesAbroad.setUserDetails(userDetails);
			}
		}
		
		if(workExperienceList != null) {
			for(WorkExperience workExperience : workExperienceList)
			{
				workExperience.setUserDetails(userDetails);
			}
		}
		
		if(educationBackgroundList != null) {
			for(EducationBackground educationBackground : educationBackgroundList)
			{
				educationBackground.setUserDetails(userDetails);
			}
		}
		
		if(familyDetailsList != null) {
			for(FamilyDetails familyDetails : familyDetailsList)
			{
				familyDetails.setUserDetails(userDetails);
			}
		}
		
		
		List<CountryPreferences> countryPreferencesList = new ArrayList<CountryPreferences>();
		if(expertise != null) {
			for(String country : expertise )
			{
				CountryPreferences countryPreferences = new CountryPreferences();
				countryPreferences.setCountry(country);
				countryPreferences.setUserDetails(userDetails);
				countryPreferencesList.add(countryPreferences);
				
			}
		}
		
		if(languageProficiencyList != null && !languageProficiencyList.isEmpty())
			userDetails.getLanguageProficiency().addAll(languageProficiencyList);
		
		if(testScoresList != null && !testScoresList.isEmpty())
			userDetails.getTestScoresList().addAll(testScoresList);
		
		if(relativesAbroadList != null && !relativesAbroadList.isEmpty())
			userDetails.getRelativesAbroadList().addAll(relativesAbroadList);
		
		if(workExperienceList != null && !workExperienceList.isEmpty())
			userDetails.getWorkExperienceList().addAll(workExperienceList);
		
		if(educationBackgroundList != null && !educationBackgroundList.isEmpty())
			userDetails.getEducationBackgroundList().addAll(educationBackgroundList);
		
		if(familyDetailsList != null && !familyDetailsList.isEmpty())
			userDetails.getFamilyDetailsList().addAll(familyDetailsList);
		
		if(countryPreferencesList != null && !countryPreferencesList.isEmpty())
			userDetails.getCountryPreferencesList().addAll(countryPreferencesList);
		
		if(report.getUserDetails().getNotification() == null) {
			userDetails.getNotification().clear();
		}
		 else {
			 userDetails.getNotification().clear();
			 userDetails.getNotification().addAll(report.getUserDetails().getNotification());
		 }
		
		if(report.getUserDetails().getUserPaymentDates() == null) {
			userDetails.getUserPaymentDates().clear();
		}
		 else {
			 userDetails.getUserPaymentDates().clear();
			 userDetails.getUserPaymentDates().addAll(report.getUserDetails().getUserPaymentDates());
		 }
		
		if(report.getUserDetails().getPaymentList()== null) {
			userDetails.getPaymentList().clear();
		}
		 else {
			 userDetails.getPaymentList().clear();
			 userDetails.getPaymentList().addAll(report.getUserDetails().getPaymentList());
		 }
		
		if(report.getUserDetails().getPaymentTransactionList() == null) {
			userDetails.getPaymentTransactionList().clear();
		}
		 else {
			 userDetails.getPaymentTransactionList().clear();
			 userDetails.getPaymentTransactionList().addAll(report.getUserDetails().getPaymentTransactionList());
		 }
		
		userDetails.setRaisedBy(report.getUserDetails().getRaisedBy());
		userDetails.setRaisedTime(report.getUserDetails().getRaisedTime());
		
		if (bindingResult.hasErrors()) {
		}
		else {
			userInformation.setUserDetails(userDetails);
			userDetailsService.saveDetails(userInformation);
			
		}
		return "redirect:/report/find/"+reportID;
	}
	
	
	
}
