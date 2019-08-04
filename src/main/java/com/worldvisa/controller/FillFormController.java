package com.worldvisa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.worldvisa.model.CountryPreferences;
import com.worldvisa.model.EducationBackground;
import com.worldvisa.model.FamilyDetails;
import com.worldvisa.model.LanguageProficiency;
import com.worldvisa.model.RelativesAbroad;
import com.worldvisa.model.Role;
import com.worldvisa.model.TestScores;
import com.worldvisa.model.UniqueID;
import com.worldvisa.model.User;
import com.worldvisa.model.UserDetails;
import com.worldvisa.model.UserInformation;
import com.worldvisa.model.WorkExperience;
import com.worldvisa.repository.RoleRepository;
import com.worldvisa.repository.UniqueIDRepository;
import com.worldvisa.repository.UserRepository;
import com.worldvisa.service.MailContentBuilder;
import com.worldvisa.service.UserDetailsService;

@Controller
public class FillFormController {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JavaMailSender sender;

	@Autowired
	private MailContentBuilder contentBuilder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UniqueIDRepository uniqueIDRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@RequestMapping(value = "/fill_a_form", method = RequestMethod.GET)
	public String home(Model model,HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		List<String> editableByClientList = new ArrayList<String>();
		editableByClientList.add("No");
		editableByClientList.add("Yes");

		List<String> requestModeList = new ArrayList<String>();
		requestModeList.add("Online");
		requestModeList.add("Telephone");
		requestModeList.add("Client Walk In");

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

		UserDetails userdetails = new UserDetails();
		UserInformation userInformation = new UserInformation();

		model.addAttribute("userdetails", userdetails);
		model.addAttribute("userInformation", userInformation);
		model.addAttribute("editableByClientList", editableByClientList);
		model.addAttribute("requestModeList", requestModeList);
		model.addAttribute("countryList", countryList);
		model.addAttribute("areaOfInterestList", areaOfInterestList);
		model.addAttribute("maritalStatusList", maritalStatusList);
		model.addAttribute("hearAboutUsList", hearAboutUsList);
		model.addAttribute("user", user);

		return "fill_a_form";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/fill_a_form", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute("userdetails") UserDetails userDetails,
			@ModelAttribute("userInformation") UserInformation userInformation, BindingResult bindingResult,
			HttpServletRequest request) {

		String email = (String) request.getSession().getAttribute("currentEmp");
		userDetails.setRaisedBy(email);

		List<LanguageProficiency> languageProficiencyList = (List<LanguageProficiency>) request.getSession()
				.getAttribute("languageProficiency");
		List<TestScores> testScoresList = (List<TestScores>) request.getSession().getAttribute("testScores");
		List<RelativesAbroad> relativesAbroadList = (List<RelativesAbroad>) request.getSession()
				.getAttribute("relativesAbroad");
		List<WorkExperience> workExperienceList = (List<WorkExperience>) request.getSession()
				.getAttribute("workExperience");
		List<EducationBackground> educationBackgroundList = (List<EducationBackground>) request.getSession()
				.getAttribute("educationBackground");
		List<FamilyDetails> familyDetailsList = (List<FamilyDetails>) request.getSession()
				.getAttribute("familyDetails");

		if (languageProficiencyList != null) {
			for (LanguageProficiency languageProficiency : languageProficiencyList) {
				languageProficiency.setUserDetails(userDetails);
			}
		}

		if (testScoresList != null) {
			for (TestScores testScores : testScoresList) {
				testScores.setUserDetails(userDetails);
			}
		}

		if (relativesAbroadList != null) {
			for (RelativesAbroad relativesAbroad : relativesAbroadList) {
				relativesAbroad.setUserDetails(userDetails);
			}
		}

		if (workExperienceList != null) {
			for (WorkExperience workExperience : workExperienceList) {
				workExperience.setUserDetails(userDetails);
			}
		}

		if (educationBackgroundList != null) {
			for (EducationBackground educationBackground : educationBackgroundList) {
				educationBackground.setUserDetails(userDetails);
			}
		}

		if (familyDetailsList != null) {
			for (FamilyDetails familyDetails : familyDetailsList) {
				familyDetails.setUserDetails(userDetails);
			}
		}

		List<CountryPreferences> countryPreferencesList = new ArrayList<CountryPreferences>();
		if (userDetails.getCountryList() != null) {
			for (String country : userDetails.getCountryList()) {
				CountryPreferences countryPreferences = new CountryPreferences();
				countryPreferences.setCountry(country);
				countryPreferences.setUserDetails(userDetails);
				countryPreferencesList.add(countryPreferences);

			}
		}

		if (languageProficiencyList != null && !languageProficiencyList.isEmpty())
			userDetails.getLanguageProficiency().addAll(languageProficiencyList);

		if (testScoresList != null && !testScoresList.isEmpty())
			userDetails.getTestScoresList().addAll(testScoresList);

		if (relativesAbroadList != null && !relativesAbroadList.isEmpty())
			userDetails.getRelativesAbroadList().addAll(relativesAbroadList);

		if (workExperienceList != null && !workExperienceList.isEmpty())
			userDetails.getWorkExperienceList().addAll(workExperienceList);

		if (educationBackgroundList != null && !educationBackgroundList.isEmpty())
			userDetails.getEducationBackgroundList().addAll(educationBackgroundList);

		if (familyDetailsList != null && !familyDetailsList.isEmpty())
			userDetails.getFamilyDetailsList().addAll(familyDetailsList);

		if (countryPreferencesList != null && !countryPreferencesList.isEmpty())
			userDetails.getCountryPreferencesList().addAll(countryPreferencesList);

		userDetails.setRaisedTime(new java.sql.Timestamp(new java.util.Date().getTime()));
		userDetails.setStatus("Fresh");

		UniqueID uniqueID = new UniqueID();
		uniqueIDRepository.save(uniqueID);

		List<UniqueID> uniqueIDList = uniqueIDRepository.findAll();
		Integer max = 0;
		for (UniqueID id : uniqueIDList) {
			if (max < id.getId()) {
				max = id.getId();
			}
		}

		if (max.toString().length() == 1) {
			userDetails.setReportId("WV000" + max);
			userInformation.setId("WV000" + max);
		} else if (max.toString().length() == 2) {
			userDetails.setReportId("WV00" + max);
			userInformation.setId("WV00" + max);
		} else if (max.toString().length() == 3) {
			userDetails.setReportId("WV0" + max);
			userInformation.setId("WV0" + max);
		} else {
			userDetails.setReportId("WV" + max);
			userInformation.setId("WV" + max);
		}

		if (bindingResult.hasErrors()) {
		} else {
			userInformation.setUserDetails(userDetails);
			userDetailsService.saveDetails(userInformation);

			String subject = "Thank You for your interest in WorldVisa";
			List<String> body = new ArrayList<String>();
			body.add("(" + String.valueOf(userDetails.getReportId()) + ")");
			com.worldvisa.service.MailClient mailClient = new com.worldvisa.service.MailClient(sender, contentBuilder);
			mailClient.prepareAndSend(userInformation.getEmailAddress(), body, "CustomerReportRecieved", subject,null);

			User user = new User();
			user.setPassword(bCryptPasswordEncoder.encode(userInformation.getName()));
			user.setActive(false);
			user.setEmail(userInformation.getEmailAddress().trim());
			user.setPasswordExpFlag(false);
			user.setRegistrationStatus("Inactive");

			Role userRole = roleRepository.findByRole("CUSTOMER");

			if (userRole == null) {
				userRole = new Role();
				userRole.setRole("CUSTOMER");
				roleRepository.save(userRole);
			}

			user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			userRepository.save(user);

		}
		return "redirect:/fill_a_form";
	}

}
